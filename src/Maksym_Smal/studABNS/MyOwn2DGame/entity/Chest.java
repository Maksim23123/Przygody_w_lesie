package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.items.LootTable;

import java.awt.*;

public class Chest extends Enemy{



    public Chest(GamePanel gamePanel) {
        super(gamePanel);

        setDefaultValues();

        defineLootTable();
    }

    @Override
    public void setDefaultValues() {
        worldX = (gamePanel.maxWorldRow * gamePanel.tileSize) / 2;
        worldY = (gamePanel.maxWorldCol * gamePanel.tileSize) / 2;

        attributeManager.setMaxHealth(1);
    }

    @Override
    public void update() {
        attributeManager.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g2.drawImage(gamePanel.imageManager.getImageByTag("chest"), screenX, screenY,
                gamePanel.tileSize, gamePanel.tileSize, null);
    }

    void defineLootTable() {
        lootTable = new LootTable(1, 0, gamePanel);

        lootTable.addItem("grayHeart", 5);
        lootTable.addItem("powerUp", 5);
        lootTable.addItem("brokenHeart", 2);
    }
}
