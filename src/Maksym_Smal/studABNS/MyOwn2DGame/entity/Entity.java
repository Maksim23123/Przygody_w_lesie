package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public HitBox hitBox;
    public int worldX, worldY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;

    boolean pushedOut = false;
    int pushPointX = 0;
    int pushPointY = 0;
    boolean stackInWall = false;

    private int pushAwaySpeed;
    protected boolean pushedAway = false;
    private int pushAwayDuration;


    GamePanel gamePanel;

    int sizeX = 64;
    int sizeY = 64;

    public AttributeManager attributeManager = new AttributeManager();

    public AttributeManager defaultAttributes = new AttributeManager();

    public void setPushAwaySpeed(int pushAwaySpeed) {
        if (pushAwaySpeed <= 0) {
            this.pushAwaySpeed = 1;
        }
        else {
            this.pushAwaySpeed = pushAwaySpeed;
        }
    }

    public int getPushAwaySpeed() {
        return pushAwaySpeed;
    }

    public int getPushAwayDuration() {
        return pushAwayDuration;
    }

    public void setPushAwayDuration(int pushAwayDuration) {
        if (pushAwayDuration <= 0) {
            pushedAway = false;
        }
        else {
            this.pushAwayDuration = pushAwayDuration;
        }
    }

    public void setPushPoint(int cordX, int cordY) {
        pushPointX = cordX;
        pushPointY = cordY;
    }

    public void setPushedOut(boolean pushedOut) {
        this.pushedOut = pushedOut;
    }

    public void setStackInWall(boolean value) {
        stackInWall = value;
    }

    public void move(int directedX, int directedY, boolean ignoreCollision, int complementMultiplier, int speed) {
        int motionX;
        int motionY;

        int complementValueX;
        int complementValueY;

        double distance = Math.sqrt(Math.pow(worldX - directedX, 2) + Math.pow(worldY - directedY, 2));
        int distanceX = Math.abs(worldX - directedX);
        int distanceY = Math.abs(worldY - directedY);

        if (worldX - directedX > 0) {
            complementValueX = -1;
        }
        else {
            complementValueX = 1;
        }

        if (worldY - directedY > 0) {
            complementValueY = -1;
        }
        else {
            complementValueY = 1;
        }

        complementValueX *= complementMultiplier;
        complementValueY *= complementMultiplier;

        double sin = distanceY / distance;
        double cos = distanceX / distance;

        motionX = (int)(speed * cos) * complementValueX;
        motionY = (int)(speed * sin) * complementValueY;

        if (ignoreCollision)
        {
            worldX += motionX;
            worldY += motionY;
        }
        else {
            if (gamePanel.collisionChecker.testMotion(this, motionX, 0)) {
                worldX += motionX;
            }

            if (gamePanel.collisionChecker.testMotion(this, 0, motionY)) {
                worldY += motionY;
            }
        }
    }

    public void pushAway(int cordX, int cordY,int speed, int duration) {
        pushedAway = true;
        setPushAwaySpeed(speed);
        setPushAwayDuration(duration);
        pushPointX = cordX;
        pushPointY = cordY;
    }
}
