package object;

import entity.Entity;
import entity.SpriteSheet;
import main.GamePanel;

import java.awt.image.BufferedImage;

/**
 * Represents a teleporter in the game.
 */
public class OBJ_Teleporter extends Entity {
    private GamePanel gp;
    private BufferedImage currentImage;
    private int hitMap;
    private int hitCol;
    private int hitRow;
    private int destMap;
    private int destCol;
    private int destRow;
    private int destArea;
    private int frameCounter = 0;

    public static final String OBJ_NAME = "Teleporter";

    /**
     * Constructs a new Teleporter.
     */
    public OBJ_Teleporter(GamePanel gp) {
        super(gp);
        this.gp = gp;
        this.name = OBJ_NAME;
        this.type = type_teleporter;

        // Set the solid area to the entire area of the teleporter
        this.solidArea.x = 0;
        this.solidArea.y = 0;
        this.solidArea.width = this.width;
        this.solidArea.height = this.height;

        this.hitMap = hitMap;
        this.hitCol = hitCol;
        this.hitRow = hitRow;
        this.destMap = destMap;
        this.destCol = destCol;
        this.destRow = destRow;
        this.destArea = destArea;

        speed = 0;
        getImage();
        animateImage();
        interact("any");
    }

    /**
     * Retrieves the images for the teleporter.
     */
    private void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/objects/teleporter.png", 48, 64, 4, 1);
        down1 = sprite.getSprite2(0,0);
        down2 = sprite.getSprite2(1,0);
        down3 = sprite.getSprite2(2, 0);
        down4 = sprite.getSprite2(3,0);
    }

    /**
     * Animates the image of the teleporter.
     */
    public void animateImage() {
        if (inCamera()) {
            frameCounter++;
            if (frameCounter > 50) {
                switch (frameCounter % 4) {
                    case 0:
                        currentImage = down1;
                        break;
                    case 1:
                        currentImage = down2;
                        break;
                    case 2:
                        currentImage = down3;
                        break;
                    case 3:
                        currentImage = down4;
                        break;
                }
                frameCounter = 0;
            }
        }
    }
    public void interact(String reqDirection) {
        if (gp.eHandler.hit(hitMap, hitCol, hitRow, reqDirection)) {
            gp.eHandler.teleport(destMap, destCol, destRow, destArea);
        }
    }
    public void setDestination(int destMap, int destCol, int destRow, int destArea) {
        this.destMap = destMap;
        this.destCol = destCol;
        this.destRow = destRow;
        this.destArea = destArea;
    }
    public boolean inCamera() {
        boolean inCamera = false;

        if(worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }
    // Add getter and setter methods for the fields here
}