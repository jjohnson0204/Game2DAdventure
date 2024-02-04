package object.weapons.warrior;

import entity.Entity;
import main.GamePanel;

public class OBJ_Legendary_Sword extends Entity {
    public  static final String objName = "Legendary Sword";

    public OBJ_Legendary_Sword(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = objName;
        down1 = setup("/objects/sword_legendary");
        attackValue = 10;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA legendary sword.";
        price = 1000;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
