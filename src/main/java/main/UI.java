package main;

import entity.Entity;
import entity.SpriteSheet;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.consumables.OBJ_ManaCrystal;
import object.util.OBJ_Equipped_Menu;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;
import  java.util.List;
public class UI {
    //Graphics, Fonts and Formats
    GamePanel gp;
    Graphics2D g2;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public Font maruMonica;
    Font purisaB;
    public BufferedImage heart_full;
    public BufferedImage heart_half;
    public BufferedImage heart_blank;
    public BufferedImage crystal_full;
    public BufferedImage crystal_blank;
    public BufferedImage coin;
    public BufferedImage equipped_menu;
    public BufferedImage weapon;
    public static BufferedImage ability1;
    public static BufferedImage ability2;
    public static BufferedImage ability3;
    BufferedImage keyImage;

    //Booleans
    public boolean messageOn = false;
    public boolean gameOver = false;

    //Others
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public Entity npc;
    double playTime;
    public String currentDialogue = "";
    public String combinedText = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int subState = 0;
    public int counter = 0;
    public int charIndex = 0;


    public UI(GamePanel gp){
        this.gp = gp;
        try{
            // load a custom font in your project folder
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/font/x12y16pxMaruMonica.ttf")).deriveFont(30f);
            purisaB = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/font/Purisa Bold.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/font/x12y16pxMaruMonica.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/font/Purisa Bold.ttf")));
        }
        catch(IOException | FontFormatException e){
            e.printStackTrace();
        }
        //Key image
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.down1;

        // Create Hud Object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;

        Entity equippedMenu = new OBJ_Equipped_Menu(gp);
        equipped_menu = equippedMenu.down1;

        weapon = gp.player.currentWeapon.down1;

        if(gp.player.currentWeapon.projectile1 != null){
            ability1 = gp.player.currentWeapon.projectile1.down1;
            ability2 = gp.player.currentWeapon.projectile2.down1;
            ability3 = gp.player.currentWeapon.projectile3.down1;
        }
    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        //Title State
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        // Play State
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMonsterLife();
            drawPlayerEquippedAbilities();
            drawGameTimer();
            drawMessage();

        }
        // Pause State
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        // Dialogue State
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        //Character State
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        //Options State
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        //Game Over State
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        //Transition State
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        //Trade State
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        //Sleep State
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }
    }
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        int iconSize = 32;
        int manaStartX = (gp.tileSize / 2) - 5;
        int manaStartY = 0;

        //Draw Max Life
        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x , y, iconSize, iconSize, null);
            i++;
            x += iconSize;
            manaStartY = y + 32;

            if (i % 8 == 0) {
                x = gp.tileSize / 2;
                y += iconSize;
            }
        }

        //Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //Draw Current Life
        while (i < gp.player.life){
            g2.drawImage(heart_half, x , y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x , y, null);
            }
            i++;
            x += gp.tileSize;
        }

        //Draw Max Mana
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        //Draw Mana
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }
    public void drawPlayerEquippedAbilities() {
        int x = (int)(gp.tileSize * 17.5);
        int y = (int)(gp.tileSize * 9.5);

        g2.setFont(g2.getFont().deriveFont(24f));
        g2.drawImage(equipped_menu, x + 10, y + 8, 64 * 2, 64 * 2,null);
        g2.drawString("Enter", 1235, 755);
        weapon = gp.player.currentWeapon.down1;
        g2.drawImage(weapon, 1190, 670, 64, 64, null);
        g2.drawString("F", 1170, 700);
        g2.drawImage(ability1, 1142, 705, 24, 24, null);
        g2.drawString("B", 1190, 645);
        g2.drawImage(ability2, 1157, 642, 24, 24, null);
        g2.drawString("V", 1245, 626);
        g2.drawImage(ability3, 1220, 633, 24, 24, null);
    }
    public void drawMonsterLife() {

        for (int i = 0; i < gp.monster[1].length; i++) {
            Entity monster = gp.monster[gp.currentMap][i];
            if (monster != null && monster.inCamera()) {
                //Monster HP Bar
                if(monster.hpBarOn && !monster.boss){
                    double oneScale = (double)gp.tileSize / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    //Outer Rect
                    g2.setColor(new Color(35, 35,35));
                    g2.fillRect(monster.getScreenX() -1, (int) monster.getScreenY()-16, gp.tileSize+2, 12);
                    //Inner Rect
                    g2.setColor(new Color(255, 0,30));
                    g2.fillRect(monster.getScreenX(), (int) (monster.getScreenY() -15), (int) hpBarValue, 10);

                    monster.hpBarCounter++;

                    if(monster.hpBarCounter > 600){
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                }
                else if (monster.boss) {
                    double oneScale = (double)gp.tileSize * 8 / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    int x = gp.screenWidth / 2 - gp.tileSize * 4;
                    int y = gp.tileSize * 10;

                    //Outer Rect
                    g2.setColor(new Color(35, 35,35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize*8 + 2, 22);
                    //Inner Rect
                    g2.setColor(new Color(255, 0,30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26));
                    g2.setColor(Color.WHITE);
                    g2.drawString(monster.name, x + 4, y - 10);
                }
            }
        }

    }
    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                // Black shadow border
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                // Text
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++ for arraylist
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawGameTimer(){
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40));
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize / 5, gp.tileSize * 3, gp.tileSize, gp.tileSize, null);
        List<Entity> keys = gp.player.inventory;
        keys = keys.stream().filter( entity -> entity.name == "Key").collect(Collectors.toList());
        g2.drawString("  x = " + keys.size(), 64, 230);
        // Time
        playTime +=(double) 1/60;
        g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
    }
    public void drawTitleScreen(){

        //Title State
        if(titleScreenState == 0){
            g2.setColor(new Color(1, 35, 15));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Lights Epic Adventure";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //Shadow Color
            g2.setColor(new Color(43, 103, 84));
            g2.drawString(text, x + 5, y + 5);

            //Main Color
            g2.setColor(new Color(0, 168, 107));
            g2.drawString(text, x, y);

            //Character Image
            x = gp.screenWidth/2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;

            g2.drawImage(Main.window.getIconImage(), x,y,gp.tileSize * 2,gp.tileSize *2, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "New Game";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Load Game";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x- gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            //Class selection screen
            //BG Color
            g2.setColor(new Color(0, 168, 107)); // rgb color code it's the green from main screen
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight); // means start from top left corner and fill the screen
            //Font color
            g2.setColor(new Color(53, 94, 59));
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));
            //Title of Character Screen
            String text = "Select your character...";
            int x = getXforCenteredText(text);
            int y = gp.tileSize *3;
            g2.drawString(text, x , y);
            //Shadow Color
            g2.setColor(new Color(175, 225, 175));
            g2.drawString(text, x + 5, y + 5);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x- gp.tileSize, y);
            }

            text = "Warrior";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Hunter";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Assassin";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Mage";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 4){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 5){
                g2.drawString(">", x- gp.tileSize, y);
            }
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x =getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawGameOverScreen(){
        g2.setColor(new Color(0, 0, 0,210 ));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        String text;
        int textLength;
        int x;
        int y;
        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - 40, y);
        }

        //Back to Title Screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - 40, y);
        }
    }
    public void drawDialogueScreen(){
        // Window
        int x = gp.tileSize * 3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        // Text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
//            currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if (charIndex < characters.length) {
                gp.playSE(17);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }
            if (gp.keyH.enterPressed) {
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.dialogueState
                || gp.gameState == gp.cutSceneState) {
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }
        else {
            npc.dialogueIndex = 0;
            if (gp.gameState == gp.dialogueState) {
                gp.gameState = gp.playState;
            }
            if (gp.gameState == gp.cutSceneState) {
                gp.csManager.scenePhase++;
            }
        }
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x , y);
            y += 40;
        }
    }
    public void drawCharacterScreen(){
        // Window
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 36;

        // Names
        g2.drawString("Level ", textX, textY);
        textY += lineHeight;
        g2.drawString("Life ", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana ", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength ", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity ", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack ", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense ", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp ", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level ", textX, textY);
        textY += lineHeight;
        g2.drawString("Monies ", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon ", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield ", textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.life + "/" + gp.player.maxLife;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.mana + "/" + gp.player.maxMana;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);

    }
    public void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }

        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Fullscreen ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }
        }

        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }

        //End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }

        //Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //Fullscreen Check Box
        textX = (int) (frameX + gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 32;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 32, 32);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 32, 32);
        }

        //Music Volume Slider
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 32);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 32);


        //SE Slider
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 32);
        int seWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, seWidth, 32);

        gp.config.saveConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //Back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
            }
        }
    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Content
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game \nand \nreturn to title screen:";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
        // Yes
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void drawInventory(Entity entity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            // Window
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            // Window
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Draw Items
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            // Highlight selected item
            if (i == gp.player.selectedItemIndex) {
                g2.setColor(new Color(240, 190, 90)); // Change color to your preference
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            // Equip item within cursor
            if(gp.player.inventory.get(i) == gp.player.currentWeapon
                    || gp.player.inventory.get(i) == gp.player.currentShield
                    || gp.player.inventory.get(i) == gp.player.currentLight){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            // Display amount
            if(gp.player.inventory.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(48f));
                int amountX;
                int amountY;

                String s = String.valueOf(gp.player.inventory.get(i).amount);
                amountX = getXforAlignToRightText(s, slotX + 55);
                amountY = slotY + gp.tileSize;

                // Shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        if(cursor){
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // Draw Cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description Window
            int dFrameX  = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight =  gp.tileSize * 3;

            // Description Text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if(itemIndex < entity.inventory.size()){
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }
    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter * 5));
        g2.fillRect(0, 0,  gp.screenWidth, gp.screenHeight);

        if(counter == 50){ // The transitioning is done
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = (int) gp.player.worldX;
            gp.eHandler.previousEventY = (int) gp.player.worldY;
            gp.changeArea();
        }
    }
    public void drawTradeScreen(){
        switch (subState){
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select(){
        npc.dialogueSet = 0;
        drawDialogueScreen();

        // Window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        //Draw Text
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x -25, y);
            if(gp.keyH.enterPressed){
                subState = 1;
            }
        }
        y += gp.tileSize;

        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x -25, y);
            if(gp.keyH.enterPressed){
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x -25, y);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
    }
    public void trade_buy(){
        //Draw Player Inventory
        drawInventory(gp.player, false);
        //Draw Merchant Inventory
        drawInventory(npc, true);
        // Window Merchant Hint
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);
        //Draw Player Coin Window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Monies: " + gp.player.coin, x + 24, y + 60);
        //Draw Price Window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);
            //Buy an Item
            if(gp.keyH.enterPressed){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    npc.startDialogue(npc, 2);
                }
                else {
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex))){
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }
    }
    public void trade_sell(){
        //Draw Player inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;
        // Window Merchant Hint
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);
        //Draw Player Coin Window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Monies: " + gp.player.coin, x + 24, y + 60);
        //Draw Price Window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);
            //Sell an Item
            if (gp.keyH.enterPressed) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon
                        || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc, 4);
                }
                else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    } else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }
        }
    }
    public void drawSleepScreen(){
        counter++;
        if(counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if(gp.eManager.lighting.filterAlpha <= 0f) {
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.playState;
                gp.player.getImage();
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}

