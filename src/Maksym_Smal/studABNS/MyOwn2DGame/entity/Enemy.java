package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.items.LootTable;

import java.awt.*;

public abstract class Enemy extends Entity{

    int targetCordX;
    int targetCordY;

    int confusionTime;

    int screenX;
    int screenY;

    boolean moving;

    private boolean updated = false;
    private boolean drowned = false;

    protected LootTable lootTable;

    public Enemy(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setDefaultValues();

        defineLootTable();
    }


    private void defineLootTable() {
        lootTable = new LootTable(1, 15,gamePanel);

        lootTable.addItem("heart", 5);
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void setPosition(int cordX, int cordY) {
        worldX = cordX;
        worldY = cordY;
    }

    public boolean getUpdated() {
        return updated;
    }

    public void setUpdated(boolean value) {
        updated = value;
    }

    public boolean getDrowned() {
        return drowned;
    }

    public void setDrowned(boolean drowned) {
        this.drowned = drowned;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public abstract void setDefaultValues();

    public abstract void update();

    public abstract void draw(Graphics2D g2);
}
