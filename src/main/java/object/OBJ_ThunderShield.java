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

public class OBJ_ThunderShield extends Projectile {
    GamePanel gp;
    Player player;
    public List<BufferedImage> downSprites = new ArrayList<>();
    public List<BufferedImage> leftSprites = new ArrayList<>();
    public List<BufferedImage> rightSprites = new ArrayList<>();
    public List<BufferedImage> upSprites = new ArrayList<>();
    public static final String objName = "Thunder Shield";
    public OBJ_ThunderShield(GamePanel gp, Player player) {
        super(gp);
        this.gp = gp;
        this.player = player;

        name = objName;
        speed = 0;
        maxLife = 320;
        life = maxLife;
        attack = 5;
        knockBackPower = 10;
        useCost = 0;
        alive = false;
        getImage();
    }

    private void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/projectile/thundershield.png", 64,64,4,1);
        for (int i = 0; i < 4; i++) {
            downSprites.add(sprite.getSprite2(i, 0));
            leftSprites.add(sprite.getSprite2(i, 0));
            rightSprites.add(sprite.getSprite2(i, 0));
            upSprites.add(sprite.getSprite2(i, 0));
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
    // Override the update method to move the shield with the player
    @Override
    public void update() {
        this.worldX = gp.player.worldX;
        this.worldY = gp.player.worldY;

        // Set the player's isThunderShieldActive field to true
        player.isThunderShieldActive = true;

        // Call the super method to handle other updates
        super.update();
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
