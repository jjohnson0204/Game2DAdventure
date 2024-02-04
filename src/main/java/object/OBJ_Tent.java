package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;
    public  static final String objName = "Tent";
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("/objects/tent");
        description = "[" + name + "]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.players[gp.selectedPlayerIndex].life = gp.players[gp.selectedPlayerIndex].maxLife;
        gp.players[gp.selectedPlayerIndex].mana = gp.players[gp.selectedPlayerIndex].maxMana;
        gp.players[gp.selectedPlayerIndex].getSleepingImage(down1);
        return false; // false reusable // true one time use
    }
}
