package entity.players;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

public class Warrior extends Player {
    GamePanel gp;
    KeyHandler keyH;
    public Warrior(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, 1);
        this.gp = gp;
        this.keyH = keyH;

    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();
        // Set Warrior-specific default values
        maxLife = 10; // Warriors have more life
        strength = 3; // Warriors have more strength
        dexterity = 1; // Warriors have less dexterity
        attack = getAttack();
        defense = getDefense();
    }
    public void shieldBlock() {
        // Increase defense temporarily
    }

    public void powerStrike() {
        // Perform a powerful attack
    }
}