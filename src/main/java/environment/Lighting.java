package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    BufferedImage bloom; // Declare bloom as an instance variable
    Graphics2D g2; // Declare g2 as an instance variable
    RadialGradientPaint paint; // Declare paint as an instance variable
    public int dayCounter;
    public float filterAlpha = 0f;

    public final int day =  0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightingSource();
        // Initialize bloom and g2 in the constructor
        bloom = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) bloom.getGraphics();
        // Initialize paint in the constructor
        Color[] colors = {new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 0f)};
        float[] fractions = {0f, 1f};
        paint = new RadialGradientPaint(200, 200, 100, fractions, colors);
        applyBloomEffect(200 , 200);
    }
    public void setLightingSource() {
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if(gp.player.currentLight == null){
            g2.setColor(new Color(0,0,0.1f,0.97f));
        }
        else {
            // Get the center x and y of the light circle
            int centerX = gp.player.screenX + (gp.tileSize) / 2;
            int centerY = gp.player.screenY + (gp.tileSize) / 2;

            // Create a gradation effect within the light circle
            Color[] color = new Color[12];
            float[] fraction = new float[12];

            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.42f);
            color[2] = new Color(0, 0, 0.1f, 0.52f);
            color[3] = new Color(0, 0, 0.1f, 0.61f);
            color[4] = new Color(0, 0, 0.1f, 0.69f);
            color[5] = new Color(0, 0, 0.1f, 0.76f);
            color[6] = new Color(0, 0, 0.1f, 0.82f);
            color[7] = new Color(0, 0, 0.1f, 0.87f);
            color[8] = new Color(0, 0, 0.1f, 0.91f);
            color[9] = new Color(0, 0, 0.1f, 0.92f);
            color[10] = new Color(0, 0, 0.1f, 0.93f);
            color[11] = new Color(0, 0, 0.1f, 0.94f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.8f;
            fraction[8] = 0.85f;
            fraction[9] = 0.9f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            // Create a gradation paint settings for the light circle
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (gp.player.currentLight.lightRadius / 2), fraction, color);

            // Set the gradient data on g2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();
    }
    public void applyBloomEffect(int x, int y) {
        // Clear the existing Graphics2D object
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.SrcOver);

        // Create a radial gradient that goes from a bright color at the center to a transparent color at the edges
        // Increase the alpha value of the center color to make the bloom effect more noticeable
        Color[] colors = {new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 0f)};
        float[] fractions = {0f, 1f};
        RadialGradientPaint paint = new RadialGradientPaint(x, y, 100, fractions, colors);

        // Apply the gradient to the Graphics2D object
        g2.setPaint(paint);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Draw the bloom effect on top of your existing graphics
        g2.drawImage(bloom, 0, 0, null);
    }

    public void resetDay() {
        dayState = day;
        filterAlpha = 0f;
    }
    public void update() {
        if(gp.player.lightUpdated){
            setLightingSource();
            gp.player.lightUpdated = false;
        }
        applyBloomEffect(gp.player.screenX + (gp.tileSize) / 2, gp.player.screenY + (gp.tileSize) / 2);
        // Check the state of the day
        if(dayState == day){
            dayCounter++;
            if(dayCounter > 600) {
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk) {
            filterAlpha += 0.0001f;
            if(filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night) {
            dayCounter++;
            if (dayCounter > 600) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn) {
            filterAlpha -= 0.0001f;
            if (filterAlpha < 0f) {
                filterAlpha = 0;
                dayState = day;
            }
        }

    }
    public void draw(Graphics2D g2) {
        if (gp.currentArea == gp.outside) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }
        if (gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug
        String situation = "";

        switch (dayState) {
            case day: situation  = "Day"; break;
            case dusk: situation = "Dusk"; break;
            case night: situation = "Night"; break;
            case dawn: situation = "Dawn"; break;
        }
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.drawString(situation, 1025, 700);
    }
}

