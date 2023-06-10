package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.control.KeyHandler;
import Maksym_Smal.studABNS.MyOwn2DGame.fileManager.PlayerData;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.Animation;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.AnimationManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.Weapon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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

    public ArrayList<AttributeModifier> attributeModifiers = new ArrayList<>();

    private AnimationManager animationManager = new AnimationManager();

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenCenterX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;
        screenCenterY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;

        screenX = screenCenterX;
        screenY = screenCenterY;

        solidArea = new Rectangle(11, 22, 42, 42);

        hitBox = new HitBox(gamePanel, this);

        direction = "down";

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
        Animation animation = new Animation("playerUp", 12, gamePanel);
        animation.addImage("playerUp1");
        animation.addImage("playerUp2");
        animationManager.addAnimation(animation);

        animation = new Animation("playerDown", 12, gamePanel);
        animation.addImage("playerDown1");
        animation.addImage("playerDown2");
        animationManager.addAnimation(animation);

        animationManager.setCurrentAnimation("playerDown");

        animation = new Animation("playerLeft", 12, gamePanel);
        animation.addImage("playerLeft1");
        animation.addImage("playerLeft2");
        animationManager.addAnimation(animation);

        animation = new Animation("playerRight", 12, gamePanel);
        animation.addImage("playerRight1");
        animation.addImage("playerRight2");
        animationManager.addAnimation(animation);

        animation = new Animation("playerIdle", 12, gamePanel);
        animation.addImage("playerIdle1");
        animation.addImage("playerIdle2");
        animationManager.addAnimation(animation);

        weapon = new Weapon(gamePanel ,gamePanel.imageManager.getImageByTag("sword"));
    }

    public void setDefaultValues(int roomIndexX, int roomIndexY){
        worldX = gamePanel.tileSize * (gamePanel.maxWorldRow / 2);
        worldY = gamePanel.tileSize * (gamePanel.maxWorldCol / 2);

        System.out.println("RoomIndexX: " + roomIndexX);
        System.out.println("RoomIndexY: " + roomIndexY);

        this.roomIndexX = roomIndexX;
        this.roomIndexY = roomIndexY;

        attributeManager.speed = 9; //7

        attributeManager.setDamage(2);
        attributeManager.setAttackCooldown(10);
        attributeManager.setAttackRange(90);
        attributeManager.setAttackFarRange(gamePanel.tileSize * 4);
        attributeManager.setMaxHealth(20);
        attributeManager.setHealth(attributeManager.getMaxHealth(), false);
        attributeManager.setStartImmortalTime(30);

        AttributeModifier attributeModifier = new AttributeModifier();
        attributeModifier.setInfinity(true);
        attributeModifier.setHealthIsRegenerating(true);
        attributeModifiers.add(attributeModifier);

        defaultAttributes = new AttributeManager(attributeManager);
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




//        System.out.println("1: " + mouseCordX);
////        System.out.println("2: " + mouseCordY);

        int cordX = screenX + gamePanel.tileSize / 2;
        int cordY = screenY + gamePanel.tileSize / 2;

        double mouseDistance = Math.sqrt(Math.pow(cordX - gamePanel.mouseHandler.getMousePosX(), 2) +
                Math.pow(cordY - gamePanel.mouseHandler.getMousePosY(), 2));

        double distanceY = Math.abs(cordY - gamePanel.mouseHandler.getMousePosY());

        double cosA = distanceY / mouseDistance;
        int angle = (int) Math.toDegrees(Math.acos(cosA));

        if (cordX > gamePanel.mouseHandler.getMousePosX() && cordY > gamePanel.mouseHandler.getMousePosY()) {
            angle = 360 - angle;
        }
        else if (cordX > gamePanel.mouseHandler.getMousePosX()) {
            angle = 180 + angle;
        }
        else if (!(cordY > gamePanel.mouseHandler.getMousePosY())) {
            angle = 180 - angle;
        }

        if (gamePanel.mouseHandler.getClicked("mouse1") && weaponReloadTimer == 0) {
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

    public PlayerData getData() {
        PlayerData playerData = new PlayerData();
        playerData.setWorldX(worldX);
        playerData.setWorldY(worldY);
        playerData.setRoomIndexX(roomIndexX);
        playerData.setRoomIndexY(roomIndexY);
        playerData.setAttributeManager(attributeManager);
        playerData.setDefaultAttributes(defaultAttributes);
        return playerData;
    }

    public void loadData(PlayerData playerData) {
        worldX = playerData.getWorldX();
        worldY = playerData.getWorldY();

        roomIndexX = playerData.getRoomIndexX();
        roomIndexY = playerData.getRoomIndexY();

        attributeManager = playerData.getAttributeManager();
        defaultAttributes = playerData.getDefaultAttributes();
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
                    gamePanel.soundManager.playSound(0);
                    item.attributeManager.dealDamage(attributeManager.getOutputDamage());
                    item.pushAway(worldX, worldY, 15, 5);
                }
                else if (direction < angle && direction + (360 - angle) < attributeManager.getAttackRange()) {
                    gamePanel.soundManager.playSound(0);
                    item.attributeManager.dealDamage(attributeManager.getOutputDamage());
                    item.pushAway(worldX, worldY, 15, 5);
                }
                else if (direction > angle && angle + (360 - direction) < attributeManager.getAttackRange()) {
                    gamePanel.soundManager.playSound(0);
                    item.attributeManager.dealDamage(attributeManager.getOutputDamage());
                    item.pushAway(worldX, worldY, 15, 5);
                }
            }
        }
    }

    void updateAttributeManager() {
//        if (attributeManager.inputDamage > 0) {
//            System.out.println("inputDamage: " + attributeManager.inputDamage);
//            System.out.println("fact inputDamage: " + (int)((double)(attributeManager.inputDamage) *
//                    attributeManager.inputDamageMultiple));
//        }

        attributeManager.update();
        int health = attributeManager.getHealth();
        int regenerationTime = attributeManager.getRegenerationTime();
        int immortalTime = attributeManager.getImmortalTime();

        attributeManager = new AttributeManager(defaultAttributes);
        attributeManager.setHealth(health, false);
        attributeManager.setRegenerationTime(regenerationTime);
        attributeManager.setImmortalTime(immortalTime);


        Iterator<AttributeModifier> iterator = attributeModifiers.iterator();
        while (iterator.hasNext()) {
            AttributeModifier item = iterator.next();

            if (item.isPermanent()) {
                defaultAttributes.marge(item);

                iterator.remove();
            }
            else if (item.isInfinity()) {
                attributeManager.marge(item);
            }
            else if (item.getDuration() > 0) {
                attributeManager.marge(item);
                item.setDuration(item.getDuration() - 1);
            }
            else {
                iterator.remove();
            }
        }
    }



    public void update() {
        updateCameraRubberBand();

        updateAttributeManager();

        animationManager.update();


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
                animationManager.setCurrentAnimation("playerUp");
                motionY -= attributeManager.speed;
                complementValueY = -1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("S")){
                animationManager.setCurrentAnimation("playerDown");
                motionY += attributeManager.speed;
                complementValueY = 1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("D")){
                animationManager.setCurrentAnimation("playerRight");
                motionX += attributeManager.speed;
                complementValueX = 1;
                horizontalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("A")){
                animationManager.setCurrentAnimation("playerLeft");
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
        else {
            animationManager.setCurrentAnimation("playerIdle");
        }
    }

    void checkingMovementBetweenRooms() {
        if (worldX < (gamePanel.maxWorldCol * gamePanel.tileSize) / 3 - 1 * gamePanel.tileSize) {
            worldX += (gamePanel.maxWorldCol * gamePanel.tileSize) / 3;
            roomIndexX -= 1;
            gamePanel.updateTileMap();
            gamePanel.itemManager.itemsOnTheFloor = new ArrayList<>();
        }
        if (worldX > ((gamePanel.maxWorldCol * gamePanel.tileSize) / 3) * 2 - 1) {
            worldX -= (gamePanel.maxWorldCol * gamePanel.tileSize) / 3;
            roomIndexX += 1;
            gamePanel.updateTileMap();
            gamePanel.itemManager.itemsOnTheFloor = new ArrayList<>();
        }

        if (worldY < (gamePanel.maxWorldRow * gamePanel.tileSize) / 3 - 1 * gamePanel.tileSize) {
            worldY += (gamePanel.maxWorldRow * gamePanel.tileSize) / 3;
            roomIndexY -= 1;
            gamePanel.updateTileMap();
            gamePanel.itemManager.itemsOnTheFloor = new ArrayList<>();
        }
        if (worldY > ((gamePanel.maxWorldRow * gamePanel.tileSize) / 3) * 2 - 1) {
            worldY -= (gamePanel.maxWorldRow * gamePanel.tileSize) / 3;
            roomIndexY += 1;
            gamePanel.updateTileMap();
            gamePanel.itemManager.itemsOnTheFloor = new ArrayList<>();
        }
    }


    public void draw(Graphics2D g2) {
        g2.drawImage(animationManager.getCurrentImage(), screenX, screenY, gamePanel.tileSize,
                gamePanel.tileSize, null);
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
                motionY -= speed;
                complementValueY = -1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("S")) {
                motionY += speed;
                complementValueY = 1;
                verticalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("D")) {
                motionX += speed;
                complementValueX = 1;
                horizontalMotion = true;
            }
            if (keyHandler.getPressedButtonsQueue().contains("A")) {
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
