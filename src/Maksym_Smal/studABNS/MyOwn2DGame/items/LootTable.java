package Maksym_Smal.studABNS.MyOwn2DGame.items;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.Random;

import java.util.ArrayList;

public class LootTable {

    ArrayList<LootSlot> lootSlots = new ArrayList<>();

    int maxDroppingItemsCount;

    int noDropChance = 0;

    GamePanel gamePanel;

    public LootTable(int maxDroppingItemsCount, int noDropChance, GamePanel gamePanel) {
        this.maxDroppingItemsCount = maxDroppingItemsCount;
        this.noDropChance = noDropChance;
        this.gamePanel = gamePanel;
    }

    public void addItem(String name, int dropChance) {
        for (Item item : gamePanel.itemManager.getItemsExamples()) {
            if (name.equals(item.getName())) {
                lootSlots.add(new LootSlot(item, dropChance));
            }
        }
    }

    public void dropItems(int cordX, int cordY) {
        int maxNumber = noDropChance;
        for (LootSlot i : lootSlots) {
            maxNumber += i.dropChance;
        }

        for (int i = 0; i < maxDroppingItemsCount; i++ ) {
            int number = Random.getRandomInt(maxNumber, false);

            if (noDropChance > number) {
                continue;
            }

            int itemNumber = noDropChance;
            for (LootSlot j : lootSlots) {
                itemNumber += j.dropChance;
                if (itemNumber > number) {
                    Item item = new Item(j);
                    item.worldX = cordX;
                    item.worldY = cordY;
                    gamePanel.itemManager.itemsOnTheFloor.add(item);
                    break;
                }
            }
        }
    }
}
