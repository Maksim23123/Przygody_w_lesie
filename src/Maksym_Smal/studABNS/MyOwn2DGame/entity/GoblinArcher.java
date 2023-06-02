package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.Random;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.Weapon;

import java.awt.*;

public class GoblinArcher extends Enemy {

    public Weapon weapon;
    int weaponReloadTimer = 0;

    int confusionTime;

    public GoblinArcher(GamePanel gamePanel) {
        super(gamePanel);

        confusionTime = 30;

        setDefaultValues();
    }

    @Override
    public void setDefaultValues() {
        solidArea = new Rectangle(11, 22, 42, 42);
        hitBox = new HitBox(gamePanel, this);
        setPosition(45  * gamePanel.tileSize, 45  * gamePanel.tileSize);
        direction = "down";
        weapon = new Weapon(gamePanel , gamePanel.imageManager.getImageByTag("bow"));
        attributeManager.setRandomAttributes(1);

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

        if (distance < gamePanel.tileSize * 7) {
            complementValue = -1;
            moving = true;
        }
        else if (distance < gamePanel.tileSize * 9) {
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

        g2.drawImage(gamePanel.imageManager.getImageByTag("regularGoblinUp1"), screenX, screenY,
                gamePanel.tileSize, gamePanel.tileSize, null);

        weapon.draw(g2);

        g2.drawImage(gamePanel.imageManager.getImageByTag("healthBBg"), screenX + gamePanel.tileSize / 6,
                screenY - gamePanel.tileSize / 3,
                gamePanel.tileSize / 6 * 4, gamePanel.tileSize / 6, null);
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthSBg"), screenX + gamePanel.tileSize / 6 + 2,
                screenY - gamePanel.tileSize / 3 + 2,
                gamePanel.tileSize / 6 * 4 - 4, gamePanel.tileSize / 6 - 4, null);
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthbar"), screenX + gamePanel.tileSize / 6 + 2,
                screenY - gamePanel.tileSize / 3 + 2,
                (gamePanel.tileSize / 6 * 4 - 4) * attributeManager.getHealth() / attributeManager.getMaxHealth(),
                gamePanel.tileSize / 6 - 4, null);
    }

    private void updateWeapon() {

        int distance = 20;

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

        if (targetDistance < gamePanel.tileSize * 10 && weaponReloadTimer == 0 &&
                confusionTime <= 0) {
            weaponReloadTimer = attributeManager.attackCooldown;
            gamePanel.projectileManager.createProjectile(worldX, worldY, targetCordX, targetCordY, 15,
                    gamePanel.imageManager.getImageByTag("arrow"), this);
        }

        if (weaponReloadTimer > 0) {
            weaponReloadTimer--;
        }

        weapon.setAngle(angle);

        weapon.setScreenX((int) (screenX - distance * Math.cos(Math.toRadians(angle + 90))));
        weapon.setScreenY((int) (screenY - distance * Math.sin(Math.toRadians(angle + 90))));
    }

}
