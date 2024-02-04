package entity;

import main.GamePanel;

import java.awt.*;
public class Particle extends Entity{
    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public int x, y;
    public int vx, vy;
    public int life;

    public Particle(GamePanel gp,
                    Entity generator, Color color,
                    int size, int speed, int maxLife,
                    int xd, int yd) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gp.tileSize/2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public Particle(GamePanel gp,int x, int y, int vx, int vy, Color color, int size, int life) {
        super(gp);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.size = size;
        this.life = life;
    }
    public void update(){
        life--;
        if(life < maxLife / 3){
            vy++;
        }
        x += vx;
        y += vy;
        if(life == 0){
            alive = false;
        }
    }
    public void draw(Graphics2D g2){
//        double screenX = worldX - gp.players[gp.selectedPlayerIndex].worldX + gp.players[gp.selectedPlayerIndex].screenX;
//        double screenY = worldY - gp.players[gp.selectedPlayerIndex].worldY + gp.players[gp.selectedPlayerIndex].screenY;

        g2.setColor(color);
        g2.fillRect(x, y, size, size);
    }
}
