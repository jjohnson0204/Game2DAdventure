package monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;
    public static final String monName = "Skeleton Lord";
    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        //Attributes
        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackPower = 5;
        sleep = true;

        //Solid Area box
        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        //Monster Image
        getImage();
        getAttackImage();
        setDialogue();
    }
    //Loading monster image
    public void  getImage(){

        int i = 5;

        if (!inRage) {
            up1 = setup2("/monster/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup2("/monster/skeletonlord_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup2("/monster/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup2("/monster/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup2("/monster/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup2("/monster/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup2("/monster/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup2("/monster/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
        }
        if (inRage) {
            up1 = setup2("/monster/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup2("/monster/skeletonlord__phase2up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup2("/monster/skeletonlord_phase2_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup2("/monster/skeletonlord_phase2_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup2("/monster/skeletonlord_phase2_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup2("/monster/skeletonlord_phase2_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup2("/monster/skeletonlord_phase2_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup2("/monster/skeletonlord_phase2_right_2", gp.tileSize * i, gp.tileSize * i);
        }

    }
    //Loading monster attack image
    public void getAttackImage() {

        int i = 5;

        if (!inRage) {
            attackUp1 = setup2("/monster/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup2("/monster/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * i *2);
            attackDown1 = setup2("/monster/skeletonlord_attack_down_1", gp.tileSize * i, gp.tileSize * i *2);
            attackDown2 = setup2("/monster/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * i *2);
            attackLeft1 = setup2("/monster/skeletonlord_attack_left_1", gp.tileSize * 2 * i, gp.tileSize * i);
            attackLeft2 = setup2("/monster/skeletonlord_attack_left_2", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight1 = setup2("/monster/skeletonlord_attack_right_1", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight2 = setup2("/monster/skeletonlord_attack_right_2", gp.tileSize * 2 * i, gp.tileSize * i);
        }
        if (inRage){
            attackUp1 = setup2("/monster/skeletonlord_phase2_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup2("/monster/skeletonlord_phase2_attack_up_2", gp.tileSize * i, gp.tileSize * i *2);
            attackDown1 = setup2("/monster/skeletonlord_phase2_attack_down_1", gp.tileSize * i, gp.tileSize * i *2);
            attackDown2 = setup2("/monster/skeletonlord_phase2_attack_down_2", gp.tileSize * i, gp.tileSize * i *2);
            attackLeft1 = setup2("/monster/skeletonlord_phase2_attack_left_1", gp.tileSize * 2 * i, gp.tileSize * i);
            attackLeft2 = setup2("/monster/skeletonlord_phase2_attack_left_2", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight1 = setup2("/monster/skeletonlord_phase2_attack_right_1", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight2 = setup2("/monster/skeletonlord_phase2_attack_right_2", gp.tileSize * 2 * i, gp.tileSize * i);
        }

    }

    public void setDialogue() {
        dialogues[0][0] = "\"Ah...another adventurer....\nhave you come seeking the staff?\"";
//        gp.playSE(23);
        dialogues[0][1] = "\"How foolish";
        dialogues[0][2] = "\"Look around....\nthe bones of those before you are scattered \nat your feet\"";
        dialogues[0][3] = "\"Still you dare to challenge me?\" -laughs";
//        gp.playSE(24);
        dialogues[0][4] = "\"Come then! For this place shall be your tomb!\"";
    }

    //Setting monster movement
    public void setAction(){
        if (!inRage && life < maxLife / 2) {
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }
        if(getTileDistance(gp.player) < 10){
            moveTowardPlayer(60);
        }
        else {
            //Get a random direction
            getRandomDirection(120);
        }

        //Check if it attacks
        if (!attacking) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
    }
    public void checkDrop(){
        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;
        //Restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        //Remove the iron doors
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null
                    && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
                gp.playSE(21);
                gp.obj[gp.currentMap][i] = null;
            }
        }

        // Cast a die
        int i = new Random().nextInt(100) + 1;
        // Set the monster drop
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
