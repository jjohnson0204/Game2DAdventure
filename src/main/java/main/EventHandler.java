package main;

import data.Progress;
import entity.Entity;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventMaster;

    int previousEventX, previousEventY;
    int tempMap, tempCol, tempRow;
    boolean canTouchEvent = true;
    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX =  eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY =  eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }
    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You busted yo ass!";
        eventMaster.dialogues[1][0] = "The Mona water.... " +
                "\n Has saved your LIFE!" +
                "\nYour progress has been saved";
        eventMaster.dialogues[1][1] = "Damn, this is some good water!";
    }
    public void checkEvent(){
        //Check if player is one tile away from event
        int xDistance = (int) Math.abs(gp.players[gp.selectedPlayerIndex].worldX - previousEventX);
        int yDistance = (int) Math.abs(gp.players[gp.selectedPlayerIndex].worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
            //Pitfalls
            if(hit(0,7, 17, "any")){damagePit(gp.dialogueState);}

            //Lake Healing locations
            else if(hit(0,8, 43, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,9, 43, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,28, 12, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,29, 12, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,30, 12, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,31, 11, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,25, 44, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,26, 44, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,27, 44, "up")){healingPool(gp.dialogueState);}
            else if(hit(0,28, 44, "up")){healingPool(gp.dialogueState);}

            //Teleports
            else if(hit(0,9, 35, "any")){teleport(1,7, 14, gp.indoor);} // Enter Merchant
            else if(hit(1,6, 14, "any")){teleport(0,9, 35, gp.outside);} // Leave Merchant
            else if(hit(1,7, 14, "any")){teleport(0,9, 35, gp.outside);}
            else if(hit(1,8, 14, "any")){teleport(0,9, 35, gp.outside);}
            else if(hit(0,12, 9, "any")){teleport(2,9, 41, gp.dungeon);}  // Enter Dungeon
            else if(hit(2,9, 41, "any")){teleport(0,12, 9, gp.outside);}  // Exit Dungeon Outside
            else if(hit(2,8, 7, "any")){teleport(3,26, 41, gp.dungeon);}  // To B2
            else if(hit(3,26, 41, "any")){teleport(2,8, 7, gp.dungeon);}  // To B1

            //Cheat to boss room tile
            else if(hit(0,16, 8, "any")){teleport(3,26, 41, gp.dungeon);}

            //Speak
            else if(hit(1, 9, 7, "up")){speak(gp.npc[1][0]);}

            //Cutscene Point
            else if(hit(3,25, 27, "any")){skeletonLord();} // Boss Cutscene
        }
    }
    private void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.players[gp.selectedPlayerIndex].life -= 1;
        canTouchEvent = false;
    }
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
            gp.players[gp.selectedPlayerIndex].attackCanceled = true;
            gp.playSE(2);
            eventMaster.startDialogue(eventMaster, 1);
            gp.players[gp.selectedPlayerIndex].life = gp.players[gp.selectedPlayerIndex].maxLife;
            gp.players[gp.selectedPlayerIndex].mana = gp.players[gp.selectedPlayerIndex].maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }

//    public String getCurrentState() {
//        String currentState = "";
//        if(hit(0,9, 35, "any")){currentState = "EnterMerchant";} // Enter Merchant
//        else if(hit(1,12, 13, "any")){teleport(0,9, 35);} // Leave Merchant
//        else if(hit(0,12, 9, "any")){teleport(2,9, 41);}  // Enter Dungeon
//        else if(hit(2,9, 41, "any")){teleport(0,12, 9);}  // Exit Dungeon Outside
//        else if(hit(2,8, 7, "any")){teleport(3,26, 41);}  // Enter Dungeon Lower Level
//        else if(hit(3,26, 41, "any")){teleport(2,8, 7);}  // Leave Lower Level to Upper Level
//        return currentState;
//    }
    public void teleport(int map, int col, int row, int area){
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol =  col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(13);
    }
    public boolean hit(int map, int col, int row, String reqDirection){

        boolean hit = false;

        //Get players solidArea and add to event
        if(map == gp.currentMap){
            gp.players[gp.selectedPlayerIndex].solidArea.x = (int) (gp.players[gp.selectedPlayerIndex].worldX + gp.players[gp.selectedPlayerIndex].solidArea.x);
            gp.players[gp.selectedPlayerIndex].solidArea.y = (int) (gp.players[gp.selectedPlayerIndex].worldY + gp.players[gp.selectedPlayerIndex].solidArea.y);
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            //If players body touches' event it's a hit
            if(gp.players[gp.selectedPlayerIndex].solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone){
                if(gp.players[gp.selectedPlayerIndex].direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = (int) gp.players[gp.selectedPlayerIndex].worldX;
                    previousEventY = (int) gp.players[gp.selectedPlayerIndex].worldY;
                }
            }
            gp.players[gp.selectedPlayerIndex].solidArea.x = gp.players[gp.selectedPlayerIndex].solidAreaDefaultX;
            gp.players[gp.selectedPlayerIndex].solidArea.y = gp.players[gp.selectedPlayerIndex].solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void speak(Entity entity){
        if(gp.keyH.enterPressed){
            gp.gameState = gp.dialogueState;
            gp.players[gp.selectedPlayerIndex].attackCanceled = true;
            entity.speak();
        }
    }
    public void skeletonLord(){
        if (!gp.bossBattleOn && !Progress.skeletonLordDefeated) {
            gp.gameState = gp.cutSceneState;
            gp.csManager.sceneNum = gp.csManager.skeletonLord;
        }
    }
}
