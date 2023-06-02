package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.Random;

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

    public void generateEnemies(int count) {


        for (int i = 0; i < count / 2; i++) {
            int cordX = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;
            int cordY = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;

            Enemy enemy = new Goblin(gamePanel);
            enemy.setPosition(cordX, cordY);
            enemies.add(enemy);
        }

        for (int i = 0; i < count / 2; i++) {
            int cordX = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;
            int cordY = (Random.getRandomInt(20, false) + 35) * gamePanel.tileSize;

            Enemy enemy = new GoblinArcher(gamePanel);
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

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy item = iterator.next();
            if (item.attributeManager.getHealth() < 1) {
                iterator.remove();
            }
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
