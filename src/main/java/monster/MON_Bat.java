package monster;

import entity.Entity;
import entity.SpriteSheet;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.consumables.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;
    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        //Attributes
        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 7;
        life = maxLife;
        attack = 7;
        defense = 0;
        exp = 2;

        //Solid Area box
        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //Monster Image
        getImage();
    }
    //Loading monster image
    public void getImage(){
        SpriteSheet sprite = new SpriteSheet("src/resources/monster/batflying.png", 32, 32, 4, 4);

        down1 = sprite.getSprite(0, 0);
        down2 = sprite.getSprite(1,0);
        down3 = sprite.getSprite(2, 0);
        down4 = sprite.getSprite(3, 0);

        left1 = sprite.getSprite(0, 1);
        left2 = sprite.getSprite(1, 1);
        left3 = sprite.getSprite(2, 1);
        left4 = sprite.getSprite(3, 1);

        right1 = sprite.getSprite(0, 2);
        right2 = sprite.getSprite(1, 2);
        right3 = sprite.getSprite(2, 2);
        right4 = sprite.getSprite(3, 2);

        up1 = sprite.getSprite(0, 3);
        up2 = sprite.getSprite(1,3);
        up3 = sprite.getSprite(2, 3);
        up4 = sprite.getSprite(3,3);
    }

    //Setting monster movement
    public void setAction(){
        if(onPath){
//            //Check if it stops chasing
//            checkStopChasingOrNot(gp.player, 15, 100);
//
//            //Search the direction to go
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
//
//            //Check if it shoots a projectile
//            checkShootOrNot(200, 30);
        }
        else {
            //Check if it starts chasing
//            checkStartChasingOrNot(gp.player, 5, 100);

            //Get a random direction
            getRandomDirection(10);
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
