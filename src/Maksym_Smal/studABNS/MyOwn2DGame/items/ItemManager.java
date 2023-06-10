package Maksym_Smal.studABNS.MyOwn2DGame.items;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns.AutoPickupOneTimePattern;
import Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns.AutoPickupPermanentPattern;
import Maksym_Smal.studABNS.MyOwn2DGame.items.itemBehaviorPatterns.PickupByHandPermanentPattern;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {
    private ArrayList<Item> itemsExamples = new ArrayList<>();

    public ArrayList<Item> itemsOnTheFloor = new ArrayList<>();

    GamePanel gamePanel;

    public ItemManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        defineItemsExamples();
    }

    public void update() {
        Iterator<Item> iterator = itemsOnTheFloor.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();

            item.update();
            if (item.inDeleteQuery) {
                iterator.remove();
            }
        }
    }

    private void defineItemsExamples() {

        Item currentItem = new Item("heart" ,"heart", 0, 0, gamePanel);
        ItemAttributeManager currentItemAttributeManager = new ItemAttributeManager();
        currentItemAttributeManager.setHealth(5, true);
        currentItem.setItemAttributeManager(currentItemAttributeManager);
        currentItem.itemBehaviorPattern = new AutoPickupOneTimePattern(gamePanel);

        itemsExamples.add(currentItem);


        currentItem = new Item("grayHeart" ,"grayHeart", 0, 0, gamePanel);
        currentItemAttributeManager = new ItemAttributeManager();
        currentItemAttributeManager.setMaxHealth(5);
        currentItem.setItemAttributeManager(currentItemAttributeManager);
        currentItem.itemBehaviorPattern = new AutoPickupPermanentPattern(gamePanel);

        itemsExamples.add(currentItem);


        currentItem = new Item("powerUp" ,"hand", 0, 0, gamePanel);
        currentItemAttributeManager = new ItemAttributeManager();
        currentItemAttributeManager.setDamage(4);
        currentItem.setItemAttributeManager(currentItemAttributeManager);
        currentItem.itemBehaviorPattern = new AutoPickupPermanentPattern(gamePanel);

        itemsExamples.add(currentItem);


        currentItem = new Item("brokenHeart" ,"brokenGrayHeart", 0, 0, gamePanel);
        currentItemAttributeManager = new ItemAttributeManager();
        currentItemAttributeManager.setMaxHealth(-5);
        currentItemAttributeManager.setInputDamageMultiple(-0.1d);
        currentItem.setItemAttributeManager(currentItemAttributeManager);
        currentItem.itemBehaviorPattern =
                new PickupByHandPermanentPattern(gamePanel,"activeBrokenGrayHeart");

        itemsExamples.add(currentItem);
    }

    public ArrayList<Item> getItemsExamples() {
        return itemsExamples;
    }

    public void draw(Graphics2D g2) {
        for (Item item : itemsOnTheFloor) {
            item.draw(g2, (int) (gamePanel.tileSize / 1.5), (int) (gamePanel.tileSize / 1.5));
        }
    }
}
