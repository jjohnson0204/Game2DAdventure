package entity;

import main.GamePanel;
import object.*;

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
        up1 = setup("/npc/merchant_down_1");
        up2 = setup("/npc/merchant_down_2");
        down1 = setup("/npc/merchant_down_1");
        down2 = setup("/npc/merchant_down_2");
        left1 = setup("/npc/merchant_down_1");
        left2 = setup("/npc/merchant_down_2");
        right1 = setup("/npc/merchant_down_1");
        right2 = setup("/npc/merchant_down_2");
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
