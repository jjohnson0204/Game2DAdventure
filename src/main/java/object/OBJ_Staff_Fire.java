package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Staff_Fire extends OBJ_Staff_Legendary{
    GamePanel gp;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public OBJ_Staff_Fire(GamePanel gp, String element) {
        super(gp, element);
        this.gp = gp;

        projectile1 = new OBJ_Fireball(gp);
        projectile2 = new OBJ_FireBlast(gp);
        projectile3 = new OBJ_FireAOE(gp);

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
