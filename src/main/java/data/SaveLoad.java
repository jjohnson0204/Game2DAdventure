package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            //Player Inventory
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }
            //Player Equipment
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
            //Objects on Map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int layerNum = 0; layerNum > gp.numberOfLayers; layerNum++) {
                    for (int i = 0; i < gp.obj[1].length; i++) {
                        if (gp.obj[mapNum][layerNum][i] == null) {
                            ds.mapObjectNames[mapNum][i] = "NA";
                        }
                        else {
                            ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][layerNum][i].name;
                            ds.mapObjectWorldX[mapNum][i] = (int) gp.obj[mapNum][layerNum][i].worldX;
                            ds.mapObjectWorldY[mapNum][i] = (int) gp.obj[mapNum][layerNum][i].worldY;
                            if (gp.obj[mapNum][layerNum][i].loot != null) {
                                ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][layerNum][i].loot.name;
                            }
                            ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][layerNum][i].opened;
                        }
                    }
                }


            }

            ///Write the DataStorage object
            oos.writeObject(ds);
        }
        catch (Exception e) {
            System.out.println("Save Exception");
        }
    }
    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //Read the DataStorage object
            DataStorage ds = (DataStorage)ois.readObject();

            //Player Stats
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            //Player Inventory
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }
            //Player Equipment
            gp.player.currentWeapon =  gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield =  gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();
            //Objects on Map
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for( int layerNum = 0; layerNum < gp.numberOfLayers; layerNum++) {
                    for (int i = 0; i < gp.obj[layerNum].length; i++) {
                        if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                            gp.obj[mapNum][layerNum][i] = null;
                        }
                        else {
                            gp.obj[mapNum][layerNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                            gp.obj[mapNum][layerNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                            gp.obj[mapNum][layerNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                            if (ds.mapObjectLootNames[mapNum][i] != null) {
                                gp.obj[mapNum][layerNum][i].loot = gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]);
                            }
                            gp.obj[mapNum][layerNum][i].opened = ds.mapObjectOpened[mapNum][i];
                            if (gp.obj[mapNum][layerNum][i].opened) {
                                gp.obj[mapNum][layerNum][i].down1 = gp.obj[mapNum][layerNum][i].image2;
                            }
                        }
                    }
                }

            }
        }
        catch (Exception e) {
            System.out.println("Load Exception");
        }
    }
}
