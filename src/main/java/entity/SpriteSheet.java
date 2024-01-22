package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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

    public BufferedImage getSprite2(int col, int row) {
        return getSprite2(col, row, 2);
    }
    public BufferedImage getSprite2(int col, int row, int scale) {
        BufferedImage sprite = img.getSubimage(
                col * this.width,
                row * this.height,
                this.width,
                this.height
        );
        Image tmp = sprite.getScaledInstance(this.width * scale, this.height * scale, Image.SCALE_SMOOTH);
        BufferedImage scaledSprite = new BufferedImage(this.width * scale, this.height * scale, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = scaledSprite.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return scaledSprite;
    }

}
