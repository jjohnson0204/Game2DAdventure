package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.consumables.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;
    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        //Attributes
        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;
        knockBackPower = 5;

        //Solid Area box
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;

        //Monster Image
        getImage();
        getAttackImage();
    }
    //Loading monster image
    public void  getImage(){
        up1 = setup("/monster/orc_up_1");
        up2 = setup("/monster/orc_up_2");
        down1 = setup("/monster/orc_down_1");
        down2 = setup("/monster/orc_down_2");
        left1 = setup("/monster/orc_left_1");
        left2 = setup("/monster/orc_left_2");
        right1 = setup("/monster/orc_right_1");
        right2 = setup("/monster/orc_right_2");
    }
    //Loading monster attack image
    public void getAttackImage() {
        attackUp1 = setup2("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup2("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup2("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup2("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup2("/monster/orc_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup2("/monster/orc_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup2("/monster/orc_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup2("/monster/orc_attack_right_2", gp.tileSize*2, gp.tileSize);
    }
    //Setting monster movement
    public void setAction(){
        if(onPath){
            //Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            //Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

        }
        else {
            //Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

            //Get a random direction
            getRandomDirection(120);
        }

        //Check if it attacks
        if (!attacking) {
            checkAttackOrNot(30, gp.tileSize * 4, gp.tileSize);
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
//        direction = gp.player.direction;
        onPath = true;
    }
    public void checkDrop(){
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
