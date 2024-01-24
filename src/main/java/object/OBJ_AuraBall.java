package object;

import entity.Entity;
import entity.Player;
import entity.Projectile;
import entity.SpriteSheet;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OBJ_AuraBall extends Projectile {
    GamePanel gp;
    Boolean fireOn = true;
    Player user;
    public List<BufferedImage> downSprites = new ArrayList<>();
    public List<BufferedImage> leftSprites = new ArrayList<>();
    public List<BufferedImage> rightSprites = new ArrayList<>();
    public List<BufferedImage> upSprites = new ArrayList<>();
    public static final String objName = "AuraBall";
    public OBJ_AuraBall(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 2;
        maxLife = 160;
        life = maxLife;
        attack = 5;
        knockBackPower = 7;
        useCost = 0;
        alive = false;
        getImage();
    }

    private void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/projectile/AuraBall.png", 64,64,6,4);
        for (int i = 0; i < 6; i++) {
            downSprites.add(sprite.getSprite(i, 0));
            leftSprites.add(sprite.getSprite(i, 1));
            rightSprites.add(sprite.getSprite(i, 2));
            upSprites.add(sprite.getSprite(i, 3));
        }
        down1 = leftSprites.get(3);
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
        Color color = new Color(137,207, 240);
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
    public int animate2( int skillCounter ) {
        int numberOfFrames = 6;
        skillCounter++;
        if(skillCounter > 5){
            spriteNum = ((spriteNum + 1) % numberOfFrames);
            skillCounter = 0;
        }
        return skillCounter;
    }

   /* public int animate2( int burstCounter ) {
        int numberOfFrames = 4;
        burstCounter++;
        if(burstCounter > 5){
            spriteNum = ((spriteNum + 1) % numberOfFrames);
            burstCounter = 0;
            while(burstCounter < 5){
                spriteNum = ((spriteNum + 1) % numberOfFrames);
                burstCounter++;
            }
        }
        return burstCounter;
    }*/

}
