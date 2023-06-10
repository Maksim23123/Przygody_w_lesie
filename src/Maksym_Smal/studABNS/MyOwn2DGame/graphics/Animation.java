package Maksym_Smal.studABNS.MyOwn2DGame.graphics;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

    String name;

    private ArrayList<String> imageTagList = new ArrayList<>();

    private int speed = 1;

    private int iterationCounter = 0;
    private int currentFrame = 0;

    private boolean isActive = true;

    GamePanel gamePanel;

    public Animation(String name, int speed, GamePanel gamePanel) {
        this.name = name;
        setSpeed(speed);
        this.gamePanel = gamePanel;
    }

    public void update() {
        if (isActive) {
            iterationCounter++;

            if (iterationCounter >= speed) {
                iterationCounter = 0;
                currentFrame++;
            }

            if (currentFrame >= imageTagList.size()) {
                currentFrame = 0;
            }
        }
    }

    public void addImage(String imageTag) {
        imageTagList.add(imageTag);
    }

    public BufferedImage getCurrentImage() {
        return gamePanel.imageManager.getImageByTag(imageTagList.get(currentFrame));
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setSpeed(int speed) {
        if (speed > 0) {
            this.speed = speed;
        }
        else {
            this.speed = 1;
        }
    }

    public int getSpeed() {
        return speed;
    }
}
