package entity;

import main.GamePanel;

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
        if(user == gp.players[gp.selectedPlayerIndex]){
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999){
                gp.players[gp.selectedPlayerIndex].damageMonster(monsterIndex, this, attack, knockBackPower);
                generateParticle(user.projectile1, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        if(user != gp.players[gp.selectedPlayerIndex]){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(!gp.players[gp.selectedPlayerIndex].invincible && contactPlayer){
                damagePlayer(attack);
                generateParticle(user.projectile1, user.projectile1);
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

        spriteCounter = user.projectile1.animate(spriteCounter);
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
    public int animate(int spriteCounter) {
        spriteCounter++;
        if(spriteCounter > 50){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if(spriteNum == 2){
                spriteNum = 3;
            } else if(spriteNum == 3){
                spriteNum = 4;
            } else if(spriteNum == 4){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        return spriteCounter;
    }
    public int animate2 ( int skillCounter ) {
        skillCounter++;
        if(skillCounter > 50){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if(spriteNum == 2){
                spriteNum = 3;
            } else if(spriteNum == 3){
                spriteNum = 4;
            } else if(spriteNum == 4){
                spriteNum = 1;
            }
            skillCounter = 0;
        }

        return skillCounter;
    }
    public int animate3 ( int burstCounter ) {
        burstCounter++;
        if(burstCounter > 50){
            if(spriteNum == 1){
                spriteNum = 2;
            } else if(spriteNum == 2){
                spriteNum = 3;
            } else if(spriteNum == 3){
                spriteNum = 4;
            } else if(spriteNum == 4){
                spriteNum = 1;
            }
            burstCounter = 0;
        }

        return burstCounter;
    }
    public boolean hasExpired(){
        boolean hasExpired = false;
        if(life <= 0){
            hasExpired = true;
        }
        return hasExpired;
    }
}
