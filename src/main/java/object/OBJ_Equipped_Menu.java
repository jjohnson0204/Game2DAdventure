package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Equipped_Menu extends Entity {
    GamePanel gp;
    Entity entity;
    BufferedImage ability1 = setup("/ui/auraability");
    BufferedImage ability2 = setup("/ui/auraballability");

    public static final String objName = "Equipped Menu";

    public OBJ_Equipped_Menu(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        down1 = setup("/ui/equipmenu");
        image = ability1;
        image2 = ability2;
        /*image3 = setup("");*/
    }
}
