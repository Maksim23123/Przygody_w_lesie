package Maksym_Smal.studABNS.MyOwn2DGame.items;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns.AutoPickupPermanentPattern;
import Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns.ItemBehaviorPattern;

import java.awt.*;

public class Item {

    private String name;

    int count = 1;

    public int worldX;

    public int worldY;

    public GamePanel gamePanel;

    String imageTag;

    public boolean inDeleteQuery = false;

    private ItemAttributeManager itemAttributeManager;

    public ItemBehaviorPattern itemBehaviorPattern = new ItemBehaviorPattern(gamePanel);

    public int complementValue = 1;

    Item(String name, String imageTag, int worldX, int worldY, GamePanel gamePanel) {
        this.name = name;
        this.gamePanel = gamePanel;
        this.imageTag = imageTag;
        this.worldX = worldX;
        this.worldY = worldY;
    }


    Item(Item item) {
        this.gamePanel = item.gamePanel;
        this.name = item.name;
        this.imageTag = item.imageTag;
        this.worldX = item.worldX;
        this.worldY = item.worldY;
        this.itemAttributeManager = item.itemAttributeManager;
        this.itemBehaviorPattern = item.itemBehaviorPattern;
    }

    void update() {
        itemBehaviorPattern.update(this);
    }

    public ItemAttributeManager getItemAttributeManager() {
        return itemAttributeManager;
    }

    public void draw(Graphics2D g2, int sizeX, int sizeY) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g2.drawImage(gamePanel.imageManager.getImageByTag(imageTag), screenX, screenY, sizeX, sizeY, null);
    }

    public void setItemAttributeManager(ItemAttributeManager itemAttributeManager) {
        this.itemAttributeManager = itemAttributeManager;
    }

    public String getName() {
        return name;
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }
}
