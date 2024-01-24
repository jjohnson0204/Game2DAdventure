package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Staff_Electric extends OBJ_Staff_Legendary{
    GamePanel gp;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public OBJ_Staff_Electric(GamePanel gp, String element) {
        super(gp, element);
        this.gp = gp;

        projectile1 = new OBJ_ThunderBall(gp);
        projectile2 = new OBJ_ThunderSlash(gp);
        projectile3 = new OBJ_ThunderShield(gp, gp.player);

        down1 = setup("/objects/staff_electric");
    }
    @Override
    public void useElectric() {
        // Implement the effect of the electric element
        System.out.println("Casting electric spell...");

        // Use projectile3 and set the player's invincibility to true
        projectile3 = new OBJ_ThunderShield(gp, gp.player);
        gp.player.invincible = true;

        // Create a new Thread to check when the projectile's life has expired
        new Thread(() -> {
            while (true) {
                if (projectile3.hasExpired()) {
                    gp.player.invincible = false;
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
