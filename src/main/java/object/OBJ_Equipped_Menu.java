package object;

import entity.Entity;
import main.GamePanel;
import main.UI;

import java.awt.image.BufferedImage;

public class OBJ_Equipped_Menu extends Entity {
    GamePanel gp;
    BufferedImage weapon;
    BufferedImage ability1 = setup("/ui/auraability");
    BufferedImage ability2 = setup("/ui/auraballability");
    BufferedImage ability3 = setup("/ui/auranadoability");

    public static final String objName = "Equipped Menu";

    public OBJ_Equipped_Menu(GamePanel gp) {
        super(gp);
        this.gp = gp;
        weapon = gp.player.currentWeapon.down1;

        name = objName;
        down1 = setup("/ui/equipmenu");
        image = ability1;
        image2 = ability2;
        image3 = ability3;
    }
}
