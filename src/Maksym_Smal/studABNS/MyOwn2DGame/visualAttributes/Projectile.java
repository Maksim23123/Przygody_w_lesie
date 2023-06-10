package Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Projectile {

    BufferedImage image;

    GamePanel gamePanel;

    Entity owner;

    int startPointX;
    int startPointY;

    int worldX;
    int worldY;

    int motionX;
    int motionY;

    int angle = 0;
    private boolean updated = false;
    public boolean mustBeDeleted = false;

    Projectile(GamePanel gamePanel, int worldX, int worldY, int directionX, int directionY, int speed,
               BufferedImage image, Entity owner) {
        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;

        startPointX = worldX;
        startPointY = worldY;

        this.owner = owner;


        int complementValueX;
        int complementValueY;

        double distance = Math.sqrt(Math.pow(worldX - directionX, 2) + Math.pow(worldY - directionY, 2));
        int distanceX = Math.abs(worldX - directionX);
        int distanceY = Math.abs(worldY - directionY);

        if (worldX - directionX > 0) {
            complementValueX = -1;
        }
        else {
            complementValueX = 1;
        }

        if (worldY - directionY > 0) {
            complementValueY = -1;
        }
        else {
            complementValueY = 1;
        }

        double cos = distanceY / distance;
        double sin = distanceX / distance;

        int angle = (int) Math.toDegrees(Math.acos(cos));

        if (worldX > directionX && worldY > directionY) {
            angle = 360 - angle;
        } else if (worldX > directionX) {
            angle = 180 + angle;
        } else if (!(worldY > directionY)) {
            angle = 180 - angle;
        }

        this.angle = angle;

        motionX = (int)(speed * sin) * complementValueX;
        motionY = (int)(speed * cos) * complementValueY;

        this.image = image;
    }

    public void update() {
        worldX += motionX;
        worldY += motionY;

        double distance = Math.sqrt(Math.pow(worldX - gamePanel.player.worldX, 2) +
                Math.pow(worldY - gamePanel.player.worldY, 2));

        if (distance < 48 && gamePanel.player.getRollingTime() == 0 ) {
            gamePanel.soundManager.playSound(1);
            gamePanel.player.attributeManager.dealDamage(owner.attributeManager.getOutputDamage());
            gamePanel.player.pushAway(startPointX, startPointY, 15, 5);
            mustBeDeleted = true;
        }

        if (!gamePanel.collisionChecker.testStackInWall(this)) {
            mustBeDeleted = true;
        }
        setUpdated(true);
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), image.getWidth() / 2, image.getHeight() / 2);

        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        g2d.drawImage(image, transform, null);
        g2d.dispose();


        g2.drawImage(rotatedImage, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public boolean getUpdated() {
        return updated;
    }

    public void setUpdated(boolean value) {
        updated = value;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
}
