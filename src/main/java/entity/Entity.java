package entity;
// The parent class for the Player, monster and NPC classes
// Stores the variables that will be used
import main.GamePanel;
import main.UtilityTool;
import object.util.OBJ_Teleporter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
public class Entity {
    GamePanel gp;
    public BufferedImage abilityIcon;
    public BufferedImage up1, up2, up3,up4,
            down1, down2, down3, down4,
            left1, left2, left3,left4,
            right1, right2, right3, right4;

    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea =  new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public String[][] dialogues = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;
    public boolean temp = false;

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
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean sleep = false;
    public boolean drawing = true;

    // Counter
    public int spriteCounter = 0;
    public int skillCounter = 0;
    public int burstCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public int burstAvailableCounter = 0;
    public int skillAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
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

    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public boolean boss;

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
    protected int x, y, width, height;


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
    public final int type_staff = 11;
    public final int type_bow = 12;
    public final int type_glove = 13;
    public final int type_dagger = 14;
    public final int type_teleporter = 15;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void resetCounter() {
        spriteCounter = 0;
        burstCounter = 0;
        skillCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        skillAvailableCounter = 0;
        burstAvailableCounter = 0;
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

            switch (gp.players[gp.selectedPlayerIndex].direction){
                case "up": gp.players[gp.selectedPlayerIndex].direction = "down"; break;
                case "down": gp.players[gp.selectedPlayerIndex].direction = "up"; break;
                case "left": gp.players[gp.selectedPlayerIndex].direction = "right"; break;
                case "right": gp.players[gp.selectedPlayerIndex].direction = "left"; break;
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
            if(gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void moveTowardPlayer(int interval) {
        actionLockCounter++;
        if (actionLockCounter > interval) {
            //Which is longer distance
            //If x distance longer move left or right
            if (getXdistance(gp.players[gp.selectedPlayerIndex]) > getYdistance(gp.players[gp.selectedPlayerIndex])) {
                direction = gp.players[gp.selectedPlayerIndex].getCenterX() < getCenterX() ? "left" : "right";
            }
            //If y distance longer move up or down
            else if (getXdistance(gp.players[gp.selectedPlayerIndex]) < getYdistance(gp.players[gp.selectedPlayerIndex])) {
                direction = gp.players[gp.selectedPlayerIndex].getCenterY() < getCenterY() ? "up" : "down";
            }
            actionLockCounter = 0;
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

        if(spriteCounter <= motion1_duration
                || burstCounter <= motion1_duration
                || skillCounter <= motion1_duration){
            spriteNum = 1;
        }
        if((spriteCounter > motion1_duration
                || burstCounter <= motion1_duration
                || skillCounter <= motion1_duration)
                && (spriteCounter <= motion2_duration
                || burstCounter <= motion2_duration
                || skillCounter <= motion2_duration)){
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
                gp.players[gp.selectedPlayerIndex].damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.players[gp.selectedPlayerIndex].damageProjectile(projectileIndex);

                int projectile2Index = gp.cChecker.checkEntity(this, gp.projectile2);
                gp.players[gp.selectedPlayerIndex].damageProjectile(projectile2Index);

                int projectile3Index = gp.cChecker.checkEntity(this, gp.projectile3);
                gp.players[gp.selectedPlayerIndex].damageProjectile(projectile3Index);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.players[gp.selectedPlayerIndex].damageInteractiveTile(iTileIndex);
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
        if (skillCounter > motion2_duration){
            spriteNum = 1;
            skillCounter = 0;
            attacking = false;
        }
        if(burstCounter > motion2_duration){
            spriteNum = 1;
            burstCounter = 0;
            attacking = false;
        }
    }
    public void damagePlayer(int attack){
        if(!gp.players[gp.selectedPlayerIndex].invincible){
            int damage = attack - gp.players[gp.selectedPlayerIndex].defense;

            //Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);

            if (gp.players[gp.selectedPlayerIndex].guarding && gp.players[gp.selectedPlayerIndex].direction.equals(canGuardDirection)) {
                //Parry
                if (gp.players[gp.selectedPlayerIndex].guardCounter < 10) {
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.players[gp.selectedPlayerIndex], knockBackPower);
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
                gp.players[gp.selectedPlayerIndex].transparent = true;
                setKnockBack(gp.players[gp.selectedPlayerIndex], this, knockBackPower);
            }
            gp.players[gp.selectedPlayerIndex].life -= damage;
            gp.players[gp.selectedPlayerIndex].invincible = true;
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
        if (!sleep) {
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
                if(spriteCounter > 50){
                    if(spriteNum == 1){
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
                burstCounter++;
                if(burstCounter > 50){
                    if(spriteNum == 1){
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    burstCounter = 0;
                }
                skillCounter++;
                if(skillCounter > 50){
                    if(spriteNum == 1){
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    skillCounter = 0;
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
            // Can only shoot one skill every 30 frames
            if(skillAvailableCounter < 30){
                skillAvailableCounter++;
            }
            // Can only shoot one burst every 30 frames
            if(burstAvailableCounter < 30){
                burstAvailableCounter++;
            }
            if (offBalance) {
                offBalanceCounter++;
                if (offBalanceCounter > 60) {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] instanceof OBJ_Teleporter) {
                OBJ_Teleporter teleporter = (OBJ_Teleporter) gp.obj[gp.currentMap][i];
                if (teleporter.inCamera() && gp.cChecker.checkPlayerTeleporterCollision(gp.players[gp.selectedPlayerIndex], teleporter)) {
                    teleporter.interact("down"); // Assuming "down" is the direction the player is moving
                }
            }
        }
    }
    public void getRandomDirection(int interval) {
        actionLockCounter++;

        if(actionLockCounter > interval) {
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
        int xDis = getXdistance(gp.players[gp.selectedPlayerIndex]);
        int yDis = getYdistance(gp.players[gp.selectedPlayerIndex]);

        switch (direction){
            case "up": if (gp.players[gp.selectedPlayerIndex].getCenterY() < getCenterY() && yDis < straight && xDis < horizontal){targetInRange = true; } break;
            case "down": if (gp.players[gp.selectedPlayerIndex].getCenterY() > getCenterY() && yDis < straight && xDis < horizontal){targetInRange = true; } break;
            case "left": if (gp.players[gp.selectedPlayerIndex].getCenterX() < getCenterX()  && xDis < straight && yDis < horizontal){targetInRange = true; } break;
            case "right": if (gp.players[gp.selectedPlayerIndex].getCenterX()  > getCenterX()  && xDis < straight && yDis < horizontal){targetInRange = true; } break;
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
        if(i == 0 && !projectile1.alive && shotAvailableCounter == shotInterval){
            projectile1.set((int) worldX, (int) worldY, direction, true, this);
            // Check Vacancy
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if(gp.projectile[gp.currentMap][ii] == null){
                    gp.projectile[gp.currentMap][ii] = projectile1;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
        /*else if(i == 0 && !projectile2.alive && burstAvailableCounter == shotInterval){
            projectile2.set((int) worldX, (int) worldY, direction, true, this);
            // Check Vacancy
            for (int ii = 0; ii < gp.projectile2[1].length; ii++) {
                if(gp.projectile2[gp.currentMap][ii] == null){
                    gp.projectile2[gp.currentMap][ii] = projectile2;
                    break;
                }
            }
            burstAvailableCounter = 0;
        }*/
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
    public boolean inCamera () {
        boolean inCamera = false;

        if(worldX + gp.tileSize * 5> gp.players[gp.selectedPlayerIndex].worldX - gp.players[gp.selectedPlayerIndex].screenX
                && worldX - gp.tileSize < gp.players[gp.selectedPlayerIndex].worldX + gp.players[gp.selectedPlayerIndex].screenX
                && worldY + gp.tileSize * 5 > gp.players[gp.selectedPlayerIndex].worldY - gp.players[gp.selectedPlayerIndex].screenY
                && worldY - gp.tileSize< gp.players[gp.selectedPlayerIndex].worldY + gp.players[gp.selectedPlayerIndex].screenY) {
            inCamera = true;
        }
        return inCamera;
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        // Boundaries
        if(inCamera() ) {

            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up":
                    if(!attacking){
                        if(spriteNum == 1) { image = up1;}
                        if(spriteNum == 2) { image = up2;}
                        if(spriteNum == 3) { image = up3;}
                        if(spriteNum == 4) { image = up4;}
                    }
                    if(attacking){
                    tempScreenY = (getScreenY() - up1.getHeight());
                        if(spriteNum == 1) { image = attackUp1;}
                        if(spriteNum == 2) { image = attackUp2;}
                    }
                    break;
                case "down":
                    if(!attacking){
                        if(spriteNum == 1) { image = down1;}
                        if(spriteNum == 2) { image = down2;}
                        if(spriteNum == 3) { image = down3;}
                        if(spriteNum == 4) { image = down4;}
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
                        if(spriteNum == 3) { image = right3;}
                        if(spriteNum == 4) { image = right4;}
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
                        if(spriteNum == 3) { image = left3;}
                        if(spriteNum == 4) { image = left4;}
                    }
                    if(attacking){
                        tempScreenX =  (getScreenX() - left1.getWidth());
                        if(spriteNum == 1) { image = attackLeft1;}
                        if(spriteNum == 2) { image = attackLeft2;}
                    }
                    break;
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
            case "up": nextWorldY = (int) (user.getTopY() - gp.players[gp.selectedPlayerIndex].speed); break;
            case "down": nextWorldY = (int) (user.getBottomY() + gp.players[gp.selectedPlayerIndex].speed); break;
            case "left": nextWorldX = (int) (user.getLeftX() - gp.players[gp.selectedPlayerIndex].speed); break;
            case "right": nextWorldX = (int) (user.getRightX() + gp.players[gp.selectedPlayerIndex].speed); break;
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
    public int getScreenX() {
        int screenX = (int) (worldX - gp.players[gp.selectedPlayerIndex].worldX + gp.players[gp.selectedPlayerIndex].screenX);
        return screenX;
    }
    public int getScreenY() {
        int screenY = (int) (worldY - gp.players[gp.selectedPlayerIndex].worldY + gp.players[gp.selectedPlayerIndex].screenY);
        return screenY;
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
    public int getCenterX() {
        int centerX = 0;
        if (left1 != null) {
            centerX = (int) (worldX + left1.getWidth() / 2);
        }
        return centerX;
    }
    public int getCenterY() {
        int centerY = 0;
        if (up1 != null) {
          centerY = (int) (worldY + up1.getHeight() / 2);
        }
        return centerY;
    }
    public int getXdistance(Entity target){
        return (int) Math.abs(getCenterX() - target.getCenterX());
    }
    public int getYdistance(Entity target){
        return (int) Math.abs(getCenterY() - target.getCenterY());
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

    public void explode(int x, int y) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) { // Generate 100 particles
            int vx = random.nextInt(21) - 10; // Random velocity between -10 and 10
            int vy = random.nextInt(21) - 10;
            Color color = Color.RED; // Color of the explosion
            int size = 5; // Size of the particles
            int life = random.nextInt(50) + 50; // Lifespan between 50 and 100
            Particle particle = new Particle(gp,x, y, vx, vy, color, size, life);
            gp.particleList.add(particle);
        }
    }
}
