package object.abilities.mage;

import entity.Entity;
import entity.Projectile;
import entity.SpriteSheet;
import entity.TargetedProjectile;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_FireAOE extends TargetedProjectile {
    GamePanel gp;
    public  static final String objName = "Fire AOE";

    public OBJ_FireAOE(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 0;
        maxLife = 200;
        life = maxLife;
        attack = 10;
        knockBackPower = 0;
        useCost = 0;
        alive = false;
        getImage();

    }
    private void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/projectile/fireaoe.png", 64,64,4,1);
        down1 = sprite.getSprite2(0,0);
        down2 = sprite.getSprite2(1,0);
        down3 = sprite.getSprite2(2, 0);
        down4 = sprite.getSprite2(3,0);
        left1 = sprite.getSprite2(0, 0);
        left2 = sprite.getSprite2(1, 0);
        left3 = sprite.getSprite2(2, 0);
        left4 = sprite.getSprite2(3, 0);
        right1 = sprite.getSprite2(0, 0);
        right2 = sprite.getSprite2(1, 0);
        right3 = sprite.getSprite2(2, 0);
        right4 = sprite.getSprite2(3, 0);
        up1 = sprite.getSprite2(0, 0);
        up2 = sprite.getSprite2(1,0);
        up3 = sprite.getSprite2(2, 0);
        up4 = sprite.getSprite2(3,0);
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
        return new Color(236,48, 10);
    }
    public int getParticleSize(){
        return 12;
    }
    public int getParticleSpeed(){
        return 1;
    }
    public int getParticleMaxLife(){
        return 20;
    }

}
