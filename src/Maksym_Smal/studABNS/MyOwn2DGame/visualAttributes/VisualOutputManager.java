package Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class VisualOutputManager {

    GamePanel gamePanel;

    public VisualOutputManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthBBg"), 0,
                0,gamePanel.getWidth() / 5, gamePanel.getHeight() / 10, null);
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthSBg"), 10,
                10,gamePanel.getWidth() / 5 - 20, gamePanel.getHeight() / 10 - 20, null);
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthbar"), 10,
                10,(gamePanel.getWidth() / 5 - 20) * gamePanel.player.attributeManager.getHealth() /
                gamePanel.player.attributeManager.getMaxHealth(), gamePanel.getHeight() / 10 - 20, null);
    }

}
