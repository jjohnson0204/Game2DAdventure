package environment;

import entity.Particle;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Rain {
    private List<Particle> particles = new ArrayList<>();
    private Random random = new Random();
    private GamePanel gp;

    public Rain(GamePanel gp) {
        this.gp = gp;
    }

    public void generateParticles(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(gp.screenWidth);
            int y = random.nextInt(gp.screenHeight);
            int vx = 0;
            int vy = random.nextInt(5) + 1;
            Color color = Color.WHITE;
            int size = 2;
            int life = random.nextInt(100) + 50;
            particles.add(new Particle(gp, x, y, vx, vy, color, size, life));
        }
    }

    public void updateParticles() {
        List<Particle> newParticles = new ArrayList<>();
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            if (!particle.alive) {
                iterator.remove();
                newParticles.add(generateParticle());
            }
        }
        particles.addAll(newParticles);
    }

    private Particle generateParticle() {
        int x = random.nextInt(gp.screenWidth);
        int y = random.nextInt(gp.screenHeight);
        int vx = 0;
        int vy = random.nextInt(5) + 1;
        Color color = Color.WHITE;
        int size = 5;
        int life = random.nextInt(100) + 50;
        return new Particle(gp, x, y, vx, vy, color, size, life);
    }

    public void drawParticles(Graphics2D g2) {
        for (Particle particle : particles) {
            particle.draw(g2);
        }
    }
}