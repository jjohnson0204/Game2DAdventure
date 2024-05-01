package entity.players;

import entity.Player;
import entity.SpriteSheet;
import main.GamePanel;
import main.KeyHandler;
import object.weapons.hunter.OBJ_Bow_Common;
import object.weapons.mage.OBJ_Staff_Air;

public class Mage extends Player {
    GamePanel gp;
    KeyHandler keyH;
    public Mage(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, 4);
        this.gp = gp;
        this.keyH = keyH;

//        getImage();
        playerType = "Mage";
    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();
        // Set Mage-specific default values
        maxLife = 5; // Mages have less life
        strength = 1; // Mages have less strength
        dexterity = 2; // Mages have average dexterity
        maxMana = 10; // Mages have more mana
        attack = getAttack();
        defense = getDefense();
        setCharacterEquipment(playerType);
    }
    public void castSpell() {
        // Cast a spell
    }

    public void heal() {
        // Restore health
    }
    public void fireball() {
        // Cast a fireball that deals damage to an enemy
    }

    public void manaShield() {
        // Create a shield that absorbs a certain amount of damage
    }
    public void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/player/npc005.png", 32, 32, 3, 5);
        down1 = sprite.getSprite(0, 0);
        down2 = sprite.getSprite(1, 0);
        down3 = sprite.getSprite(2, 0);

        left1 = sprite.getSprite(0, 1);
        left2 = sprite.getSprite(1, 1);
        left3 = sprite.getSprite(2, 1);

        right1 = sprite.getSprite(0, 2);
        right2 = sprite.getSprite(1, 2);
        right3 = sprite.getSprite(2, 2);

        up1 = sprite.getSprite(0, 3);
        up2 = sprite.getSprite(1, 3);
        up3 = sprite.getSprite(2, 3);

    }
}