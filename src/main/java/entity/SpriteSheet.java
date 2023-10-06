package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    public String uri;
    private int width;
    private int height;
    private int cols;
    private int rows;

    private BufferedImage img;
    private BufferedImage[] sprites;
    GamePanel gp;

    public SpriteSheet( String location, int width, int height, int cols, int rows) {
        uri = location;
        this.height = height;
        this.width = width;
        this.cols = cols;
        this.rows = rows;
        try {
            this.img = ImageIO.read(new File(uri));
            this.sprites = new BufferedImage[rows * cols];
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
//    public BufferedImage setup(String imagePath){
//        UtilityTool uTool = new UtilityTool();
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File(uri));
//            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return image;
//    }

    public BufferedImage getSprite(int col, int row) {
        return img.getSubimage(
                col * this.width,
                row * this.height,
                this.width,
                this.height
        );
    }
}
