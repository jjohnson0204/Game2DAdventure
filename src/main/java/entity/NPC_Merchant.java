package entity;

import main.GamePanel;
import object.*;
import object.consumables.OBJ_Potion_Red;
import object.weapons.OBJ_Axe;
import object.weapons.warrior.OBJ_Sword_Normal;
import object.weapons.warrior.OBJ_Shield_Blue;
import object.weapons.warrior.OBJ_Shield_Wood;

import java.awt.*;

public class NPC_Merchant extends Entity{

    public NPC_Merchant(GamePanel gp){
        super(gp);

        type = type_npc;
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 48;
        solidArea.y = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 64;
        solidArea.height = 64;

        getImage();
        setDialogue();
        setItems();

        setDialogue();
    }
    public void getImage(){
        SpriteSheet sprite = new SpriteSheet("src/resources/npc/merchant.png", 32,32,4,4);
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
        dialogues[0][0] = "Hello, there! Looks like you found my shop." +
                "\nI have many things you may be interested in. " +
                "\nCare for a trade???";
        dialogues[1][0] = "Good-bye for now!";
        dialogues[2][0] = "Not enough monies!";
        dialogues[3][0] = "You can not hold anymore items.";
        dialogues[4][0] = "You can't sell equipped items!";
    }
    public void setItems(){
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
    }
    public void speak(){
        facePlayer();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }

    public void setOcclussion() {
    }
}
