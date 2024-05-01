package object;

import entity.Entity;
import environment.Lighting;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    // Create a Lighting object
    Lighting lighting;
    public  static final String objName = "Chest";
    private List<Entity> loot;
    public GamePanel getGp() {
        return gp;
    }

    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.gp = gp;
        this.lighting = new Lighting(gp);
        this.loot = new ArrayList<>();

        gp.chests.add(this);
        type = type_obstacle;
        name = objName;
        image = setup("/objects/chest");
        image2 = setup("/objects/chest_opened");
        down1 = image;
        collisionOn = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Calculate the bloom effect coordinates based on the OBJ_Chest object's position
        int bloomX = solidArea.x + solidArea.width / 2;
        int bloomY = solidArea.y + solidArea.height / 2;

        // Call the applyBloomEffect method
//        lighting.applyBloomEffect(bloomX, bloomY);
    }
    public void setLoot(Entity loot){
        this.loot.add(loot);
        setDialogue();
    }
    public void setLoot(Entity... loots){
        for (Entity loot : loots) {
            this.loot.add(loot);
        }
        setDialogue();
    }
    public void setDialogue() {
        if (loot.size() > 1) {
            dialogues[0][0] = "You open the chest and find a " + loot.get(0).name + "!\n....But you cannot carry any more!";
            dialogues[0][1] = "You open the chest and find a " + loot.get(1).name + "!\n....But you cannot carry any more!";
            dialogues[1][0] = "You open the chest and find a " + loot.get(0).name + "!\nYou obtain the " + loot.get(0).name + "!";
            dialogues[1][1] = "You open the chest and find a " + loot.get(1).name + "!\nYou obtain the " + loot.get(1).name + "!";
        } else if (loot.size() == 1) {
            dialogues[0][0] = "You open the chest and find a " + loot.get(0).name + "!\n....But you cannot carry any more!";
            dialogues[1][0] = "You open the chest and find a " + loot.get(0).name + "!\nYou obtain the " + loot.get(0).name + "!";
        } else {
            dialogues[2][0] = "It's empty.";
        }
    }
    public void interact() {
        if(!opened) {
            gp.playSE(3);

            boolean canObtainAnyItem = false;
            for (Entity item : loot) {
                if (gp.player.canObtainItem(item)) {
                    canObtainAnyItem = true;
                    break;
                }
            }

            if (!canObtainAnyItem) {
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        } else {
            startDialogue(this, 2);
        }
    }
    public boolean inCamera() {
        boolean inCamera = false;

        if(worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }
    public List<Entity> getLoot() {
        return this.loot;
    }

}
