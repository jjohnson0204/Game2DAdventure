package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_OldMan extends Entity{

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
        solidArea.width = 48;
        solidArea.height = 48;

        dialogueSet = -1;

        getOldManImage();
        setDialogue();
    }
    public void getOldManImage(){
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
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
            int goalCol = (int) (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (int) (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

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
//        if (gp.player.life < gp.player.maxLife/3) {
//            dialogueSet = 1;
//        }

//        onPath = true;
    }

}
