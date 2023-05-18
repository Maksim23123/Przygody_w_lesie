package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    final public int screenCenterX;
    final public int screenCenterY;

    public int screenX;
    public int screenY;

    int roomIndexX;

    int roomIndexY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenCenterX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;
        screenCenterY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;

        screenX = screenCenterX;
        screenY = screenCenterY;

        solidArea = new Rectangle(11, 22, 42, 42);

        hitBox = new HitBox(gamePanel, this);

        getPlayerImage();
    }

    public void updateCameraRubberBand() {
        int cameraSpeedX = 2;
        int cameraSpeedY = 2;
        int maxDistance = 24;
        float distance = (float) Math.sqrt((Math.pow((screenX - screenCenterX), 2d) +
                Math.pow((screenY - screenCenterY), 2d)));
        float sin = (Math.abs(screenCenterX - screenX) / distance);
        float cos = (Math.abs(screenCenterY - screenY) / distance);

        if (screenCenterX > screenX) {
            screenX += cameraSpeedX * sin * (distance / maxDistance);
        }
        else if (screenCenterX < screenX) {
            screenX -= cameraSpeedX * sin * (distance / maxDistance);
        }

        if (screenCenterY > screenY) {
            screenY += cameraSpeedY * cos * (distance / maxDistance);
        }
        else if (screenCenterY < screenY) {
            screenY -= cameraSpeedY * cos * (distance / maxDistance);
        }
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/walk_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/walk_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/player/boy_right_2.png")));

        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void setDefaultValues(int roomIndexX, int roomIndexY){
        worldX = gamePanel.tileSize * (gamePanel.maxWorldRow / 2);
        worldY = gamePanel.tileSize * (gamePanel.maxWorldCol / 2);
        System.out.println("RoomIndexX: " + roomIndexX);
        System.out.println("RoomIndexY: " + roomIndexY);
        this.roomIndexX = roomIndexX;
        this.roomIndexY = roomIndexY;
        speed = 9; //7
        direction = "down";
    }

    public int getRoomIndexX() {
        return roomIndexX;
    }

    public int getRoomIndexY() {
        return roomIndexY;
    }

    public void update() {

        updateCameraRubberBand();

        boolean verticalMotion = false;
        boolean horizontalMotion = false;

        int motionX = 0;
        int motionY = 0;

        int complementValueX = 0;
        int complementValueY = 0;

        hitBox.update();

        if (keyHandler.getPressedButtonsQueue().contains("A") ||
                keyHandler.getPressedButtonsQueue().contains("W") ||
                keyHandler.getPressedButtonsQueue().contains("D") ||
                keyHandler.getPressedButtonsQueue().contains("S")) {
            if (keyHandler.getPressedButtonsQueue().contains("W")){
                direction = "up";
                motionY -= speed;
                complementValueY = -1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("S")){
                direction = "down";
                motionY += speed;
                complementValueY = 1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("D")){
                direction = "right";
                motionX += speed;
                complementValueX = 1;
                horizontalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("A")){
                direction = "left";
                motionX -= speed;
                complementValueX = -1;
                horizontalMotion = true;
            }


            if (verticalMotion && horizontalMotion) {
                motionX *= 0.707;
                motionY *= 0.707;
                motionX += complementValueX;
                motionY += complementValueY;
                if (gamePanel.collisionChecker.testMotion(this, motionX, 0)) {
                    worldX += motionX;
                    screenX += motionX;
                }
                if (gamePanel.collisionChecker.testMotion(this, 0, motionY)) {
                    worldY += motionY;
                    screenY += motionY;
                }
            }
            else if (verticalMotion) {
                if (gamePanel.collisionChecker.testMotion(this, 0, motionY)) {
                    worldY += motionY;
                    screenY += motionY;
                }
            }
            else if (horizontalMotion) {
                if (gamePanel.collisionChecker.testMotion(this, motionX, 0)) {
                    worldX += motionX;
                    screenX += motionX;
                }
            }


            //checking movement between rooms
            if (worldX < (gamePanel.maxWorldCol * gamePanel.tileSize) / 3 - 1 * gamePanel.tileSize) {
                worldX += (gamePanel.maxWorldCol * gamePanel.tileSize) / 3 - 1;
                roomIndexX -= 1;
                gamePanel.updateTileMap();
                System.out.println("RoomIndexX: " + roomIndexX);
                System.out.println("RoomIndexY: " + roomIndexY);
            }
            if (worldX > ((gamePanel.maxWorldCol * gamePanel.tileSize) / 3) * 2 - 1) {
                worldX -= (gamePanel.maxWorldCol * gamePanel.tileSize) / 3 + 1;
                roomIndexX += 1;
                gamePanel.updateTileMap();
                System.out.println("RoomIndexX: " + roomIndexX);
                System.out.println("RoomIndexY: " + roomIndexY);
            }

            if (worldY < (gamePanel.maxWorldRow * gamePanel.tileSize) / 3 - 1 * gamePanel.tileSize) {
                worldY += (gamePanel.maxWorldRow * gamePanel.tileSize) / 3 - 1;
                roomIndexY -= 1;
                gamePanel.updateTileMap();
                System.out.println("RoomIndexX: " + roomIndexX);
                System.out.println("RoomIndexY: " + roomIndexY);
            }
            if (worldY > ((gamePanel.maxWorldRow * gamePanel.tileSize) / 3) * 2 - 1) {
                worldY -= (gamePanel.maxWorldRow * gamePanel.tileSize) / 3 + 1;
                roomIndexY += 1;
                gamePanel.updateTileMap();
                System.out.println("RoomIndexX: " + roomIndexX);
                System.out.println("RoomIndexY: " + roomIndexY);
            }

            //---


            spriteCounter ++;
            if (spriteCounter  > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }



        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
