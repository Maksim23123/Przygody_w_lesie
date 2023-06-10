package Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VisualOutputManager {

    GamePanel gamePanel;

    MiniMap miniMap;

    public VisualOutputManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        miniMap = new MiniMap(gamePanel);
    }

    public MiniMap getMiniMap() {
        return miniMap;
    }

    public void draw(Graphics2D g2) {
        drawHealthBar(g2);
        miniMap.draw(g2);
    }

    public void drawHealthBar(Graphics2D g2) {

        int maxHeartCount = gamePanel.player.attributeManager.getMaxHealth() / 5;

        if (gamePanel.player.attributeManager.getMaxHealth() / 5f > maxHeartCount) {
            maxHeartCount++;
        }

        int distanceBetween;

        if (gamePanel.player.attributeManager.getMaxHealth() / 3 < 48) {
            distanceBetween = (int) (10 - (gamePanel.player.attributeManager.getMaxHealth() / 3));
        }
        else {
            distanceBetween = -48;
        }


        for (int i = 0; i < maxHeartCount; i++) {
            g2.drawImage(gamePanel.imageManager.getImageByTag("grayHeart"), gamePanel.tileSize * i + distanceBetween
                    * i + 10,10,gamePanel.tileSize, gamePanel.tileSize, null);
        }

        int health = gamePanel.player.attributeManager.getHealth() / 5;

        for (int i = 0; i < health; i++) {
            g2.drawImage(gamePanel.imageManager.getImageByTag("heart"), gamePanel.tileSize * i + distanceBetween
                    * i + 10,10,gamePanel.tileSize, gamePanel.tileSize, null);
        }

        if (gamePanel.player.attributeManager.getHealth() / 5f > health) {
            int heartPart = gamePanel.player.attributeManager.getHealth();
            while (heartPart > 5) {
                heartPart -= 5;
            }

            BufferedImage originalImage = gamePanel.imageManager.getImageByTag("heart");

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            BufferedImage copiedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = copiedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();


            g2d = copiedImage.createGraphics();

            int x = (width / 5) * heartPart;
            int y = 0; // координата y початкової точки прямокутника
            int clearWidth = (width / 5) *  (5 - heartPart);
            int cleaHeight = gamePanel.tileSize;
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(x, y, clearWidth, cleaHeight);

            g2d.dispose();

            g2.drawImage(copiedImage, gamePanel.tileSize * health + distanceBetween * health + 10,
                    10,gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }
}
