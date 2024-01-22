package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";
    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setup2("src/resources/objects/blueheart", gp.tileSize, gp.tileSize);
    }
    public void setDialogues() {
        dialogues[0][0] = "You picked up a beautiful\nblue gem.";
        dialogues[0][1] = "Along with the legendary staff,\nto help you on your adventure";
        dialogues[0][2] = "With mastery of this staff,\nyou will learn to use all elements";
    }
}
