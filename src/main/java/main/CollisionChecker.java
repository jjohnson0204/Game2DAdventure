package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity, int layer) {

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
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityLeftCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityRightCol][(int) entityTopRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = ((entityTopWorldY + entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityLeftCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityRightCol][(int) entityTopRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = ((entityRightWorldX - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityLeftCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityRightCol][(int) entityTopRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = ((entityLeftWorldX - entity.speed) / gp.tileSize);
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityLeftCol][(int) entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][layer][(int) entityRightCol][(int) entityTopRow];

                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }

    }
    public int checkObject(Entity entity, boolean player){

        int index = 999;

        for (int i = 0; i < gp.obj[1].length; i++) {
            for(int currentLayer = 0; currentLayer < gp.numberOfLayers; currentLayer ++) {
                if(gp.obj[gp.currentMap][currentLayer] != null && gp.obj[gp.currentMap][currentLayer][i] != null){
                        // get entities solid area position
                        entity.solidArea.x = (int) (entity.worldX + entity.solidArea.x);
                        entity.solidArea.y = (int) (entity.worldY + entity.solidArea.y);
                        // get the objects solid area position
                        gp.obj[gp.currentMap][currentLayer][i].solidArea.x = (int) (gp.obj[gp.currentMap][currentLayer][i].worldX + gp.obj[gp.currentMap][currentLayer][i].solidArea.x);
                        gp.obj[gp.currentMap][currentLayer][i].solidArea.y = (int) (gp.obj[gp.currentMap][currentLayer][i].worldY + gp.obj[gp.currentMap][currentLayer][i].solidArea.y);

                        switch (entity.direction){
                            case "up": entity.solidArea.y -= entity.speed; break;
                            case "down": entity.solidArea.y += entity.speed; break;
                            case "left": entity.solidArea.x -= entity.speed; break;
                            case "right": entity.solidArea.x += entity.speed; break;
                        }
                        if(entity.solidArea.intersects(gp.obj[gp.currentMap][currentLayer][i].solidArea)){
                            if(gp.obj[gp.currentMap][currentLayer][i].collisionOn){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = i;
                            }
                        }
                        entity.solidArea.x = entity.solidAreaDefaultX;
                        entity.solidArea.y = entity.solidAreaDefaultY;
                        gp.obj[gp.currentMap][currentLayer][i].solidArea.x = gp.obj[gp.currentMap][currentLayer][i].solidAreaDefaultX;
                        gp.obj[gp.currentMap][currentLayer][i].solidArea.y = gp.obj[gp.currentMap][currentLayer][i].solidAreaDefaultY;
                    }
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
}
