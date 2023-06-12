package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.Random;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.Animation;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.AnimationManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.Weapon;

import java.awt.*;


public class Goblin extends Enemy {

    public Weapon weapon;
    String weaponState = "right";
    boolean weaponMoving = false;

    int weaponAngleCounter = 0;
    int weaponIncreaseValue = 45;
    int weaponReloadTimer = 0;

    private AnimationManager animationManager = new AnimationManager();

    public Goblin(GamePanel gamePanel, int lvl) {
        super(gamePanel);

        confusionTime = 30;

        attributeManager.setRandomAttributes(lvl);

        setDefaultValues();
        getGoblinImage();
    }


    @Override
    public void setPosition(int cordX, int cordY) {
        worldX = cordX;
        worldY = cordY;
    }

    @Override
    public void setDefaultValues(){
        solidArea = new Rectangle(11, 22, 42, 42);
        hitBox = new HitBox(gamePanel, this);
        setPosition(45  * gamePanel.tileSize, 45  * gamePanel.tileSize);
        direction = "down";
    }

    @Override
    public void update() {
        targetCordX = gamePanel.player.worldX;
        targetCordY = gamePanel.player.worldY;

        hitBox.update();
        attributeManager.update();
        animationManager.update();

        if (gamePanel.collisionChecker.testMotion(this, 0, 0)) {
            pushedOut = false;
        }

        if (stackInWall) {
            move(45 * gamePanel.tileSize,45 * gamePanel.tileSize, true, 1,
                    9);
        }
        else if (pushedOut) {
            move(pushPointX, pushPointY, true, -1,
                    9);
        }
        else if (pushedAway) {
            move(pushPointX, pushPointY, false, -1, getPushAwaySpeed());
            setPushAwayDuration(getPushAwayDuration() - 1);
            if (!pushedAway) {
                confusionTime += Random.getRandomInt(20, false) + 10;
            }
        }
        else if (moving && confusionTime <= 0) {
            move(targetCordX, targetCordY, false, 1, attributeManager.speed);
        }

        if (confusionTime > 0) {
            confusionTime--;
        }

        setStackInWall(false);
        updateWeapon();
    }

    @Override
    public void draw(Graphics2D g2) {

        screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g2.drawImage(animationManager.getCurrentImage(), screenX, screenY,
                gamePanel.tileSize, gamePanel.tileSize, null);

        weapon.draw(g2);

        g2.drawImage(gamePanel.imageManager.getImageByTag("healthBBg"), screenX + gamePanel.tileSize / 6,
                screenY - gamePanel.tileSize / 3,
                gamePanel.tileSize / 6 * 4, gamePanel.tileSize / 6, null);
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthSBg"), screenX + gamePanel.tileSize / 6 + 2,
                screenY - gamePanel.tileSize / 3 + 2,
                gamePanel.tileSize / 6 * 4 - 4, gamePanel.tileSize / 6 - 4, null);
        if (attributeManager.getHealth() >= 0) {
            g2.drawImage(gamePanel.imageManager.getImageByTag("healthbar"), screenX + gamePanel.tileSize / 6 + 2,
                    screenY - gamePanel.tileSize / 3 + 2,
                    (gamePanel.tileSize / 6 * 4 - 4) * attributeManager.getHealth() / attributeManager.getMaxHealth(),
                    gamePanel.tileSize / 6 - 4, null);
        }
    }

    private void updateWeapon() {

        int distance = 50;

        double targetDistance = Math.sqrt(Math.pow(worldX - targetCordX, 2) +
                Math.pow(worldY - targetCordY, 2));

        double distanceY = Math.abs(worldY - targetCordY);

        double cosA = distanceY / targetDistance;
        int angle = (int) Math.toDegrees(Math.acos(cosA));

        if (worldX > targetCordX && worldY > targetCordY) {
            angle = 360 - angle;
        } else if (worldX > targetCordX) {
            angle = 180 + angle;
        } else if (!(worldY > targetCordY)) {
            angle = 180 - angle;
        }

        if (targetDistance < attributeManager.getAttackFarRange() && weaponReloadTimer == 0 &&
                confusionTime <= 0) {
            weaponMoving = true;
            weaponReloadTimer = attributeManager.attackCooldown;
            if (gamePanel.player.getRollingTime() == 0) {
                gamePanel.soundManager.playSound(1);
                gamePanel.player.attributeManager.dealDamage(attributeManager.getOutputDamage());
                gamePanel.player.pushAway(worldX, worldY, 15, 5);
            }
        }

        angle += weaponIncreaseValue;

        if (weaponReloadTimer > 0) {
            weaponReloadTimer--;
        }

        if (weaponMoving) {
            weaponAngleCounter += 15;
            if (weaponState.equals("right")) {
                angle -= weaponAngleCounter;
            } else {
                angle += weaponAngleCounter;
            }

            if (weaponAngleCounter >= 90) {
                if (weaponState.equals("right")) {
                    weaponState = "left";
                    weaponIncreaseValue = -45;
                } else {
                    weaponState = "right";
                    weaponIncreaseValue = 45;
                }
                weaponMoving = false;
                weaponAngleCounter = 0;
            }
        } else {
            weapon.setAngle(angle);
        }

        weapon.setScreenX((int) (screenX - distance * Math.cos(Math.toRadians(angle + 90))));
        weapon.setScreenY((int) (screenY - distance * Math.sin(Math.toRadians(angle + 90))));
    }

    public void getGoblinImage() {

        Animation animation = new Animation("goblinDown", 12, gamePanel);
        animation.addImage("goblinDown1");
        animation.addImage("goblinDown2");
        animationManager.addAnimation(animation);

        animationManager.setCurrentAnimation("goblinDown");

        weapon = new Weapon(gamePanel ,gamePanel.imageManager.getImageByTag("wood"));
    }
}
