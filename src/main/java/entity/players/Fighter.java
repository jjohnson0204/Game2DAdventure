package entity.players;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

public class Fighter extends Player {
    GamePanel gp;
    KeyHandler keyH;
    public Fighter(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH, 0);
        this.gp = gp;
        this.keyH = keyH;

    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();
        // Set Fighter-specific default values
        maxLife = 8; // Fighters have more life
        strength = 2; // Fighters have more strength
        dexterity = 1; // Fighters have less dexterity
        attack = getAttack();
        defense = getDefense();
    }
}