package main;

import entity.players.*;
import object.OBJ_Chest;
import object.OBJ_Key;
import object.weapons.assassin.OBJ_Dagger_Fire;
import object.weapons.fighter.OBJ_Glove_Fire;
import object.weapons.hunter.OBJ_Bow_Fire;
import object.weapons.mage.OBJ_Staff_Fire;
import object.weapons.warrior.OBJ_Legendary_Sword;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener {
    public boolean enterKeyProcessed = false;

    public boolean upPressed, downPressed, leftPressed, rightPressed,
            enterPressed, spacePressed,
            shotKeyPressed, skillKeyPressed, burstKeyPressed;
    public boolean showDebugText = false;
    public boolean godModeOn = false;

    GamePanel gp;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER && !enterKeyProcessed) {
            enterPressed = true;
            enterKeyProcessed = true; // Set the flag to true after the key is pressed
        }

        //Title State
        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        // Play State
        else if(gp.gameState == gp.playState){
            playState(code);
        }
        // Pause State
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        // Dialogue State and Cutscene State
        else if(gp.gameState == gp.dialogueState
        || gp.gameState == gp.cutSceneState){
            dialogueState(code);
        }
        // Character State
        else if(gp.gameState == gp.characterState) {
           characterState(code);
        }
        // Options State
        else if(gp.gameState == gp.optionsState){
            optionsState(code);
        }
        // Game Over State
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        // Trade State
        else if(gp.gameState == gp.tradeState){
            tradeState(code);
        }
        // Map State
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }
    }
    public void optionsState(int code) {
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum ==1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum ==2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum ==1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum ==2 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_B) {
            skillKeyPressed = false;
        }
        if (code == KeyEvent.VK_V) {
            burstKeyPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
            enterKeyProcessed = false; // Reset the flag when the key is released
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
    public void titleState(int code){
        if(gp.ui.titleScreenState == 0){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                gp.playSE(9);
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(9);
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState = 1;
                }
                if(gp.ui.commandNum == 1){
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        else if(gp.ui.titleScreenState == 1){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                gp.playSE(9);
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 5;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(9);
                if(gp.ui.commandNum > 5){
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.selectedPlayerIndex = gp.ui.commandNum;

                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.player = new Fighter(gp, this);
                    gp.player.setPlayerType(gp.ui.commandNum);
                    // Find the chest at the location (35, 25)
                    for (int mapNum = 0; mapNum < gp.obj.length; mapNum++) {
                        for (int i = 0; i < gp.obj[mapNum].length; i++) {
                            if (gp.obj[mapNum][i] instanceof OBJ_Chest &&
                                    gp.obj[mapNum][i].worldX == 35 * gp.tileSize &&
                                    gp.obj[mapNum][i].worldY == 25 * gp.tileSize) {

                                // Set loot for the chest after player selection
                                ((OBJ_Chest) gp.obj[mapNum][i]).setLoot(new OBJ_Glove_Fire(gp, "fire"), new OBJ_Key(gp));
                                break;
                            }
                        }
                    }
                    gp.playMusic(0);
                }
                else if(gp.ui.commandNum == 1){
                    gp.gameState = gp.playState;
                    gp.player = new Warrior(gp, this);
                    gp.player.setPlayerType(gp.ui.commandNum);

                    // Find the chest at the location (35, 25)
                    for (int mapNum = 0; mapNum < gp.obj.length; mapNum++) {
                        for (int i = 0; i < gp.obj[mapNum].length; i++) {
                            if (gp.obj[mapNum][i] instanceof OBJ_Chest &&
                                    gp.obj[mapNum][i].worldX == 35 * gp.tileSize &&
                                    gp.obj[mapNum][i].worldY == 25 * gp.tileSize) {

                                // Set loot for the chest after player selection
                                ((OBJ_Chest) gp.obj[mapNum][i]).setLoot(new OBJ_Legendary_Sword(gp), new OBJ_Key(gp));
                                break;
                            }
                        }
                    }
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 2){
                    gp.gameState = gp.playState;
                    gp.player = new Hunter(gp, this);
                    gp.player.setPlayerType(gp.ui.commandNum);

                    // Find the chest at the location (35, 25)
                    for (int mapNum = 0; mapNum < gp.obj.length; mapNum++) {
                        for (int i = 0; i < gp.obj[mapNum].length; i++) {
                            if (gp.obj[mapNum][i] instanceof OBJ_Chest &&
                                    gp.obj[mapNum][i].worldX == 35 * gp.tileSize &&
                                    gp.obj[mapNum][i].worldY == 25 * gp.tileSize) {

                                // Set loot for the chest after player selection
                                ((OBJ_Chest) gp.obj[mapNum][i]).setLoot(new OBJ_Bow_Fire(gp, "fire"), new OBJ_Key(gp));
                                break;
                            }
                        }
                    }
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 3){
                    gp.gameState = gp.playState;
                    gp.player = new Assassin(gp, this);
                    gp.player.setPlayerType(gp.ui.commandNum);

                    // Find the chest at the location (35, 25)
                    for (int mapNum = 0; mapNum < gp.obj.length; mapNum++) {
                        for (int i = 0; i < gp.obj[mapNum].length; i++) {
                            if (gp.obj[mapNum][i] instanceof OBJ_Chest &&
                                    gp.obj[mapNum][i].worldX == 35 * gp.tileSize &&
                                    gp.obj[mapNum][i].worldY == 25 * gp.tileSize) {

                                // Set loot for the chest after player selection
                                ((OBJ_Chest) gp.obj[mapNum][i]).setLoot(new OBJ_Dagger_Fire(gp, "fire"), new OBJ_Key(gp));
                                break;
                            }
                        }
                    }
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 4){
                    gp.gameState = gp.playState;
                    gp.player = new Mage(gp, this);
                    gp.player.setPlayerType(gp.ui.commandNum);

                // Find the chest at the location (35, 25)
                for (int mapNum = 0; mapNum < gp.obj.length; mapNum++) {
                        for (int i = 0; i < gp.obj[mapNum].length; i++) {
                            if (gp.obj[mapNum][i] instanceof OBJ_Chest &&
                                    gp.obj[mapNum][i].worldX == 35 * gp.tileSize &&
                                    gp.obj[mapNum][i].worldY == 25 * gp.tileSize) {

                                // Set loot for the chest after player selection
                                ((OBJ_Chest) gp.obj[mapNum][i]).setLoot(new OBJ_Staff_Fire(gp, "fire"), new OBJ_Key(gp));
                                break;
                            }
                        }
                    }
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 5){
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
            }
        }
    }
    public void playState(int code){
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_B) {
            skillKeyPressed = true;
        }
        if (code == KeyEvent.VK_V) {
            burstKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_X) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }
        if (code == KeyEvent.VK_UP) {
            gp.zoomInOut(1);
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.zoomInOut(-1);
        }
        //Pause Button
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        //Character Stats Screen
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }

        // Debug Button
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }

        // Load Level
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0: gp.tileM.loadMap("src/resources/maps/world11.txt", 0); break;
                case 1: gp.tileM.loadMap("src/resources/maps/ShopInteriorNewTileMap.txt",1); break;
            }
        }

        // GodMode Button
        if (code == KeyEvent.VK_G) {
            godModeOn = !godModeOn;
        }
    }
    public void pauseState(int code){
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
    }
    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.resetGame(false);
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
    }
    public void tradeState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(gp.ui.subState == 0){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
                gp.playSE(9);
            }
        }
        if(gp.ui.subState == 1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }
    public void mapState(int code){
        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }
    public void playerInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.playerSlotRow != 0){
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.playerSlotCol != 0){
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow != 3){
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.playerSlotCol != 4){
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }
    public void npcInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.npcSlotRow != 0){
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.npcSlotCol != 0){
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.npcSlotRow != 3){
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.npcSlotCol != 4){
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }
}

