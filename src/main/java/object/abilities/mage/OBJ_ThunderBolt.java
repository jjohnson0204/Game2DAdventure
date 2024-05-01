package object.abilities.mage;

import entity.Entity;
import entity.Projectile;
import entity.SpriteSheet;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class OBJ_ThunderBolt extends Projectile {

    GamePanel gp;
    public List<BufferedImage> downSprites = new ArrayList<>();
    public List<BufferedImage> leftSprites = new ArrayList<>();
    public List<BufferedImage> rightSprites = new ArrayList<>();
    public List<BufferedImage> upSprites = new ArrayList<>();
    public static final String objName = "ThunderBolt";
    public OBJ_ThunderBolt(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 5;
        knockBackPower = 5;
        useCost = 0;
        alive = false;
        getImage();
    }

    private void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/projectile/thunderbolt.png", 64,64,8,1);
        for (int i = 0; i < 8; i++) {
            downSprites.add(sprite.getSprite(i, 0));
            leftSprites.add(sprite.getSprite(i, 0));
            rightSprites.add(sprite.getSprite(i, 0));
            upSprites.add(sprite.getSprite(i, 0));
        }
        down1 = downSprites.get(0);
    }
    public void draw(Graphics2D g2) {
        switch (direction) {
            case "up":
                image = upSprites.get(spriteNum);
                break;
            case "down":
                image = downSprites.get(spriteNum);
                break;
            case "left":
                image = leftSprites.get(spriteNum);
                break;
            case "right":
                image = rightSprites.get(spriteNum);
                break;
        }

        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, (int) screenX, (int) screenY, gp.tileSize, gp.tileSize, null);

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
        Color color = new Color(70, 234, 144);
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
