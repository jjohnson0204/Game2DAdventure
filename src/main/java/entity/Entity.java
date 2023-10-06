package entity;

// The parent class for the Player, monster and NPC classes
// Stores the variables that will be used

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public BufferedImage up1, up2, up3,
            down1, down2, down3,
            left1, left2, left3,
            right1, right2, right3;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea =  new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public String[][] dialogues = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;

    // State
    public double worldX,worldY;
    public String direction = "down";
    public String knockBackDirection;
    public int spriteNum = 1;
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;

    // Counter
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;

    // Character Attributes
    public String name;
    public int defaultSpeed;
    public double speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    // Item Attributes
    public ArrayList<Entity> inventory = new ArrayList<>();
    public boolean stackable = false;
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public int amount = 1;
    public int lightRadius;


    // Type
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void resetCounter() {
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }
    public void setLoot(Entity loot){}
    public void move(String direction){}
    public void setAction(){}
    public void damageReaction(){}
    public void speak(){

    }
    public void facePlayer() {
        switch (gp.player.direction){
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }
    public void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }
    public void interact(){}
    public boolean use(Entity entity){return false;}
    public void checkDrop(){}
    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public String getOppositeDirection(String direction){
        String oppositeDirection = "";

        switch (direction){
            case "up": oppositeDirection = "down"; break;
            case "down": oppositeDirection = "up"; break;
            case "left": oppositeDirection = "right"; break;
            case "right": oppositeDirection = "left"; break;
        }
        return oppositeDirection;
    }
    public void attacking(){
        spriteCounter++;

        if(spriteCounter <= motion1_duration){
            spriteNum = 1;
        }
        if((spriteCounter > motion1_duration) && (spriteCounter <= motion2_duration)){
            spriteNum = 2;
            //Save the current worldX/Y and solidArea
            int currentWorldX = (int) worldX;
            int currentWorldY = (int) worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //Adjust players worldX/Y for the attackArea
            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == type_monster) {
                if (gp.cChecker.checkPlayer(this)) {
                    damagePlayer(attack);
                }
            }
            else { // Player
                //Check monster collision
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);
            }


            //After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > motion2_duration){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            int damage = attack - gp.player.defense;

            //Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);

            if (gp.player.guarding && gp.player.direction.equals(canGuardDirection)) {
                //Parry
                if (gp.player.guardCounter < 10) {
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60; // Stun
                }
                else {
                    //Normal guard
                    damage /= 3;
                    gp.playSE(15);
                }
            }
            else {
                //Not guarding
                gp.playSE(6);
                if (damage < 1) {
                    damage = 1;
                }
            }
            if (damage != 0) {
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void setKnockBack(Entity target, Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp,target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp,target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp,target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp,target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(type == type_monster && contactPlayer){
            damagePlayer(attack);
        }

    }
    public void update(){
        if(knockBack){
            checkCollision();
            if (!collisionOn) {
                switch (knockBackDirection) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            } else {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if (attacking) {
            attacking();
        }
        else {
            setAction();
            checkCollision();

            //If collision is false, can move
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 24){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter >40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        // Can only shoot one fireball every 30 frames
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if (offBalance) {
            offBalanceCounter++;
            if (offBalanceCounter > 60) {
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
    }
    public void getRandomDirection() {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1 to 100
            if(i <= 25){ direction = "up"; }
            if(i > 25 && i <= 50){ direction = "down"; }
            if(i >50 && i <= 75){ direction = "left"; }
            if(i > 75){ direction = "right"; }
            actionLockCounter = 0;
        }
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (direction){
            case "up": if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal){targetInRange = true; } break;
            case "down": if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal){targetInRange = true; } break;
            case "left": if (gp.player.worldX < worldX && xDis < straight && yDis < horizontal){targetInRange = true; } break;
            case "right": if (gp.player.worldX > worldX && xDis < straight && yDis < horizontal){targetInRange = true; } break;
        }
        if (targetInRange) {
            //Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if(i == 0 && !projectile.alive && shotAvailableCounter == shotInterval){
            projectile.set((int) worldX, (int) worldY, direction, true, this);
            // Check Vacancy
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if(gp.projectile[gp.currentMap][ii] == null){
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if(getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = true;
            }
        }
    }
    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if(getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = false;
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        double screenX = worldX - gp.player.worldX + gp.player.screenX;
        double screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Boundaries
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) {

            int tempScreenX = (int) screenX;
            int tempScreenY = (int) screenY;

            switch (direction) {
                case "up":
                    if(!attacking){
                        if(spriteNum == 1) { image = up1;}
                        if(spriteNum == 2) { image = up2;}
                    }
                    if(attacking){
//                    tempScreenY = screenY - gp.tileSize;
                        if(spriteNum == 1) { image = attackUp1;}
                        if(spriteNum == 2) { image = attackUp2;}
                    }
                    break;
                case "down":
                    if(!attacking){
                        if(spriteNum == 1) { image = down1;}
                        if(spriteNum == 2) { image = down2;}
                    }
                    if(attacking){
                        if(spriteNum == 1) { image = attackDown1;}
                        if(spriteNum == 2) { image = attackDown2;}
                    }
                    break;
                case "right":
                    if(!attacking){
                        if(spriteNum == 1) { image = right1;}
                        if(spriteNum == 2) { image = right2;}
                    }
                    if(attacking){
                        if(spriteNum == 1) { image = attackRight1;}
                        if(spriteNum == 2) { image = attackRight2;}
                    }
                    break;
                case "left":
                    if(!attacking){
                        if(spriteNum == 1) { image = left1;}
                        if(spriteNum == 2) { image = left2;}
                    }
                    if(attacking){
//                    tempScreenX = screenX - gp.tileSize;
                        if(spriteNum == 1) { image = attackLeft1;}
                        if(spriteNum == 2) { image = attackLeft2;}
                    }
                    break;
            }

            //Monster HP Bar
            if(type == type_monster && hpBarOn){
                double oneScale = (double)gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                //Outer Rect
                g2.setColor(new Color(35, 35,35));
                g2.fillRect((int) screenX-1, (int) screenY-16, gp.tileSize+2, 12);
                //Inner Rect
                g2.setColor(new Color(255, 0,30));
                g2.fillRect((int) screenX, (int) (screenY -15), (int) hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f); // renders opacity to 60%
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            //Reset alpha
            changeAlpha(g2, 1f);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
//        double ratio = (double) dyingCounter / 5;
//        int count = (int) Math.ceil( ratio );
//        if(dyingCounter > 40){
//            dying = false;
//            alive = false;
//        }
//        else if( count % 2 == 0) {
//            changeAlpha(g2, 1f);
//        }else {
//            changeAlpha(g2, 0f);
//        }
        if(dyingCounter <= 5){ changeAlpha(g2, 0f); }
        if(dyingCounter > i && dyingCounter <=i * 2){ changeAlpha(g2, 1f); }
        if(dyingCounter > i *2 && dyingCounter <= i * 3){ changeAlpha(g2, 0f); }
        if(dyingCounter > i * 3 && dyingCounter <= i * 4){ changeAlpha(g2, 1f); }
        if(dyingCounter > i * 4 && dyingCounter <= i * 5){ changeAlpha(g2, 0f); }
        if(dyingCounter > i * 5 && dyingCounter <= i * 6){ changeAlpha(g2, 1f); }
        if(dyingCounter > i * 6 && dyingCounter <= i * 7){ changeAlpha(g2, 0f); }
        if(dyingCounter > i * 7 && dyingCounter <= i * 8){ changeAlpha(g2, 1f); }
        if(dyingCounter > i * 8){
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public BufferedImage setup2(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaledImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (int) ((worldX + solidArea.x) / gp.tileSize);
        int startRow = (int) ((worldY + solidArea.y) / gp.tileSize);

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()){
            //Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            // Entity's solidArea position
            int enLeftX = (int) (worldX + solidArea.x);
            int enRightX = (int) (worldX + solidArea.x + solidArea.width);
            int enTopY = (int) (worldY + solidArea.y);
            int enBottomY = (int) (worldY + solidArea.y + solidArea.height);

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                // Left or Right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                // Up or Left
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                // Up or Right
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                // Down or Left
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                // Down or Right
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }
        }
    }
    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;

        // Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        switch (user.direction) {
            case "up": nextWorldY = (int) (user.getTopY() - gp.player.speed); break;
            case "down": nextWorldY = (int) (user.getBottomY() + gp.player.speed); break;
            case "left": nextWorldX = (int) (user.getLeftX() - gp.player.speed); break;
            case "right": nextWorldX = (int) (user.getRightX() + gp.player.speed); break;
        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for (int i = 0; i < target[1].length; i++) {
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col
                && target[gp.currentMap][i].getRow() == row
                && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    public int getLeftX(){
        return (int) (worldX + solidArea.x);
    }
    public int getRightX(){
        return (int) (worldX + solidArea.x + solidArea.width);
    }
    public int getTopY(){
        return (int) (worldY + solidArea.y);
    }
    public int getBottomY(){
        return (int) (worldY + solidArea.y + solidArea.height);
    }
    public int getCol(){
        return (int) ((worldX + solidArea.x) / gp.tileSize);
    }
    public int getRow(){
        return (int) ((worldY + solidArea.y) / gp.tileSize);
    }
    public int getXdistance(Entity target){
        return (int) Math.abs(worldX - target.worldX);
    }
    public int getYdistance(Entity target){
        return (int) Math.abs(worldY - target.worldY);
    }
    public int getTileDistance(Entity target){
        return (getXdistance(target) + getYdistance(target)) / gp.tileSize;
    }
    public int getGoalCol(Entity target){
        return (int) (target.worldX + target.solidArea.x) / gp.tileSize;
    }
    public int getGoalRow(Entity target){
        return (int) (target.worldY + target.solidArea.y) / gp.tileSize;
    }
}
