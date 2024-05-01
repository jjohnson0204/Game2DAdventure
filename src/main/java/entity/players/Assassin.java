package entity.players;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

public class Assassin extends Player {
    GamePanel gp;
    KeyHandler keyH;
    public Assassin(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, 3);
        this.gp = gp;
        this.keyH = keyH;

    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();
        // Set Assassin-specific default values
        maxLife = 6; // Assassins have less life
        strength = 1; // Assassins have less strength
        dexterity = 4; // Assassins have more dexterity
        attack = getAttack();
        defense = getDefense();
    }
    public void stealth() {
        // Become invisible
    }

    public void backstab() {
        // Perform a powerful attack from behind
    }
    public void shadowStep() {
        // Teleport behind an enemy
    }

    public void poisonBlade() {
        // Apply poison effect to the enemy on the next attack
    }
}