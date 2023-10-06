package tile.tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {
    GamePanel gp;
    public IT_DryTree(GamePanel gp, int col, int row) {
            super(gp, col, row);
            this.gp = gp;
            this.worldX = gp.tileSize * col;
            this.worldY = gp.tileSize * row;

            name = "Dry Tree";
            down1 = setup("/tiles_interactive/drytree");
            destructible = true;
            life = 1;
    }
    public void playSE(){
        gp.playSE(11);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp,
                (int) (worldX/ gp.tileSize),
                (int) (worldY/gp.tileSize));
        return tile;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public Color getParticleColor(){
        Color color = new Color(65,50, 30);
        return color;
    }
    public int getParticleSize(){
        int size = 6; // 6 pixels
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
