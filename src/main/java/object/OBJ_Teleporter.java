package object;

import entity.Entity;
import entity.SpriteSheet;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Teleporter extends Entity {
    GamePanel gp;
    private int destinationMap;
    private int destinationCol;
    private int destinationRow;
    private int destinationArea;
    private BufferedImage currentImage;
    private int frameCounter = 0;

    public static final String objName = "Teleporter";

    public OBJ_Teleporter(GamePanel gp) {
        super(gp);
        this.gp = gp;
        this.name = objName;
        this.type = type_teleporter;

        // Set the solid area to the entire area of the teleporter
        this.solidArea.x = 0;
        this.solidArea.y = 0;
        this.solidArea.width = this.width;
        this.solidArea.height = this.height;
        speed = 0;
        getImage();
    }

    public void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/objects/teleporter.png", 48, 64, 4, 1);
        down1 = sprite.getSprite2(0,0);
        down2 = sprite.getSprite2(1,0);
        down3 = sprite.getSprite2(2, 0);
        down4 = sprite.getSprite2(3,0);
        animateImage();
    }
    public void animateImage() {
        frameCounter++;
        if(frameCounter > 50){
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
