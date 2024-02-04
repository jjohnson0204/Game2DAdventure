package object.weapons.mage;

import entity.Projectile;
import main.GamePanel;
import object.abilities.mage.OBJ_Aura;
import object.abilities.mage.OBJ_AuraBall;
import object.abilities.mage.OBJ_AuraNado;

public class OBJ_Staff_Air extends OBJ_Legendary_Staff {
    GamePanel gp;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public static final String objName = "Air Staff";
    public OBJ_Staff_Air(GamePanel gp, String element){
        super(gp, element);
        this.gp = gp;

        projectile1 = new OBJ_Aura(gp);
        projectile2 = new OBJ_AuraBall(gp);
        projectile3 = new OBJ_AuraNado(gp);

        type = type_staff;
        name = objName;
        down1 = setup("/objects/staff_air");
        attackValue = 10;
        description = "[" + name + "]\nA air staff.";
        price = 100;
    }
    @Override
    public void useAir() {
        // Implement the effect of the air element
        System.out.println("Casting air spell...");

        // For example, you might want to increase the player's speed when the air spell is cast:
        this.speed += 10;

        // Or you might want to deal damage to enemies in a certain radius:
        // (Assuming you have a method `dealDamageToEnemiesInRadius(radius, damage)`)
        int radius = 5;
        int damage = 10;
//        dealDamageToEnemiesInRadius(radius, damage);

        System.out.println("Air spell has been cast!");
    }

}
