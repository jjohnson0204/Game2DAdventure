package object;

import entity.Entity;
import entity.Player;
import entity.Projectile;
import entity.SpriteSheet;
import main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OBJ_Aura extends Projectile {
    GamePanel gp;
    Boolean fireOn = true;
    Player user;
    public List<BufferedImage> downSprites = new ArrayList<>();
    public List<BufferedImage> leftSprites = new ArrayList<>();
    public List<BufferedImage> rightSprites = new ArrayList<>();
    public List<BufferedImage> upSprites = new ArrayList<>();
    public  static final String objName = "Aura";

    public OBJ_Aura(GamePanel gp) {
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
        SpriteSheet sprite = new SpriteSheet("src/resources/projectile/aura.png", 32, 32, 9, 4);
//        down1 = sprite.getSprite(0, 0);
//        down2 = sprite.getSprite(1, 0);
//        down3 = sprite.getSprite(2, 0);
//        down4 = sprite.getSprite(3, 0);
//        down5 = sprite.getSprite(4, 0);
//        down6 = sprite.getSprite(5, 0);
//        down7 = sprite.getSprite(6, 0);
//        down8 = sprite.getSprite(7, 0);
//        down9 = sprite.getSprite(8, 0);

        for (int i = 0; i < 9; i++) {
                downSprites.add(sprite.getSprite(i, 0));
                leftSprites.add(sprite.getSprite(i, 1));
                rightSprites.add(sprite.getSprite(i, 2));
                upSprites.add(sprite.getSprite(i, 3));
        }

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
    public int animate( int spriteCounter ) {
        int numberOfFrames = 9;
        spriteCounter++;
        if(spriteCounter > 5){
            spriteNum = ((spriteNum + 1) % numberOfFrames);
            spriteCounter = 0;
        }
        return spriteCounter;
    }
}
