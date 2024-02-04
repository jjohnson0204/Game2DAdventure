package object.weapons.assassin;

import entity.Projectile;
import main.GamePanel;
import object.abilities.mage.OBJ_ThunderBall;
import object.abilities.mage.OBJ_ThunderShield;
import object.abilities.mage.OBJ_ThunderSlash;

public class OBJ_Dagger_Electric extends OBJ_Legendary_Dagger {
    GamePanel gp;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public static final String objName = "Dagger Electric";
    public OBJ_Dagger_Electric(GamePanel gp, String element) {
        super(gp, element);
        this.gp = gp;

        projectile1 = new OBJ_ThunderBall(gp);
        projectile2 = new OBJ_ThunderSlash(gp);
        projectile3 = new OBJ_ThunderShield(gp, gp.players[gp.selectedPlayerIndex]);

        type = type_dagger;
        name = objName;
        down1 = setup("/objects/staff_electric");
    }
    @Override
    public void useElectric() {
        // Implement the effect of the electric element
        System.out.println("Casting electric spell...");

        // Use projectile3 and set the player's invincibility to true
        projectile3 = new OBJ_ThunderShield(gp, gp.players[gp.selectedPlayerIndex]);
        gp.players[gp.selectedPlayerIndex].invincible = true;

        // Create a new Thread to check when the projectile's life has expired
        new Thread(() -> {
            while (true) {
                if (projectile3.hasExpired()) {
                    gp.players[gp.selectedPlayerIndex].invincible = false;
                    break;
                }

                try {
                    Thread.sleep(100); // Check every 100 milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
