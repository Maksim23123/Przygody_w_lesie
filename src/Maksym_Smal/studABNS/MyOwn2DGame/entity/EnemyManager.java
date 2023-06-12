package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.Random;
import Maksym_Smal.studABNS.MyOwn2DGame.items.Item;
import Maksym_Smal.studABNS.MyOwn2DGame.items.LootTable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EnemyManager {
    GamePanel gamePanel;

    ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void generateEnemies(int difficulty) {
        int enemiesCount = 0;
        int enemiesLvl = 0;

        if (difficulty > 1) {
            enemiesCount = Random.getRandomInt(difficulty, true);
        }
        else {
            enemiesCount = 1;
        }

        if (enemiesCount > 20) {
            enemiesCount = 20;
        }

        if (difficulty > 1) {
            enemiesLvl = difficulty - enemiesCount;
        }
        else {
            enemiesLvl = 1;
        }


        for (int i = 0; i < enemiesCount; i++) {
            int cordX = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;
            int cordY = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;

            Enemy enemy = new Goblin(gamePanel, enemiesLvl);
            enemy.setPosition(cordX, cordY);
            enemies.add(enemy);
        }

        for (int i = 0; i < enemiesCount / 2; i++) {
            int cordX = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;
            int cordY = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;

            Enemy enemy = new GoblinArcher(gamePanel, enemiesLvl);
            enemy.setPosition(cordX, cordY);
            enemies.add(enemy);
        }

        for (int i = 0; i < enemiesCount / 5; i++) {
            int cordX = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;
            int cordY = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;

            Enemy enemy = new GoblinSupport(gamePanel, enemiesLvl);
            enemy.setPosition(cordX, cordY);
            enemies.add(enemy);
        }
    }

    public void draw(Graphics2D g2) {
        ArrayList<Enemy> enemiesCopy = new ArrayList<>(enemies);
        Iterator<Enemy> iterator = enemiesCopy.iterator();
        boolean drawComplete = false;
        while (iterator.hasNext()) {
            Enemy item = iterator.next();
            if ( item != null && !item.getDrowned()) {
                item.draw(g2);
                item.setDrowned(true);
                drawComplete = true;
            }
        }
        if (!drawComplete) {
            iterator = enemiesCopy.iterator();
            while (iterator.hasNext()) {
                Enemy item = iterator.next();
                if (!item.getUpdated()) {
                    item.setDrowned(false);
                }
            }
            if (!enemies.isEmpty()) {
                draw(g2);
            }
        }
    }

    public void update() {

        ArrayList<Enemy> enemiesCopy = new ArrayList<>(enemies);

        String lastRemoved = "Chest";

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy item = iterator.next();
            if (item.attributeManager.getHealth() < 1) {
                item.getLootTable().dropItems(item.worldX, item.worldY);
                lastRemoved = item.getClass().getSimpleName();
                iterator.remove();
            }
        }

        if (enemies.isEmpty() && !lastRemoved.equals("Chest")) {
            enemies.add(new Chest(gamePanel));
//            LootTable lootTable = new LootTable(1, 0, gamePanel);
//            lootTable.addItem("powerUp", 5);
//            lootTable.dropItems((gamePanel.maxWorldRow * gamePanel.tileSize) / 2,
//                    (gamePanel.maxWorldCol * gamePanel.tileSize) / 2);
        }

        iterator = enemiesCopy.iterator();
        boolean updateComplete = false;
        while (iterator.hasNext()) {
            Enemy item = iterator.next();
            if (!item.getUpdated()) {
                item.setMoving(true);
                item.update();
                updateComplete = true;
            }
        }
        if (!updateComplete) {
            iterator = enemiesCopy.iterator();
            while (iterator.hasNext()) {
                Enemy item = iterator.next();
                if (!item.getUpdated()) {
                    item.setUpdated(false);
                }
            }
            if (!enemies.isEmpty()) {
                update();
            }
        }
    }
}
