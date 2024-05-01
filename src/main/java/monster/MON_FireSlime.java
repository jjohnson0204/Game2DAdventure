package monster;

import entity.Entity;
import entity.SpriteSheet;
import main.GamePanel;
import object.*;
import object.abilities.mage.OBJ_Fireball;
import object.consumables.OBJ_ManaCrystal;

import java.util.Random;

public class MON_FireSlime extends Entity {
    GamePanel gp;
    public MON_FireSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        //Attributes
        type = type_monster;
        name = "Fire Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile1 = new OBJ_Fireball(gp);
        projectile2 = new OBJ_Fireball(gp);
        projectile3 = new OBJ_Fireball(gp);

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
        SpriteSheet sprite = new SpriteSheet("src/resources/monster/fireslime.png", 32,32,4,4);
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
            getRandomDirection(120);
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
