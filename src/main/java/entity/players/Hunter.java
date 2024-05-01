package entity.players;

import entity.Player;
import entity.SpriteSheet;
import main.GamePanel;
import main.KeyHandler;

public class Hunter extends Player {
    GamePanel gp;
    KeyHandler keyH;

    public Hunter(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, 2);
        this.gp = gp;
        this.keyH = keyH;



    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();
        // Set Hunter-specific default values
        maxLife = 7; // Hunters have less life
        strength = 2; // Hunters have average strength
        dexterity = 3; // Hunters have more dexterity
        attack = getAttack();
        defense = getDefense();
    }
    public void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/player/femalehunterwalk.png", 48, 48, 4, 4);
        down1 = sprite.getSprite2(0, 0);
        down2 = sprite.getSprite2(1, 0);
        down3 = sprite.getSprite2(2, 0);
        down4 = sprite.getSprite2(3, 0);

        left1 = sprite.getSprite2(0, 1);
        left2 = sprite.getSprite2(1, 1);
        left3 = sprite.getSprite2(2, 1);
        left4 = sprite.getSprite2(3, 1);

        right1 = sprite.getSprite2(0, 2);
        right2 = sprite.getSprite2(1, 2);
        right3 = sprite.getSprite2(2, 2);
        right4 = sprite.getSprite2(3, 2);

        up1 = sprite.getSprite2(0, 3);
        up2 = sprite.getSprite2(1, 3);
        up3 = sprite.getSprite2(2, 3);
        up4 = sprite.getSprite2(3, 3);
    }
    public void aimedShot() {
        // Perform a precise attack
    }

    public void trap() {
        // Set a trap
    }
    public void rapidShot() {
        // Perform multiple attacks in quick succession
    }

    public void eagleEye() {
        // Increase accuracy for a certain number of turns
    }
}