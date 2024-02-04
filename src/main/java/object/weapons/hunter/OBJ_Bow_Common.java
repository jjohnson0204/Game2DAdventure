package object.weapons.hunter;

import entity.Projectile;
import main.GamePanel;
import object.abilities.hunter.OBJ_CommonArrows;

public class OBJ_Bow_Common extends OBJ_Legendary_Bow{
    GamePanel gp;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public static final String objName = "Common Bow";
    public OBJ_Bow_Common(GamePanel gp, String element) {
        super(gp, element);
        this.gp = gp;

        this.projectile1 = new OBJ_CommonArrows(gp);
        this.projectile2 = new OBJ_CommonArrows(gp);
        this.projectile3 = new OBJ_CommonArrows(gp);

        type = type_bow;
        name = objName;
        down1 = setup("/objects/commonbow");
        attackValue = 5;
        description = "[" + name + "]\nA common bow.";
        price = 100;
    }
    @Override
    public void useCommon() {
        // Implement the effect of the common element
        System.out.println("Casting common spell...");

        // For example, you might want to increase the player's speed when the common spell is cast:
        this.speed += 5;

        // Or you might want to deal damage to enemies in a certain radius:
        // (Assuming you have a method `dealDamageToEnemiesInRadius(radius, damage)`)
        int radius = 3;
        int damage = 5;
//        dealDamageToEnemiesInRadius(radius, damage);

        System.out.println("Common spell has been cast!");
    }

}
