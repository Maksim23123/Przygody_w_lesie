package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.KeyHandler;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.Weapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class Player extends Entity{
    KeyHandler keyHandler;

    final public int screenCenterX;
    final public int screenCenterY;

    public int screenX;
    public int screenY;

    int roomIndexX;
    int roomIndexY;

    public Weapon weapon;

    String weaponState = "right";
    boolean weaponMoving = false;
    int weaponAngleCounter = 0;
    int weaponIncreaseValue = 45;
    int weaponReloadTimer = 0;
    private int parryTime = 0;

    int rollingTime = 0;
    int rollingCooldown = 0;

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
            weapon = new Weapon(gamePanel ,gamePanel.imageManager.getImageByTag("sword"));

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
        attributeManager.speed = 9; //7
        direction = "down";

        attributeManager.setDamage(2);
        attributeManager.setAttackCooldown(10);
        attributeManager.setAttackRange(90);
        attributeManager.setAttackFarRange(gamePanel.tileSize * 4);
        attributeManager.setMaxHealth(20);
        attributeManager.setHealth(attributeManager.getMaxHealth(), false);
    }

    public int getRoomIndexX() {
        return roomIndexX;
    }

    public int getRoomIndexY() {
        return roomIndexY;
    }

    public int getRollingTime() {
        return rollingTime;
    }

    private void updateWeapon() {

        int distance = 50;

        double mouseDistance = Math.sqrt(Math.pow(screenX - gamePanel.mouseHandler.getMousePosX(), 2) +
                Math.pow(screenY - gamePanel.mouseHandler.getMousePosY(), 2));

        double distanceY = Math.abs(screenY - gamePanel.mouseHandler.getMousePosY());

        double cosA = distanceY / mouseDistance;
        int angle = (int) Math.toDegrees(Math.acos(cosA));

        if (screenX > gamePanel.mouseHandler.getMousePosX() && screenY > gamePanel.mouseHandler.getMousePosY()) {
            angle = 360 - angle;
        }
        else if (screenX > gamePanel.mouseHandler.getMousePosX()) {
            angle = 180 + angle;
        }
        else if (!(screenY > gamePanel.mouseHandler.getMousePosY())) {
            angle = 180 - angle;
        }

        if (gamePanel.mouseHandler.getClicked() && weaponReloadTimer == 0) {
            weaponMoving = true;
            weaponReloadTimer = attributeManager.attackCooldown;
            parryTime = attributeManager.getAttackCooldown();
            dealDamage(angle);
        }

        angle += weaponIncreaseValue;

        if (weaponReloadTimer > 0) {
            weaponReloadTimer--;
        }

        if (parryTime > 0) {
            parryTime--;
        }

        if (weaponMoving) {
            weaponAngleCounter += 15;
            if (weaponState.equals("right")) {
                angle -= weaponAngleCounter;
            }
            else {
                angle += weaponAngleCounter;
            }

            if (weaponAngleCounter >= 90) {
                if (weaponState.equals("right")) {
                    weaponState = "left";
                    weaponIncreaseValue = -45;
                }
                else {
                    weaponState = "right";
                    weaponIncreaseValue = 45;
                }
                weaponMoving = false;
                weaponAngleCounter = 0;
            }
        }
        else {
            weapon.setAngle(angle);
        }

        weapon.setScreenX((int) (screenX - distance * Math.cos(Math.toRadians(angle + 90))));
        weapon.setScreenY((int) (screenY - distance * Math.sin(Math.toRadians(angle + 90))));
    }

    void dealDamage(int direction) {
        Iterator<Enemy> iterator = gamePanel.enemyManager.enemies.iterator();
        while (iterator.hasNext()) {
            Enemy item = iterator.next();
            double distance = Math.sqrt(Math.pow(worldX - item.worldX,2) +
                    Math.pow(worldY - item.worldY,2));
            if (distance < attributeManager.getAttackFarRange()) {
                double distanceY = Math.abs(Math.abs(worldY - item.worldY));
                double cosA = distanceY / distance;

                int angle = (int) Math.toDegrees(Math.acos(cosA));
                if (worldX > item.worldX && worldY > item.worldY) {
                    angle = 360 - angle;
                }
                else if (worldX > item.worldX) {
                    angle = 180 + angle;
                }
                else if (!(worldY > item.worldY)) {
                    angle = 180 - angle;
                }


                if (Math.abs(angle - direction) < attributeManager.getAttackRange()) {
                    item.attributeManager.dealDamage(attributeManager.getOutputDamage());
                    item.pushAway(worldX, worldY, 15, 5);
                }
                else if (direction < angle && direction + (360 - angle) < attributeManager.getAttackRange()) {
                    item.attributeManager.dealDamage(attributeManager.getOutputDamage());
                    item.pushAway(worldX, worldY, 15, 5);
                }
                else if (direction > angle && angle + (360 - direction) < attributeManager.getAttackRange()) {
                    item.attributeManager.dealDamage(attributeManager.getOutputDamage());
                    item.pushAway(worldX, worldY, 15, 5);
                }
            }
        }
    }



    public void update() {
        updateCameraRubberBand();

        attributeManager.update();

        hitBox.update();

        gamePanel.collisionChecker.testMotion(this, 0, 0);


        if (keyHandler.getPressedButtonsQueue().contains("Space") && rollingCooldown == 0) {
            rollingTime = 20;
            rollingCooldown = 45;
        }

        if (stackInWall) {
            move(45 * gamePanel.tileSize,45 * gamePanel.tileSize, true, 1,
                    9);
        }
        else if (pushedAway) {
            move(pushPointX, pushPointY, false, -1, getPushAwaySpeed());
            setPushAwayDuration(getPushAwayDuration() - 1);
        }
        else if (rollingTime > 0) {
            controlMoveWithIgnoringEntities(attributeManager.speed * 2);
        }
        else {
            controlledMotion();
        }

        if (rollingCooldown > 0) {
            rollingCooldown--;
        }

        if (rollingTime > 0) {
            rollingTime--;
        }

        setStackInWall(false);
        updateWeapon();
    }

    void controlledMotion() {
        boolean verticalMotion = false;
        boolean horizontalMotion = false;

        int motionX = 0;
        int motionY = 0;

        int complementValueX = 0;
        int complementValueY = 0;


        if (keyHandler.getPressedButtonsQueue().contains("A") ||
                keyHandler.getPressedButtonsQueue().contains("W") ||
                keyHandler.getPressedButtonsQueue().contains("D") ||
                keyHandler.getPressedButtonsQueue().contains("S")) {
            if (keyHandler.getPressedButtonsQueue().contains("W")){
                direction = "up";
                motionY -= attributeManager.speed;
                complementValueY = -1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("S")){
                direction = "down";
                motionY += attributeManager.speed;
                complementValueY = 1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("D")){
                direction = "right";
                motionX += attributeManager.speed;
                complementValueX = 1;
                horizontalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("A")){
                direction = "left";
                motionX -= attributeManager.speed;
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

            checkingMovementBetweenRooms();

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

    void checkingMovementBetweenRooms() {
        if (worldX < (gamePanel.maxWorldCol * gamePanel.tileSize) / 3 - 1 * gamePanel.tileSize) {
            worldX += (gamePanel.maxWorldCol * gamePanel.tileSize) / 3;
            roomIndexX -= 1;
            gamePanel.updateTileMap();
        }
        if (worldX > ((gamePanel.maxWorldCol * gamePanel.tileSize) / 3) * 2 - 1) {
            worldX -= (gamePanel.maxWorldCol * gamePanel.tileSize) / 3;
            roomIndexX += 1;
            gamePanel.updateTileMap();
        }

        if (worldY < (gamePanel.maxWorldRow * gamePanel.tileSize) / 3 - 1 * gamePanel.tileSize) {
            worldY += (gamePanel.maxWorldRow * gamePanel.tileSize) / 3;
            roomIndexY -= 1;
            gamePanel.updateTileMap();
        }
        if (worldY > ((gamePanel.maxWorldRow * gamePanel.tileSize) / 3) * 2 - 1) {
            worldY -= (gamePanel.maxWorldRow * gamePanel.tileSize) / 3;
            roomIndexY += 1;
            gamePanel.updateTileMap();
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
            screenX += motionY / 2;
            worldY += motionY;
            screenY += motionY / 2;
        }
        else {
            if (gamePanel.collisionChecker.testMotion(this, motionX, 0)) {
                worldX += motionX;
                screenX += motionX / 2;
            }

            if (gamePanel.collisionChecker.testMotion(this, 0, motionY)) {
                worldY += motionY;
                screenX += motionX / 2;
            }
        }

        checkingMovementBetweenRooms();
    }

    void controlMoveWithIgnoringEntities(int speed) {
        boolean verticalMotion = false;
        boolean horizontalMotion = false;

        int motionX = 0;
        int motionY = 0;

        int complementValueX = 0;
        int complementValueY = 0;

        if (keyHandler.getPressedButtonsQueue().contains("A") ||
                keyHandler.getPressedButtonsQueue().contains("W") ||
                keyHandler.getPressedButtonsQueue().contains("D") ||
                keyHandler.getPressedButtonsQueue().contains("S")) {
            if (keyHandler.getPressedButtonsQueue().contains("W")) {
                direction = "up";
                motionY -= speed;
                complementValueY = -1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("S")) {
                direction = "down";
                motionY += speed;
                complementValueY = 1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("D")) {
                direction = "right";
                motionX += speed;
                complementValueX = 1;
                horizontalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("A")) {
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
                if (gamePanel.collisionChecker.testMotionWithIgnoringEntities(this, motionX, 0)) {
                    worldX += motionX;
                    screenX += motionX;
                }
                if (gamePanel.collisionChecker.testMotionWithIgnoringEntities(this, 0, motionY)) {
                    worldY += motionY;
                    screenY += motionY;
                }
            } else if (verticalMotion) {
                if (gamePanel.collisionChecker.testMotionWithIgnoringEntities(this, 0, motionY)) {
                    worldY += motionY;
                    screenY += motionY;
                }
            } else if (horizontalMotion) {
                if (gamePanel.collisionChecker.testMotionWithIgnoringEntities(this, motionX, 0)) {
                    worldX += motionX;
                    screenX += motionX;
                }
            }
        }

        checkingMovementBetweenRooms();
    }
}
