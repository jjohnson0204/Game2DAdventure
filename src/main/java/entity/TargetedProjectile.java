package entity;

import main.GamePanel;

public class TargetedProjectile extends Projectile {
    private Entity target;
    public TargetedProjectile(GamePanel gp) {
        super(gp);
    }

    @Override
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        Entity closestMonster = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity[] monsters : gp.monster) {
            for (Entity monster : monsters) {
                if (monster != null) {
                    double distance = Math.sqrt(Math.pow(user.worldX - monster.worldX, 2) + Math.pow(user.worldY - monster.worldY, 2));
                    if (distance <= 500 && distance < closestDistance) {
                        closestMonster = monster;
                        closestDistance = distance;
                    }
                }
            }
        }

        if (closestMonster != null) {
            this.target = closestMonster;
        }

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }
}