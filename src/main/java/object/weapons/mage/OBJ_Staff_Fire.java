package object.weapons.mage;

import entity.Projectile;
import main.GamePanel;
import object.abilities.mage.OBJ_FireAOE;
import object.abilities.mage.OBJ_FireBlast;
import object.abilities.mage.OBJ_Fireball;

public class OBJ_Staff_Fire extends OBJ_Legendary_Staff {
    GamePanel gp;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public static final String objName = "Fire Staff";
    public OBJ_Staff_Fire(GamePanel gp, String element) {
        super(gp, element);
        this.gp = gp;

        projectile1 = new OBJ_Fireball(gp);
        projectile2 = new OBJ_FireBlast(gp);
        projectile3 = new OBJ_FireAOE(gp);

        type = type_staff;
        name = objName;
        down1 = setup("/objects/staff_fire");
    }
    @Override
    public void useFire() {
        // Implement the effect of the fire element
        System.out.println("Casting fire spell...");

        // For example, you might want to increase the player's speed when the fire spell is cast:
//        this.speed += 10;

        // Or you might want to deal damage to enemies in a certain radius:
        // (Assuming you have a method `dealDamageToEnemiesInRadius(radius, damage)`)
        int radius = 5;
        int damage = 10;
    }
}
