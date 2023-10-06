package tile.tile_interactive;

import entity.Entity;
import entity.NPC_Merchant;
import main.GamePanel;

import java.awt.*;

public class IT_OcclussionTile extends InteractiveTile{
    private Entity NPC_Merchant;
    Graphics2D g2;
    GamePanel gp;
    int tileDistance = getTileDistance(NPC_Merchant);
    public IT_OcclussionTile(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("");
        occludable = true;
        checkCharacterAboveTile(g2, .5f);
    }

    public void checkCharacterAboveTile(Graphics2D g2, float alphaValue) {
        // Assuming characterX and characterY represent the character's position
        // Assuming tileX and tileY represent the target tile's position

//        int distanceX;
//        distanceX = Math.abs(gp.npc[mapNum][i].worldX - getCol());
//        int distanceY = Math.abs(gp.npc - tileY);
//
//        if (distanceX <= 1 && distanceY <= 1) {
//            changeAlpha(g2, alphaValue);
//        }
    }
}
