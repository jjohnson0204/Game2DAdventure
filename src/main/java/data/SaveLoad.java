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

            ds.level = gp.players[gp.selectedPlayerIndex].level;
            ds.maxLife = gp.players[gp.selectedPlayerIndex].maxLife;
            ds.life = gp.players[gp.selectedPlayerIndex].life;
            ds.maxMana = gp.players[gp.selectedPlayerIndex].maxMana;
            ds.mana = gp.players[gp.selectedPlayerIndex].mana;
            ds.strength = gp.players[gp.selectedPlayerIndex].strength;
            ds.dexterity = gp.players[gp.selectedPlayerIndex].dexterity;
            ds.exp = gp.players[gp.selectedPlayerIndex].exp;
            ds.nextLevelExp = gp.players[gp.selectedPlayerIndex].nextLevelExp;
            ds.coin = gp.players[gp.selectedPlayerIndex].coin;

            //players[gp.selectedPlayerIndex] Inventory
            for (int i = 0; i < gp.players[gp.selectedPlayerIndex].inventory.size(); i++) {
                ds.itemNames.add(gp.players[gp.selectedPlayerIndex].inventory.get(i).name);
                ds.itemAmounts.add(gp.players[gp.selectedPlayerIndex].inventory.get(i).amount);
            }
            //players[gp.selectedPlayerIndex] Equipment
            ds.currentWeaponSlot = gp.players[gp.selectedPlayerIndex].getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.players[gp.selectedPlayerIndex].getCurrentShieldSlot();
            //Objects on Map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    }
                    else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = (int) gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = (int) gp.obj[mapNum][i].worldY;
                        if (gp.obj[mapNum][i].loot != null) {
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
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

            //players[gp.selectedPlayerIndex] Stats
            gp.players[gp.selectedPlayerIndex].level = ds.level;
            gp.players[gp.selectedPlayerIndex].maxLife = ds.maxLife;
            gp.players[gp.selectedPlayerIndex].life = ds.life;
            gp.players[gp.selectedPlayerIndex].maxMana = ds.maxMana;
            gp.players[gp.selectedPlayerIndex].mana = ds.mana;
            gp.players[gp.selectedPlayerIndex].strength = ds.strength;
            gp.players[gp.selectedPlayerIndex].dexterity = ds.dexterity;
            gp.players[gp.selectedPlayerIndex].exp = ds.exp;
            gp.players[gp.selectedPlayerIndex].nextLevelExp = ds.nextLevelExp;
            gp.players[gp.selectedPlayerIndex].coin = ds.coin;

            //players[gp.selectedPlayerIndex] Inventory
            gp.players[gp.selectedPlayerIndex].inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.players[gp.selectedPlayerIndex].inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.players[gp.selectedPlayerIndex].inventory.get(i).amount = ds.itemAmounts.get(i);
            }
            //players[gp.selectedPlayerIndex] Equipment
            gp.players[gp.selectedPlayerIndex].currentWeapon =  gp.players[gp.selectedPlayerIndex].inventory.get(ds.currentWeaponSlot);
            gp.players[gp.selectedPlayerIndex].currentShield =  gp.players[gp.selectedPlayerIndex].inventory.get(ds.currentShieldSlot);
            gp.players[gp.selectedPlayerIndex].getAttack();
            gp.players[gp.selectedPlayerIndex].getDefense();
            gp.players[gp.selectedPlayerIndex].getAttackImage();
            //Objects on Map
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                        gp.obj[mapNum][i] = null;
                    }
                    else {
                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if (ds.mapObjectLootNames[mapNum][i] != null) {
                            gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if (gp.obj[mapNum][i].opened) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
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
