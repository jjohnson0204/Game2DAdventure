package entity;

import main.GamePanel;
import monster.MON_GreenSlime;

public class Projectile extends Entity{
    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX;
        this.worldY =  worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }
    public void update(){
        if(user == gp.player){
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, this, attack, knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        if(user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true){
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
                alive = false;
            }
        }
        switch (direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        life--;
        if(life <= 0){
            alive = false;
        }

        spriteCounter = user.projectile.animate(spriteCounter);
        skillCounter = user.projectile2.animate2(skillCounter);
        burstCounter = user.projectile3.animate3(burstCounter);

//        spriteCounter++;
//        if(spriteCounter > 12){
//            if(spriteNum == 1){
//                spriteNum = 2;
//            }
//            else if(spriteNum == 2){
//                spriteNum = 1;
//            }
//            spriteCounter = 0;
//        }
    }
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        return haveResource;
    }
    public void subtractResource(Entity user){
    }
    public int animate ( int spriteCounter ) {
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        return spriteCounter;
    }
    public int animate2 ( int skillCounter ) {
        skillCounter++;
        if(skillCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            skillCounter = 0;
        }

        return skillCounter;
    }
    public int animate3 ( int burstCounter ) {
        burstCounter++;
        if(burstCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            burstCounter = 0;
        }

        return burstCounter;
    }
}
