package object.weapons.mage;

import entity.Entity;
import main.GamePanel;

public class OBJ_Legendary_Staff extends Entity {
    public static final String objName = "Legendary Staff";
    private String element;
//    private Staff[] elements;
    public OBJ_Legendary_Staff(GamePanel gp, String element) {
        super(gp);
        this.element = element;
        type = type_staff;
        name = objName;
        down1 = setup("/objects/staff_legendary");

        setElementAttributes(element);
    }
    // Methods for each element
    public void useAir() {
        // Implement the effect of the air element
    }

    public void useFire() {
        // Implement the effect of the fire element
    }

    public void useWater() {
        // Implement the effect of the water element
    }

    public void useElectric() {
        // Implement the effect of the electric element
    }

    // Method to use the staff
    public void useElement() {
        switch (element) {
            case "air":
                useAir();
                break;
            case "fire":
                useFire();
                break;
            case "water":
                useWater();
                break;
            case "electric":
                useElectric();
                break;
            default:
                // Handle the case where the element is not recognized
                break;
        }
    }
    public void setElementAttributes(String element) {
        switch (element) {
            case "air":
                name = "Legendary Staff (Air)";
                defaultSpeed = 10;
                speed = 10.0;
                maxLife = 100;
                life = 100;
                maxMana = 50;
                mana = 50;
                ammo = 10;
                level = 1;
                strength = 10;
                dexterity = 10;
                attack = 10;
                defense = 10;
                exp = 0;
                nextLevelExp = 100;
                coin = 0;
                motion1_duration = 5;
                motion2_duration = 25;
                currentWeapon = this; // or another weapon
                break;
            case "fire":
                // Set the values for the fire element
                name = "Legendary Staff (Fire)";
                defaultSpeed = 10;
                speed = 10.0;
                maxLife = 100;
                life = 100;
                maxMana = 50;
                mana = 50;
                ammo = 10;
                level = 1;
                strength = 10;
                dexterity = 10;
                attack = 10;
                defense = 10;
                exp = 0;
                nextLevelExp = 100;
                coin = 0;
                motion1_duration = 5;
                motion2_duration = 25;
                currentWeapon = this; // or another weapon
                break;
            case "water":
                // Set the values for the water element
                name = "Legendary Staff (Water)";
                defaultSpeed = 10;
                speed = 10.0;
                maxLife = 100;
                life = 100;
                maxMana = 50;
                mana = 50;
                ammo = 10;
                level = 1;
                strength = 10;
                dexterity = 10;
                attack = 10;
                defense = 10;
                exp = 0;
                nextLevelExp = 100;
                coin = 0;
                motion1_duration = 5;
                motion2_duration = 25;
                currentWeapon = this; // or another weapon
                break;
            case "electric":
                // Set the values for the electric element
                name = "Legendary Staff (Electric)";
                defaultSpeed = 10;
                speed = 10.0;
                maxLife = 100;
                life = 100;
                maxMana = 50;
                mana = 50;
                ammo = 10;
                level = 1;
                strength = 10;
                dexterity = 10;
                attack = 10;
                defense = 10;
                exp = 0;
                nextLevelExp = 100;
                coin = 0;
                motion1_duration = 5;
                motion2_duration = 25;
                currentWeapon = this; // or another weapon
                break;
            default:
                // Handle the case where the element is not recognized
                break;
        }
    }

}
