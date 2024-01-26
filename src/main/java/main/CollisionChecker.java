package main;

import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity) {

        double entityLeftWorldX = (entity.worldX + entity.solidArea.x);
        double entityRightWorldX = (entity.worldX + entity.solidArea.x + entity.solidArea.width);
        double entityTopWorldY = (entity.worldY + entity.solidArea.y);
        double entityBottomWorldY = (entity.worldY + entity.solidArea.y + entity.solidArea.height);

        double entityLeftCol = (entityLeftWorldX / gp.tileSize);
        double entityRightCol = (entityRightWorldX / gp.tileSize);
        double entityTopRow = (entityTopWorldY / gp.tileSize);
        double entityBottomRow = (entityBottomWorldY / gp.tileSize);

        int tileNum1, tileNum2;

        //Use a temporal direction when it's being knocked back
        String direction = entity.direction;
        if (entity.knockBack) {
            direction = entity.knockBackDirection;
        }

        switch (direction) {
            case "up":
                entityTopRow = ((entityTopWorldY - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][(int) entityLeftCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][(int) entityRightCol][(int) entityTopRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = ((entityBottomWorldY + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][(int) entityLeftCol][(int) entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][(int) entityRightCol][(int) entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = ((entityRightWorldX + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][(int) entityRightCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][(int) entityRightCol][(int) entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = ((entityLeftWorldX - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][(int) entityLeftCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][(int) entityLeftCol][(int) entityBottomRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }

    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        //Use a temporal direction when it's being knocked back
        String direction = entity.direction;
        if (entity.knockBack) {
            direction = entity.knockBackDirection;
        }

        for (int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] != null){
                // get entities solid area position
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
                // get the objects solid area position
                gp.obj[gp.currentMap][i].solidArea.x = (int) (gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x);
                gp.obj[gp.currentMap][i].solidArea.y = (int) (gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y);

                switch (direction){
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if(gp.obj[gp.currentMap][i].collisionOn){
                        entity.collisionOn = true;
                    }
                    if(player){
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[][] target){

        int index = 999;

        //Use a temporal direction when it's being knocked back
        String direction = entity.direction;
        if (entity.knockBack) {
            direction = entity.knockBackDirection;
        }

        for (int i = 0; i < target[1].length; i++) {
            if(target[gp.currentMap][i] != null){
                // get entities solid area position
                entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
                // get the objects solid area position
                target[gp.currentMap][i].solidArea.x = (int) (target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x);
                target[gp.currentMap][i].solidArea.y = (int) (target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y);

                switch (direction){
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity){
        boolean contactPlayer = false;

        // get entities solid area position
        entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
        entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
        // get the objects solid area position
        gp.player.solidArea.x = (int) (gp.player.worldX + gp.player.solidArea.x);
        gp.player.solidArea.y = (int) (gp.player.worldY + gp.player.solidArea.y);

        switch (entity.direction){
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }
        if(entity.solidArea.intersects(gp.player.solidArea)){
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
    public boolean checkPlayerTeleporterCollision(Entity player, Entity teleporter) {
        // get player's solid area position
        player.solidArea.x = (int) (player.worldX + player.solidArea.x);
        player.solidArea.y = (int) (player.worldY + player.solidArea.y);
        // get teleporter's solid area position
        teleporter.solidArea.x = (int) (teleporter.worldX + teleporter.solidArea.x);
        teleporter.solidArea.y = (int) (teleporter.worldY + teleporter.solidArea.y);

        // check if player's solid area intersects with teleporter's solid area
        boolean collision = player.solidArea.intersects(teleporter.solidArea);

        // reset solid area positions
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        teleporter.solidArea.x = teleporter.solidAreaDefaultX;
        teleporter.solidArea.y = teleporter.solidAreaDefaultY;

        return collision;
    }
}
