package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPC_OldMan extends Entity{
    UtilityTool util;
    public NPC_OldMan(GamePanel gp){
        super(gp);

        type = type_npc;
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 42;

        dialogueSet = -1;

        getOldManImage();
        setDialogue();
    }
    public void getOldManImage(){
        SpriteSheet sprite = new SpriteSheet("src/resources/npc/wiseman.png", 30,42,4,4);
        down1 = sprite.getSprite2(0,0);
        down2 = sprite.getSprite2(1,0);
        down3 = sprite.getSprite2(2, 0);
        down4 = sprite.getSprite2(3,0);
        left1 = sprite.getSprite2(0, 1);
        left2 = sprite.getSprite2(1, 1);
        left3 = sprite.getSprite2(2, 1);
        left4 = sprite.getSprite2(3, 1);
        right1 = sprite.getSprite2(0, 2);
        right2 = sprite.getSprite2(1, 2);
        right3 = sprite.getSprite2(2, 2);
        right4 = sprite.getSprite2(3, 2);
        up1 = sprite.getSprite2(0, 3);
        up2 = sprite.getSprite2(1,3);
        up3 = sprite.getSprite2(2, 3);
        up4 = sprite.getSprite2(3,3);
    }
    public void setDialogue(){
        dialogues[0][0] = "Hello, there!";
        dialogues[0][1] = "You must be looking for the treasures of this world.";
        dialogues[0][2] = "I will help guide you.";
        dialogues[0][3] = "Check in from time to time.";
        dialogues[0][4] = "Best of luck on your adventure!";

        dialogues[1][0] = "If you become battered and bruised.\nGrab some water from the lake.";
        dialogues[1][1] = "The only catch is the enemies respawn.";
        dialogues[1][2] = "Kinda weird but that's how it is.";

        dialogues[2][0] = "Have you noticed the locked doors around?";
        dialogues[2][1] = "You must collect keys to open them,\n they can be found around the map.";
        dialogues[2][2] = "Also, if you have enough monies,\n find the merchant, he sales them.";

    }
    public void setAction(){
        if(onPath == true){
//            int goalCol = 12;
//            int goalRow = 9;
            int goalCol = (int) (gp.players[gp.selectedPlayerIndex].worldX + gp.players[gp.selectedPlayerIndex].solidArea.x) / gp.tileSize;
            int goalRow = (int) (gp.players[gp.selectedPlayerIndex].worldY + gp.players[gp.selectedPlayerIndex].solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter++;

            if(actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // 1 to 100
                if(i <= 25){
                    direction = "up";
                }
                if(i > 25 && i <= 50){
                    direction = "down";
                }
                if(i >50 && i <= 75){
                    direction = "left";
                }
                if(i > 75){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void speak(){
        // This method will control character specific situations
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null) {
            dialogueSet = 0;
        }

        //Example Set - if the player health is at a 3rd NPC will speak this dialogue
//        if (gp.players[gp.selectedPlayerIndex].life < gp.players[gp.selectedPlayerIndex].maxLife/3) {
//            dialogueSet = 1;
//        }

//        onPath = true;
    }

}
