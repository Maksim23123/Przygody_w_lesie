package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;

public abstract class Enemy extends Entity{

    boolean moving;

    private boolean updated = false;
    private boolean drowned = false;

    public Enemy(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setDefaultValues();
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