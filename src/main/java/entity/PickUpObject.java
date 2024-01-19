package entity;

import main.GamePanel;

public class PickUpObject extends Entity {
    // Add any properties specific to PickUpObject here
    private boolean isPickedUp;
    GamePanel gp;

    public PickUpObject(GamePanel gp) {
        super(gp);
        // Initialize any PickUpObject-specific properties here
        this.gp = gp;
        this.isPickedUp = false;
    }

    // Method to pick up the object
    public void pickUp() {
        this.isPickedUp = true;
    }

    public void update() {
        // Update the state of the PickUpObject here
        if (this.isPickedUp) {
            // Do something when the object is picked up...
        }
    }
}
