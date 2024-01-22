package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{
    public static final String npcName = "Dummy";
    public PlayerDummy(GamePanel gp) {
        super(gp);
         name = npcName;
         getImage();
    }
    public void getImage() {
        SpriteSheet sprite = new SpriteSheet("src/resources/player/npc005.png", 32, 32, 3, 5);
        down1 = sprite.getSprite(0, 0);
        down2 = sprite.getSprite(1,0);
        down3 = sprite.getSprite(2, 0);

        left1 = sprite.getSprite(0, 1);
        left2 = sprite.getSprite(1, 1);
        left3 = sprite.getSprite(2, 1);

        right1 = sprite.getSprite(0, 2);
        right2 = sprite.getSprite(1, 2);
        right3 = sprite.getSprite(2, 2);

        up1 = sprite.getSprite(0, 3);
        up2 = sprite.getSprite(1,3);
        up3 = sprite.getSprite(2, 3);

    }
}
