package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.Random;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.Animation;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.AnimationManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.Weapon;

import java.awt.*;

public class GoblinSupport extends Enemy {

    public Weapon weapon;
    int weaponReloadTimer = 0;

    int confusionTime;

    private AnimationManager animationManager = new AnimationManager();

    public GoblinSupport(GamePanel gamePanel, int lvl) {
        super(gamePanel);

        confusionTime = 30;

        attributeManager.setRandomAttributes(lvl);

        setDefaultValues();

        getGoblinImage();
    }

    @Override
    public void setDefaultValues() {
        solidArea = new Rectangle(11, 22, 42, 42);
        hitBox = new HitBox(gamePanel, this);
        setPosition(45  * gamePanel.tileSize, 45  * gamePanel.tileSize);
        direction = "down";


        attributeManager.setAttackCooldown(30 + 30 * Random.getRandomInt(4, true) +
                attributeManager.getAttackCooldown());
    }

    @Override
    public void update() {
        targetCordX = gamePanel.player.worldX;
        targetCordY = gamePanel.player.worldY;

        hitBox.update();
        attributeManager.update();

        int complementValue = 1;

        double distance = Math.sqrt(Math.pow(worldX - targetCordX, 2) + Math.pow(worldY - targetCordY, 2));

        if (distance < gamePanel.tileSize * 5) {
            complementValue = -1;
            moving = true;
        }
        else if (distance < gamePanel.tileSize * 7) {
            moving = false;
        }
        else {
            moving = true;
        }

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
            move(targetCordX, targetCordY, false, complementValue, attributeManager.speed);
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

        int distance = 60;

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

        if (targetDistance < gamePanel.tileSize * 8 && weaponReloadTimer == 0 &&
                confusionTime <= 0) {
            weaponReloadTimer = attributeManager.attackCooldown;
            int number = Random.getRandomInt(3, false);
            AttributeModifier attributeModifier = new AttributeModifier();
            attributeModifier.setInfinity(false);
            attributeModifier.setPermanent(false);
            attributeModifier.setDuration(attributeManager.getAttackCooldown() - 5);
            if (number == 0) {
                attributeModifier.setSpeed(-2);
            }
            else if (number == 1) {
                attributeModifier.setInputDamageMultiple(0.1);
            }
            else if (number == 2) {
                attributeModifier.setAttackCooldown(10);
            }
            gamePanel.player.attributeModifiers.add(attributeModifier);

        }

        if (weaponReloadTimer > 0) {
            weaponReloadTimer--;
        }

        weapon.setAngle(angle);

        weapon.setScreenX((int) (screenX - distance * Math.cos(Math.toRadians(angle + 90))));
        weapon.setScreenY((int) (screenY - distance * Math.sin(Math.toRadians(angle + 90))));
    }

    public void getGoblinImage() {

        Animation animation = new Animation("goblinDown", 12, gamePanel);
        animation.addImage("goblinDown1");
        animation.addImage("goblinDown2");
        animationManager.addAnimation(animation);

        animationManager.setCurrentAnimation("goblinDown");

        weapon = new Weapon(gamePanel , gamePanel.imageManager.getImageByTag("staff"));
    }

}
