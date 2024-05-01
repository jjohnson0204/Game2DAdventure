package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.EntityGenerator;
import entity.PickUpObject;
import entity.Player;
import environment.EnvironmentManager;
import environment.Rain;
import environment.Snow;
import environment.Wind;
import object.OBJ_Chest;
import tile.Map;
import tile.TileManager;
import tile.tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2;
    public int tileSize = originalTileSize * scale; // 64x64 tile
    public int maxScreenCol = 20;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 1024
    public int screenHeight = tileSize * maxScreenRow; // 768

    // World settings
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

   //For Full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    public boolean gameOverSoundPlayed = false;

    //FPS
    int FPS = 90;

    //System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public EventHandler eHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    Thread gameThread;
    Rain rain = new Rain(this);
    Snow snow = new Snow(this);
    Wind wind = new Wind(this);

    //Entity and Object
//    public Player player = new Player(this,keyH, 4);
    public int selectedPlayerIndex = 0;
    public Player player = new Player(this,keyH, selectedPlayerIndex);

    public UI ui = new UI(this);
    PickUpObject pickUpObject = new PickUpObject(this);
    public Entity[][] obj = new Entity[maxMap][300];
    public Entity[][] npc = new Entity[maxMap][50];
    public Entity[][] monster = new Entity[maxMap][50];
    public ArrayList<OBJ_Chest> chests = new ArrayList<>();
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
//    public ArrayList<Entity> projectileList = new ArrayList<>();

    public Entity[][] projectile = new Entity[maxMap][50];
    public Entity[][] projectile2 = new Entity[maxMap][50];
    public Entity[][] projectile3 = new Entity[maxMap][50];
    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutSceneState = 11;

    //Others
    public boolean bossBattleOn = false;

    //Area
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

    public GamePanel() throws FileNotFoundException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void zoomInOut(int i) {
        int oldWorldWidth = tileSize * maxWorldCol;
        tileSize += i;
        int newWorldWidth = tileSize * maxWorldCol;

        player.speed = (double) newWorldWidth / 812.50;
        double multiplier = (double) newWorldWidth / oldWorldWidth;

        System.out.println("Tile Size: " + tileSize);
        System.out.println("World Width: " + newWorldWidth);
        System.out.println("Player World X: " + player.worldX);

        double newPlayerWorldX = player.worldX * multiplier;
        double newPlayerWorldY = player.worldY * multiplier;

        player.worldX = newPlayerWorldX;
        player.worldY = newPlayerWorldY;

        adjustObjectPositions(multiplier);
    }
    public void setupGame(){

        // Set Entities and Objects in world


        player.setDefaultPositions();

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();

        gameState = titleState;
        currentArea = outside;

        tempScreen = new BufferedImage(screenWidth,
                screenHeight,
                BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        rain.generateParticles(100);
        snow.generateParticles(100);
        wind.generateParticles(100);
        if(fullScreenOn){
            setFullScreen();
        }

    }
    public void resetGame(boolean restart){
        stopMusic();
        currentArea = outside;
        removeTempEntity();
        bossBattleOn = false;

        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();

        aSetter.setNPC();
        aSetter.setMonster();

        if(restart) {
            player.setDefaultValues();

            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }
    public void setFullScreen(){
        //Get Local Screen Device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // Get Full Screen Width and Height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub

        double drawInterval = 1_000_000_000.0/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen();     // draw everything to the screen
                delta--;
                drawCount++;
            }
            if(timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        if(gameState == playState){
            //Player

            player.update();

            pickUpObject.update();

            //NPC
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying){
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    if(projectile[currentMap][i].alive){
                        projectile[currentMap][i].update();
                    }
                    if(!projectile[currentMap][i].alive){
                        projectile[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectile2[1].length; i++){
                if(projectile2[currentMap][i] != null){
                    if(projectile2[currentMap][i].alive){
                        projectile2[currentMap][i].update();
                    }
                    if(!projectile2[currentMap][i].alive){
                        projectile2[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectile3[1].length; i++){
                if(projectile3[currentMap][i] != null){
                    if(projectile3[currentMap][i].alive){
                        projectile3[currentMap][i].update();
                    }
                    if(!projectile3[currentMap][i].alive){
                        projectile3[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }
            }
            for (int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            //Environment
            rain.updateParticles();
            snow.updateParticles();
            wind.updateParticles();
        }
        if(gameState == pauseState){

        }
        if(gameState == dialogueState){

        }
        if(gameState == gameOverState){
        }
    }
    public void drawToTempScreen() {
        //Debug
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }
        //Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        }
        //Map Screen
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        }
        //Others
        else {
            //Tile
            tileM.draw(g2);

            //Interactive Tile
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            // Update Player and pickUpObject

            player.update();
            //Add Entities to the List
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            for (int i = 0; i < projectile2[1].length; i++) {
                if (projectile2[currentMap][i] != null) {
                    entityList.add(projectile2[currentMap][i]);
                }
            }
            for (int i = 0; i < projectile3[1].length; i++) {
                if (projectile3[currentMap][i] != null) {
                    entityList.add(projectile3[currentMap][i]);
                }
            }
            for (Entity entity : particleList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            eManager.update();
            //Sort
            entityList.sort((e1, e2) -> {
                int result = Integer.compare((int) e1.worldY, (int) e2.worldY);
                return result;
            });

            //Draw Entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            //Empty Entity List
            entityList.clear();

            //Environment
            eManager.draw(g2);

            //Mini Map
            map.drawMiniMap(g2);

            //CutScene
            csManager.draw(g2);
            // UI
            ui.draw(g2);
            //Rain
//            rain.drawParticles(g2);
//            snow.drawParticles(g2);
            wind.drawParticles(g2);
        }
        //Debug
        if (keyH.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY " + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize, x, y += lineHeight);
            g2.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize, x, y += lineHeight);

            g2.drawString("Draw Time: " + passed, 10, y += lineHeight);
//            System.out.println("Draw Time: " + passed);

            g2.drawString("God Mode:" + keyH.godModeOn, x, y += lineHeight);

            //Get the tile location info
            int playerTileNum = tileM.mapTileNum[currentMap][player.getCol()][player.getRow()];
            g2.drawString("Player is on " + playerTileNum + " tile", x, y += lineHeight);

        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    //Sound Effects
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
    public void changeArea() {
        if (nextArea != currentArea) {
            stopMusic();
            if(nextArea == outside) {
                playMusic(0);
            }
            if(nextArea == indoor) {
                playMusic(18);
            }
            if(nextArea == dungeon) {
                playMusic(19);
                playSE(23);
                playSE(24);
            }
            aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }
    public void removeTempEntity() {
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].temp) {
                    obj[mapNum][i] = null;
                }
            }
        }
    }
    public ArrayList<OBJ_Chest> getChests() {
        return chests;
    }
    public void adjustObjectPositions(double multiplier) {
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[mapNum].length; i++) {
                if (obj[mapNum][i] != null) {
                    obj[mapNum][i].worldX *= multiplier;
                    obj[mapNum][i].worldY *= multiplier;
                }
            }
            for (int i = 0; i < npc[mapNum].length; i++) {
                if (npc[mapNum][i] != null) {
                    npc[mapNum][i].worldX *= multiplier;
                    npc[mapNum][i].worldY *= multiplier;
                }
            }
            for (int i = 0; i < monster[mapNum].length; i++) {
                if (monster[mapNum][i] != null) {
                    monster[mapNum][i].worldX *= multiplier;
                    monster[mapNum][i].worldY *= multiplier;
                }
            }
            for (int i = 0; i < projectile[mapNum].length; i++) {
                if (projectile[mapNum][i] != null) {
                    projectile[mapNum][i].worldX *= multiplier;
                    projectile[mapNum][i].worldY *= multiplier;
                }
            }
            for (int i = 0; i < projectile2[mapNum].length; i++) {
                if (projectile2[mapNum][i] != null) {
                    projectile2[mapNum][i].worldX *= multiplier;
                    projectile2[mapNum][i].worldY *= multiplier;
                }
            }
            for (int i = 0; i < projectile3[mapNum].length; i++) {
                if (projectile3[mapNum][i] != null) {
                    projectile3[mapNum][i].worldX *= multiplier;
                    projectile3[mapNum][i].worldY *= multiplier;
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    particleList.get(i).worldX *= multiplier;
                    particleList.get(i).worldY *= multiplier;
                }
            }
            for (int i = 0; i < iTile[mapNum].length; i++) {
                if (iTile[mapNum][i] != null) {
                    iTile[mapNum][i].worldX *= multiplier;
                    iTile[mapNum][i].worldY *= multiplier;
                }
            }

            // Add similar code for any other types of objects in your game
        }

    }
}
