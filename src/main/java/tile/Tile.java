package tile;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }
    public Tile() {
    }

    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(image, x, y, null);
    }

    public boolean isSolid() {
        return collision;
    }

}
