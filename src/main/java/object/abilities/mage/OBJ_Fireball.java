package object.abilities.mage;

import entity.Entity;
import entity.Projectile;
import entity.SpriteSheet;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;
    public  static final String objName = "Fireball";

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        knockBackPower = 5;
        useCost = 1;
        alive = false;
        getImage();
    }
    private void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/projectile/fireball.png", 32,32,4,4);
        down1 = sprite.getSprite2(0,0);
        down2 = sprite.getSprite2(1,0);
        down3 = sprite.getSprite2(2, 0);
        down4 = sprite.getSprite2(3,0);
        left1 = sprite.getSprite2(0, 1);
        left2 = sprite.getSprite2(1, 1);
        left3 = sprite.getSprite2(2, 1);
        left4 = sprite.getSprite2(3, 1);
        right1 = sprite.getSprite2(0, 2);
        right2 = sprite.getSprite2(1, 2);
        right3 = sprite.getSprite2(2, 2);
        right4 = sprite.getSprite2(3, 2);
        up1 = sprite.getSprite2(0, 3);
        up2 = sprite.getSprite2(1,3);
        up3 = sprite.getSprite2(2, 3);
        up4 = sprite.getSprite2(3,3);
    }
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
    public Color getParticleColor(){
        Color color = new Color(236,48, 10);
        return color;
    }
    public int getParticleSize(){
        int size = 12; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }

}
