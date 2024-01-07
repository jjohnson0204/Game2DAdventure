package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {
    public BufferedImage lastImage = null;
    public KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //solidArea(x,y) solidAreaDefault(x,y) solidArea(w,h)
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
    }
    public void setDefaultValues() {
        // player position on the map
        worldX = gp.tileSize * 16;
        worldY = gp.tileSize * 9;
        defaultSpeed = 4;
        speed = defaultSpeed;
//      speed = gp.worldWidth/700.5;
        direction = "down";

        //Player Status
        type = type_player;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Aura(gp);
        projectile2 = new OBJ_AuraBall(gp);
        attack = getAttack();
        defense = getDefense();

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }
    public void setDefaultPositions(){
        worldX = gp.tileSize * 16;
        worldY = gp.tileSize * 9;
        direction = "down";
    }
    public void setDialogue() {
        dialogues[0][0] = "You are level " + level + " now!\n" + "You are getting stronger!";

    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;

    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_duration =currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        up3 = image;
        down1 = image;
        down2 = image;
        down3 = image;
        left1 = image;
        left2 = image;
        left3 = image;
        right1 = image;
        right2 = image;
        right3 = image;
    }
    public void getAttackImage(){
        if(currentWeapon.type == type_sword){
            attackUp1 = setup2("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup2("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup2("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup2("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup2("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup2("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup2("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup2("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setup2("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup2("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup2("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup2("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup2("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup2("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup2("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup2("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setup2("/player/boy_pick_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup2("/player/boy_pick_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup2("/player/boy_pick_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup2("/player/boy_pick_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup2("/player/boy_pick_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup2("/player/boy_pick_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup2("/player/boy_pick_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup2("/player/boy_pick_right_2", gp.tileSize*2, gp.tileSize);
        }

    }
    public void getGuardImage(){
        guardUp = setup("/player/boy_guard_up");
        guardDown = setup("/player/boy_guard_down");
        guardLeft = setup("/player/boy_guard_left");
        guardRight = setup("/player/boy_guard_right");

    }
    public void update() {
        if(knockBack){
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else {
                switch (knockBackDirection) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking){
            attacking();
        }
        else if (keyH.spacePressed) {
            guarding = true;
            guardCounter++;
        }
        else if(keyH.upPressed || keyH.downPressed | keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }
            else if (keyH.downPressed) {
                direction = "down";
            }
            else if (keyH.leftPressed) {
                direction = "left";
            }
            else if (keyH.rightPressed) {
                direction = "right";
            } else {
                direction = "";
            }
            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            burstCounter++;
            if(burstCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                burstCounter = 0;
            }
            //Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //Check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //Check Interactive Tile Collision
            gp.cChecker.checkEntity(this, gp.iTile);

            //Check event collision
            gp.eHandler.checkEvent();

            //If collision is false. player can move
            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            if(keyH.enterPressed && !attackCanceled){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
                burstCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;
        }
        if(
                gp.keyH.shotKeyPressed
                && !projectile.alive
                && shotAvailableCounter == 30
                && projectile.haveResource(this)
        ){
            // Set default coordinates, direction and user
            projectile.set((int) worldX, (int) worldY, direction, true, this);
            // Subtract the cost to use projectile
            projectile.subtractResource(this);
            // Check Vacancy
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            // SE for fireball
            gp.playSE(10);
        }
        if(
                gp.keyH.burstKeyPressed
                        && !projectile2.alive
                        && burstAvailableCounter == 30
                        && projectile2.haveResource(this)
        ){
            // Set default coordinates, direction and user
            projectile2.set((int) worldX, (int) worldY, direction, true, this);
            // Subtract the cost to use projectile
            projectile2.subtractResource(this);
            // Check Vacancy
            for (int i = 0; i < gp.projectile2[1].length; i++) {
                if(gp.projectile2[gp.currentMap][i] == null){
                    gp.projectile2[gp.currentMap][i] = projectile2;
                    break;
                }
            }
            burstAvailableCounter = 0;
            // SE for fireball
            gp.playSE(10);
        }
        // Outside of key if statement
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter >60){
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        // Can only shoot one fireball every 30 frames
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        // Can only shoot one burst every 30 frames
        if(burstAvailableCounter < 30){
            burstAvailableCounter++;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(12);
        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower){
        if(i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.playSE(5);
                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }
                if (gp.monster[gp.currentMap][i].offBalance) {
                    attack *= 5;
                }
                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i){
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible
                && gp.iTile[gp.currentMap][i].isCorrectItem(this)
                && !gp.iTile[gp.currentMap][i].invincible){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            // Generate Particle
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i].checkDrop(); //Walls drop items
                gp.iTile[gp.currentMap][i] =  gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
        if(i != 999){
            Entity projectile2 = gp.projectile2[gp.currentMap][i];
            projectile2.alive = false;
            generateParticle(projectile2, projectile2);
        }
    }
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this,0);
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword
                    || selectedItem.type == type_axe
                    || selectedItem.type == type_pickaxe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){
                if(currentLight == selectedItem) {
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_consumable){
               if(selectedItem.use(this)) {
                   if(selectedItem.amount > 1) {
                       selectedItem.amount--;
                   }
                   else {
                       inventory.remove(itemIndex);
                   }
               }
            }
        }
    }
    public void contactMonster(int i){
        if(i != 999){
            if(!invincible && !gp.monster[gp.currentMap][i].dying){
                gp.playSE(6);
                int damage = attack - gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }
    public void interactNPC(int i){
        if(i != 999){

            if(gp.keyH.enterPressed){
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);
        }
    }
    public void pickUpObject(int i){
        if(i != 999) {
            //Pickup Only Items
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // Obstacle
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(keyH.enterPressed){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // Inventory Items
            else {
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i])){
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "You can not carry any more items!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    public int searchItemInInventory(String itemName) {
        int intemIndex = 999;
        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)){
                intemIndex = i;
                break;
            }
        }
        return intemIndex;
    }
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;
        Entity newItem = gp.eGenerator.getObject(item.name);

        // Check if stackable
        if(newItem.stackable){
            int index = searchItemInInventory(newItem.name);
            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else { // Not stackable so check vacancy
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

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
                if (guarding){
                    image = guardUp;
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
                if (guarding){
                    image = guardDown;
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
                if (guarding){
                    image = guardRight;
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
                if (guarding){
                    image = guardLeft;
                }
                break;
            default:
                image = lastImage;
                break;
        }
//        lastImage = image;

        if(transparent){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)); // renders opacity to 60%
        }
        g2.drawImage(image, tempScreenX, tempScreenY, gp.tileSize, gp.tileSize, null);

        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug damage
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
    public void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/player/npc005.png", 32, 32, 3, 4);
        down1 = sprite.getSprite(0, 0);
        down2 = sprite.getSprite(2,0);
        left1 = sprite.getSprite(0, 1);
        left2 = sprite.getSprite(2, 1);
        right1 = sprite.getSprite(0, 2);
        right2 = sprite.getSprite(2, 2);
        up1 = sprite.getSprite(0, 3);
        up2 = sprite.getSprite(2,3);
    }
}
