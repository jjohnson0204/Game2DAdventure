package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UI;
import object.*;
import object.abilities.hunter.OBJ_CommonArrows;
import object.abilities.mage.OBJ_Aura;
import object.abilities.mage.OBJ_AuraBall;
import object.abilities.mage.OBJ_AuraNado;
import object.util.OBJ_Teleporter;
import object.weapons.*;
import object.weapons.assassin.*;
import object.weapons.fighter.*;
import object.weapons.hunter.*;
import object.weapons.mage.*;
import object.weapons.warrior.OBJ_Legendary_Sword;
import object.weapons.warrior.OBJ_Shield_Wood;
import object.weapons.warrior.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    public BufferedImage lastImage = null;
    public KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
    public boolean isThunderShieldActive = false;
    public String playerType;
    protected boolean isPoisoned;
    protected boolean isStunned;
    protected boolean isBleeding;
    protected boolean isBuffed;
    protected boolean isDebuffed;
    protected boolean isHealing;
    protected List<String> quests;
    protected List<String> achievements;

    // Add this list to store the active projectiles
    private List<TargetedProjectile> activeProjectiles = new ArrayList<>();
    private List<OBJ_Teleporter> teleports = new ArrayList<>();

    public Player(GamePanel gp, KeyHandler keyH, int i) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        //solidArea(x,y) solidAreaDefault(x,y) solidArea(w,h)
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 18;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setPlayerType(i);
        setDefaultValues();
    }
    public void setPlayerType(int selectedPlayerIndex) {
        switch (selectedPlayerIndex) {
            case 0:
                this.playerType = "Fighter";
                break;
            case 1:
                this.playerType = "Warrior";
                break;
            case 2:
                this.playerType = "Hunter";
                break;
            case 3:
                this.playerType = "Assassin";
                break;
            case 4:
                this.playerType = "Mage";
                break;
            default:
                throw new IllegalArgumentException("Invalid player index: " + selectedPlayerIndex);
        }
    }
    public void setDefaultValues() {
        // player position on the map
        worldX = gp.tileSize * 16;
        worldY = gp.tileSize * 9;
        defaultSpeed = 3;
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
        currentLight = null;
        this.setCharacterEquipment(playerType);
        this.currentShield = new OBJ_Shield_Wood(gp);
        this.projectile1 = new Projectile(gp);
        this.projectile2 = new Projectile(gp);
        this.projectile3 = new Projectile(gp);
        this.attack = getAttack();
        this.defense = getDefense();
        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();

    }
    public void setDefaultPositions() {
        gp.currentMap = 0;
        worldX = gp.tileSize * 16;
        worldY = gp.tileSize * 9;
        direction = "down";
    }
    public void setDialogue() {
        dialogues[0][0] = "You are level " + level + " now!\n" + "You are getting stronger!";

    }
    public void restoreStatus() {
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
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));
//        inventory.add(new OBJ_Key(gp));
//        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == gp.players[gp.selectedPlayerIndex].currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == gp.players[gp.selectedPlayerIndex].currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getSleepingImage(BufferedImage image) {
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
    public void getAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setup2("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup2("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup2("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup2("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup2("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup2("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup2("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup2("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_axe) {
            attackUp1 = setup2("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup2("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup2("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup2("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup2("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup2("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup2("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup2("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_pickaxe) {
            attackUp1 = setup2("/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup2("/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup2("/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup2("/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup2("/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup2("/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup2("/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup2("/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_bow) {
            SpriteSheet sprite = new SpriteSheet("src/resources/player/femalehunterattack.png",64,64,4,1);
            attackUp1 = sprite.getSprite(2, 0);
            attackUp2 = sprite.getSprite(3, 0);
            attackDown1 = sprite.getSprite(2, 0);
            attackDown2 = sprite.getSprite(3, 0);
            attackLeft1 = sprite.getSprite(2, 0);
            attackLeft2 = sprite.getSprite(3, 0);
            attackRight1 = sprite.getSprite(2, 0);
            attackRight2 = sprite.getSprite(3, 0);
        }
    }
    public void getGuardImage() {
        guardUp = setup("/player/boy_guard_up");
        guardDown = setup("/player/boy_guard_down");
        guardLeft = setup("/player/boy_guard_left");
        guardRight = setup("/player/boy_guard_right");

    }
    public void update() {
        if (knockBack) {
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            if (collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else {
                switch (knockBackDirection) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }
            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else if (attacking) {
            attacking();
        } else if (keyH.spacePressed) {
            guarding = true;
            guardCounter++;
        } else if (keyH.upPressed || keyH.downPressed | keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else {
                direction = "";
            }
            spriteCounter++;
            if (spriteCounter > 24) {
                if(spriteNum == 1){
                    spriteNum = 2;
                } else if(spriteNum == 2){
                    spriteNum = 3;
                } else if(spriteNum == 3){
                    spriteNum = 4;
                } else if(spriteNum == 4){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
//            burstCounter++;
//            if (burstCounter > 50) {
//                spriteNum++;
//                if (spriteNum > 9) {
//                    spriteNum = 1;
//                }
//                burstCounter = 0;
//            }
//            skillCounter++;
//            if (skillCounter > 50) {
//                spriteNum++;
//                if (spriteNum > 9) {
//                    spriteNum = 1;
//                }
//                skillCounter = 0;
//            }
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
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }

            if (keyH.enterPressed && !attackCanceled) {
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
        if (
                gp.keyH.shotKeyPressed
                        && !projectile1.alive
                        && shotAvailableCounter == 30
                        && projectile1.haveResource(this)
        ) {
            // If playerType is Hunter, get the attack image for the bow
//            if (playerType.equals("Hunter") && currentWeapon instanceof OBJ_Common_Bow) {
//                getAttackImage();
//            }
            // Set default coordinates, direction and user
            projectile1.set((int) worldX, (int) worldY, direction, true, this);
            // Subtract the cost to use projectile
            projectile1.subtractResource(this);
            // Check Vacancy
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile1;
                    break;
                }
            }
            shotAvailableCounter = 0;
            // SE for fireball
            gp.playSE(10);
        }
        if (
                gp.keyH.skillKeyPressed
                        && !projectile2.alive
                        && skillAvailableCounter == 30
                        && projectile2.haveResource(this)
        ) {
            // Set default coordinates, direction and user
            projectile2.set((int) worldX, (int) worldY, direction, true, this);
            // Subtract the cost to use projectile
            projectile2.subtractResource(this);
            // Check Vacancy
            for (int i = 0; i < gp.projectile2[1].length; i++) {
                if (gp.projectile2[gp.currentMap][i] == null) {
                    gp.projectile2[gp.currentMap][i] = projectile2;
                    break;
                }
            }
            skillAvailableCounter = 0;
            // SE for fireball
            gp.playSE(10);
        }
        if (
                gp.keyH.burstKeyPressed
                        && !projectile3.alive
                        && burstAvailableCounter == 30
                        && projectile3.haveResource(this)
        ) {
            // Set default coordinates, direction and user
            projectile3.set((int) worldX, (int) worldY, direction, true, this);
            // Subtract the cost to use projectile
            projectile3.subtractResource(this);
            // Check Vacancy
            for (int i = 0; i < gp.projectile3[1].length; i++) {
                if (gp.projectile3[gp.currentMap][i] == null) {
                    gp.projectile3[gp.currentMap][i] = projectile3;
                    break;
                }
            }
            burstAvailableCounter = 0;
            // SE for fireball
            gp.playSE(10);
        }
        // Outside of key if statement
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        // Can only shoot one fireball every 30 frames
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        // Can only shoot one burst every 30 frames
        if (skillAvailableCounter < 30) {
            skillAvailableCounter++;
        }
        // Can only shoot one burst every 30 frames
        if (burstAvailableCounter < 30) {
            burstAvailableCounter++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        if (!keyH.godModeOn) {
            if (life <= 0) {
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum = -1;
                if (!gp.gameOverSoundPlayed) {
                    gp.playSE(12);
                    gp.gameOverSoundPlayed = true;
                }
                gp.stopMusic();
            }
        }
//        // Assign the images
        UI.ability1 = gp.players[gp.selectedPlayerIndex].projectile1.down1;
        UI.ability2 = gp.players[gp.selectedPlayerIndex].projectile2.down1;
        UI.ability3 = gp.players[gp.selectedPlayerIndex].projectile3.down1;
        // Check if player's location matches the location of the PickUpObject
//        if (this.worldX == myObject.worldX && this.worldY == myObject.worldY) {
//            // If it does, call the pickUp() method of the PickUpObject
//            myObject.pickUp();
//        }
        // Check for teleporter collision
        for (OBJ_Teleporter teleporter : teleports) {
            if (gp.cChecker.checkPlayerTeleporterCollision(this, teleporter)) {
                teleporter.interact("any"); // Assuming "down" is the direction the player is moving
            }
        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.playSE(5);
                if (knockBackPower > 0) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }
                if (gp.monster[gp.currentMap][i].offBalance) {
                    attack *= 5;
                }
                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[gp.currentMap][i].destructible
                && gp.iTile[gp.currentMap][i].isCorrectItem(this)
                && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            // Generate Particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i].checkDrop(); //Walls drop items
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
            explode(projectile.x, projectile.y);
        }
        if (i != 999) {
            Entity projectile2 = gp.projectile2[gp.currentMap][i];
            projectile2.alive = false;
            generateParticle(projectile2, projectile2);
            explode(projectile2.x, projectile2.y);
        }
        if (i != 999) {
            Entity projectile3 = gp.projectile3[gp.currentMap][i];
            projectile3.alive = false;
            generateParticle(projectile3, projectile3);
            explode(projectile3.x, projectile3.y);
        }
    }
    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
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
            startDialogue(this, 0);
        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword
                    || selectedItem.type == type_axe
                    || selectedItem.type == type_pickaxe
                    || selectedItem.type == type_staff
                    || selectedItem.type == type_bow
                    || selectedItem.type == type_dagger
                    || selectedItem.type == type_glove
            ) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if (selectedItem instanceof OBJ_Legendary_Staff) {
                setCurrentWeapon(selectedItem);
            }
            if (selectedItem instanceof OBJ_Legendary_Bow) {
                setCurrentWeapon(selectedItem);
            }
            if (selectedItem instanceof OBJ_Legendary_Sword) {
                setCurrentWeapon(selectedItem);
            }
            if (selectedItem instanceof OBJ_Legendary_Dagger) {
                setCurrentWeapon(selectedItem);
            }
            if (selectedItem instanceof OBJ_Legendary_Glove) {
                setCurrentWeapon(selectedItem);
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_light) {
                if (currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if (selectedItem.type == type_consumable) {
                if (selectedItem.use(this)) {
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && isThunderShieldActive && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(6);
                int damage = attack - gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 1) {
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }
    public void interactNPC(int i) {
        if (i != 999) {

            if (gp.keyH.enterPressed) {
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);
        }
    }
    public void pickUpObject(int i) {
        //you have to change them all in the future to take a layer and pick up that. pickUpObject(int i, int layer)
        if (i != 999) {
            //Pickup Only Items
            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // Obstacle
            else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
                if (keyH.enterPressed) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // Inventory Items
            else {
                String text;
                if (canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
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
            if (inventory.get(i).name.equals(itemName)) {
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
        if (newItem.stackable) {
            int index = searchItemInInventory(newItem.name);
            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            } else {
                if (inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        } else { // Not stackable so check vacancy
            if (inventory.size() != maxInventorySize) {
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
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                    if(spriteNum == 4) { image = up4;}
                }
                if (attacking) {
//                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                if (guarding) {
                    image = guardUp;
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                    if(spriteNum == 4) { image = down4;}
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                if (guarding) {
                    image = guardDown;
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    if(spriteNum == 4) { image = right4;}
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                if (guarding) {
                    image = guardRight;
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    if(spriteNum == 4) { image = left4;}
                }
                if (attacking) {
//                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                if (guarding) {
                    image = guardLeft;
                }
                break;
            default:
                image = lastImage;
                break;
        }
//        lastImage = image;

        if (transparent) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f)); // renders opacity to 60%
        }
        if (drawing) {
            g2.drawImage(image, tempScreenX, tempScreenY, gp.tileSize, gp.tileSize, null);
        }


        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug damage
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
    public void getImage() {
//        SpriteSheet sprite = new SpriteSheet("src/resources/player/npc005.png", 32, 32, 3, 5);
//        down1 = sprite.getSprite(0, 0);
//        down2 = sprite.getSprite(1, 0);
//        down3 = sprite.getSprite(2, 0);
//
//        left1 = sprite.getSprite(0, 1);
//        left2 = sprite.getSprite(1, 1);
//        left3 = sprite.getSprite(2, 1);
//
//        right1 = sprite.getSprite(0, 2);
//        right2 = sprite.getSprite(1, 2);
//        right3 = sprite.getSprite(2, 2);
//
//        up1 = sprite.getSprite(0, 3);
//        up2 = sprite.getSprite(1, 3);
//        up3 = sprite.getSprite(2, 3);

    }
    public void interactWithPickUpObject(PickUpObject obj) {
        // Call the pickUp method of the PickUpObject
        obj.pickUp();
    }
    public void setCharacterEquipment(String characterType) {
        switch (characterType) {
            case "Warrior":
                OBJ_Sword_Normal warriorWeapon = new OBJ_Sword_Normal(gp);
                setCurrentWeapon(warriorWeapon);
                setCurrentSword(warriorWeapon);
                currentWeapon = warriorWeapon;
                currentShield = new OBJ_Shield_Wood(gp);
                projectile1 = warriorWeapon.projectile1;
                projectile2 = warriorWeapon.projectile2;
                projectile3 = warriorWeapon.projectile3;
                break;
            case "Mage":
                OBJ_Staff_Air mageWeapon = new OBJ_Staff_Air(gp, "air");
                if (this.projectile1 == null) {
                    mageWeapon.projectile1 = new OBJ_Aura(gp);
                    this.projectile1 = mageWeapon.projectile1;
                }
                if (this.projectile2 == null) {
                    mageWeapon.projectile2 = new OBJ_AuraBall(gp);
                    this.projectile2 = mageWeapon.projectile2;
                }
                if (this.projectile3 == null) {
                    mageWeapon.projectile3 = new OBJ_AuraNado(gp);
                    this.projectile3 = mageWeapon.projectile3;
                }
                setCurrentWeapon(mageWeapon);
                setCurrentStaff(mageWeapon);

                break;
            case "Hunter":
                OBJ_Bow_Common hunterWeapon = new OBJ_Bow_Common(gp, "common");
                if (this.projectile1 == null) {
                    hunterWeapon.projectile1 = new OBJ_CommonArrows(gp);
                    this.projectile1 = hunterWeapon.projectile1;
                }
                if (this.projectile2 == null) {
                    hunterWeapon.projectile2 = new OBJ_CommonArrows(gp);
                    this.projectile2 = hunterWeapon.projectile2;
                }
                if (this.projectile3 == null) {
                    hunterWeapon.projectile3 = new OBJ_CommonArrows(gp);
                    this.projectile3 = hunterWeapon.projectile3;
                }
                setCurrentWeapon(hunterWeapon);
                setCurrentBow(hunterWeapon);
                currentWeapon = hunterWeapon;
                break;
            case "Assassin":
                OBJ_Dagger_Air assassinWeapon = new OBJ_Dagger_Air(gp, "air");
                setCurrentWeapon(assassinWeapon);
                setCurrentDagger(assassinWeapon);
                projectile1 = assassinWeapon.projectile1;
                projectile2 = assassinWeapon.projectile2;
                projectile3 = assassinWeapon.projectile3;
                break;
            case "Fighter":
                OBJ_Glove_Air fighterWeapon = new OBJ_Glove_Air(gp, "air");
                setCurrentWeapon(fighterWeapon);
                setCurrentGlove(fighterWeapon);
                projectile1 = fighterWeapon.projectile1;
                projectile2 = fighterWeapon.projectile2;
                projectile3 = fighterWeapon.projectile3;
                break;
            default:
                throw new IllegalArgumentException("Invalid character type: " + characterType);
        }
    }
    public void setCurrentWeapon(Object weapon) {
        if (playerType.equals("Warrior")) {
            if (!(weapon instanceof OBJ_Sword_Normal)) {
                throw new IllegalArgumentException("Invalid weapon type. Weapon must be an instance of OBJ_Sword_Normal.");
            }
            setCurrentSword((OBJ_Sword_Normal) weapon);
        } else if (playerType.equals("Mage")) {
            if (!(weapon instanceof OBJ_Legendary_Staff)) {
                throw new IllegalArgumentException("Invalid weapon type. Weapon must be an instance of OBJ_Staff_Legendary.");
            }
            setCurrentStaff((OBJ_Legendary_Staff) weapon);
        }
        else if (playerType.equals("Hunter")) {
            if (!(weapon instanceof OBJ_Legendary_Bow)) {
                throw new IllegalArgumentException("Invalid weapon type. Weapon must be an instance of OBJ_Staff_Legendary.");
            }
            setCurrentBow((OBJ_Legendary_Bow) weapon);
        }
        else if (playerType.equals("Assassin")) {
            if (!(weapon instanceof OBJ_Legendary_Dagger)) {
                throw new IllegalArgumentException("Invalid weapon type. Weapon must be an instance of OBJ_Staff_Legendary.");
            }
            setCurrentDagger((OBJ_Legendary_Dagger) weapon);
        }
        else if (playerType.equals("Fighter")) {
            if (!(weapon instanceof OBJ_Legendary_Glove)) {
                throw new IllegalArgumentException("Invalid weapon type. Weapon must be an instance of OBJ_Staff_Legendary.");
            }
            setCurrentGlove((OBJ_Legendary_Glove) weapon);
        }
            // Add more else if statements for other types of players
    }
    public void setCurrentStaff(OBJ_Legendary_Staff weapon) {
        this.currentWeapon = weapon;

        if (weapon instanceof OBJ_Staff_Air) {
            OBJ_Staff_Air airStaff = (OBJ_Staff_Air) weapon;
            this.projectile1 = airStaff.projectile1;
            this.projectile2 = airStaff.projectile2;
            this.projectile3 = airStaff.projectile3;
        } else if (weapon instanceof OBJ_Staff_Fire) {
            OBJ_Staff_Fire fireStaff = (OBJ_Staff_Fire) weapon;
            this.projectile1 = fireStaff.projectile1;
            this.projectile2 = fireStaff.projectile2;
            this.projectile3 = fireStaff.projectile3;
        } else if (weapon instanceof OBJ_Staff_Water) {
            OBJ_Staff_Water waterStaff = (OBJ_Staff_Water) weapon;
            this.projectile1 = waterStaff.projectile1;
            this.projectile2 = waterStaff.projectile2;
            this.projectile3 = waterStaff.projectile3;
        } else if (weapon instanceof OBJ_Staff_Electric) {
            OBJ_Staff_Electric electricStaff = (OBJ_Staff_Electric) weapon;
            this.projectile1 = electricStaff.projectile1;
            this.projectile2 = electricStaff.projectile2;
            this.projectile3 = electricStaff.projectile3;
        }
        // Add more else if statements for other types of staffs
    }
    public void setCurrentBow(OBJ_Legendary_Bow weapon) {
        this.currentWeapon = weapon;

        if (weapon instanceof OBJ_Bow_Common) {
            OBJ_Bow_Common commonBow = (OBJ_Bow_Common) weapon;
            this.projectile1 = commonBow.projectile1;
            this.projectile2 = commonBow.projectile2;
            this.projectile3 = commonBow.projectile3;
        }else if (weapon instanceof OBJ_Bow_Air) {
            OBJ_Bow_Air airBow = (OBJ_Bow_Air) weapon;
//            this.projectile1 = airBow.projectile1;
//            this.projectile2 = airBow.projectile2;
//            this.projectile3 = airBow.projectile3;
        } else if (weapon instanceof OBJ_Bow_Fire) {
            OBJ_Bow_Fire fireBow = (OBJ_Bow_Fire) weapon;
//            this.projectile1 = fireBow.projectile1;
//            this.projectile2 = fireBow.projectile2;
//            this.projectile3 = fireBow.projectile3;
        } else if (weapon instanceof OBJ_Bow_Water) {
            OBJ_Bow_Water waterBow = (OBJ_Bow_Water) weapon;
//            this.projectile1 = waterBow.projectile1;
//            this.projectile2 = waterBow.projectile2;
//            this.projectile3 = waterBow.projectile3;
        } else if (weapon instanceof OBJ_Bow_Electric) {
            OBJ_Bow_Electric electricBow = (OBJ_Bow_Electric) weapon;
//            this.projectile1 = electricBow.projectile1;
//            this.projectile2 = electricBow.projectile2;
//            this.projectile3 = electricBow.projectile3;
        }
    }
    public void setCurrentDagger(OBJ_Legendary_Dagger weapon) {
        this.currentWeapon = weapon;

        if (weapon instanceof OBJ_Dagger_Air) {
            OBJ_Dagger_Air airDagger = (OBJ_Dagger_Air) weapon;
        } else if (weapon instanceof OBJ_Dagger_Fire) {
            OBJ_Dagger_Fire fireDagger = (OBJ_Dagger_Fire) weapon;
        } else if (weapon instanceof OBJ_Dagger_Water) {
            OBJ_Dagger_Water waterDagger = (OBJ_Dagger_Water) weapon;
        } else if (weapon instanceof OBJ_Dagger_Electric) {
            OBJ_Dagger_Electric electricDagger = (OBJ_Dagger_Electric) weapon;
        }
    }
    public void setCurrentGlove(OBJ_Legendary_Glove weapon) {
        this.currentWeapon = weapon;

        if (weapon instanceof OBJ_Glove_Air) {
            OBJ_Glove_Air airGlove = (OBJ_Glove_Air) weapon;
        } else if (weapon instanceof OBJ_Glove_Fire) {
            OBJ_Glove_Fire fireGlove = (OBJ_Glove_Fire) weapon;
        } else if (weapon instanceof OBJ_Glove_Water) {
            OBJ_Glove_Water waterGlove = (OBJ_Glove_Water) weapon;
        } else if (weapon instanceof OBJ_Glove_Electric) {
            OBJ_Glove_Electric electricGlove = (OBJ_Glove_Electric) weapon;
        }
    }
    public void setCurrentSword(OBJ_Sword_Normal weapon) {
        this.currentWeapon = weapon;
        this.projectile1 = null;
        this.projectile2 = null;
        this.projectile3 = null;
    }
    public void obtainWeapon(Object weapon) {
        if (weapon instanceof OBJ_Legendary_Staff
                || weapon instanceof OBJ_Legendary_Sword
                || weapon instanceof OBJ_Legendary_Bow
                || weapon instanceof OBJ_Legendary_Dagger
                || weapon instanceof OBJ_Legendary_Glove
        ) {
            inventory.add((Entity) weapon);
        }
    }
    public void fireProjectile() {
        if (currentWeapon instanceof OBJ_Bow_Common) {
            getAttackImage();
        }
//        // Create a new projectile
//        TargetedProjectile projectile = new TargetedProjectile(this.gp);
//
//        // Set the projectile's properties
//        projectile.set((int) worldY, (int) worldY, direction, true, this); // Set alive to true
//
//        // Add the projectile to the list of active projectiles
//        activeProjectiles.add(projectile);
    }

    // status effects
    public void applyPoison() {
        // Apply poison effect
        isPoisoned = true;
    }
    public void applyStun() {
        // Apply stun effect
        isStunned = true;
    }
    public void applyBleed() {
        // Apply bleed effect
        isBleeding = true;
    }
    public void applyBuff() {
        // Apply buff effect
        isBuffed = true;
    }
    public void applyDebuff() {
        // Apply debuff effect
        isDebuffed = true;
    }
    public void applyHealing() {
        // Apply healing effect
        isHealing = true;
    }
    public void updateStatusEffects() {
        // Update the effects of the status conditions
        if (isPoisoned) {
            // Reduce health
        }
        if (isStunned) {
            // Skip turn
        }
        if (isBleeding) {
            // Reduce health
        }
        if (isBuffed) {
            // Increase stats
        }
        if (isDebuffed) {
            // Decrease stats
        }
        if (isHealing) {
            // Increase health
        }
    }
    // Quests
    public void addQuest(String quest) {
        // Add a new quest
        quests.add(quest);
    }
    public void completeQuest(String quest) {
        // Complete a quest
        quests.remove(quest);
        achievements.add("Completed quest: " + quest);
    }
    public void addAchievement(String achievement) {
        // Add a new achievement
        achievements.add(achievement);
    }
    // In the Player class
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
