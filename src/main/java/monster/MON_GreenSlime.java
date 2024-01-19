package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        //Attributes
        type = type_monster;
        name = "Green Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);
        projectile2 = new OBJ_Rock(gp);
        projectile3 = new OBJ_Rock(gp);

        //Solid Area box
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //Monster Image
        getImage();
    }
    //Loading monster image
    public void getImage(){
        up1 = setup("/monster/greenslime_down_1");
        up2 = setup("/monster/greenslime_down_2");
        down1 = setup("/monster/greenslime_down_1");
        down2 = setup("/monster/greenslime_down_2");
        left1 = setup("/monster/greenslime_down_1");
        left2 = setup("/monster/greenslime_down_2");
        right1 = setup("/monster/greenslime_down_1");
        right2 = setup("/monster/greenslime_down_2");

    }

    //Setting monster movement
    public void setAction(){
        if(onPath){
            //Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            //Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            //Check if it shoots a projectile
            checkShootOrNot(200, 30);
        }
        else {
            //Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

            //Get a random direction
            getRandomDirection();
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
