package Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Weapon {
    BufferedImage image;

    GamePanel gamePanel;

    int screenX = 0;

    int screenY = 0;

    int angle = 0;

    public Weapon(GamePanel gamePanel,BufferedImage image) {
        this.image = image;
        this.gamePanel = gamePanel;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void draw(Graphics2D g2) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), image.getWidth() / 2, image.getHeight() / 2);

        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        g2d.drawImage(image, transform, null);
        g2d.dispose();


        g2.drawImage(rotatedImage, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}