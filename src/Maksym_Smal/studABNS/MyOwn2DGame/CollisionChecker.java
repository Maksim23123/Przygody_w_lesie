package Maksym_Smal.studABNS.MyOwn2DGame;

import Maksym_Smal.studABNS.MyOwn2DGame.entity.Enemy;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Entity;
import Maksym_Smal.studABNS.MyOwn2DGame.tile.TileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.Projectile;

public class CollisionChecker {

    GamePanel gamePanel;

    CollisionChecker(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public boolean testStackInWall(Projectile projectile) {
        boolean result = true;
        int row = projectile.getWorldX() / gamePanel.tileSize;
        int col = projectile.getWorldY() / gamePanel.tileSize;
        TileManager tileManager = gamePanel.tileManager;

        if ((row >= 0 && col >= 0) && (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) &&
                tileManager.tiles[tileManager.mapTileNumber[col][row]].
                        projectileCollision) {

            result = false;
        }
        return result;
    }

    public boolean testMotionWithIgnoringEntities(Entity entity, int motionX, int motionY) {
        boolean result = true;
        TileManager tileManager = gamePanel.tileManager;

        if (motionX == 0 && motionY == 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = entity.hitBox.getEntityRightWorldX() / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].
                    collision || tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
                entity.setStackInWall(true);
            }

            int rightCol = entity.hitBox.getEntityRightCol();
            int leftCol = entity.hitBox.getEntityLeftCol();
            int nextVerticalRow = (entity.hitBox.getEntityTopWorldY()) / gamePanel.tileSize;
            if (nextVerticalRow >= 0 &&
                    (tileManager.tiles[tileManager.mapTileNumber[rightCol][nextVerticalRow]].collision ||
                            tileManager.tiles[tileManager.mapTileNumber[leftCol][nextVerticalRow]].collision)){
                result = false;
                entity.setStackInWall(true);
            }
        }

        if (motionX > 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = (entity.hitBox.getEntityRightWorldX() + motionX) / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].
                    collision || tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
            }
        }
        if (motionX < 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = (entity.hitBox.getEntityLeftWorldX() + motionX) / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].
                    collision || tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
            }
        }
        //-----
        if (motionY < 0) {
            int rightCol = entity.hitBox.getEntityRightCol();
            int leftCol = entity.hitBox.getEntityLeftCol();
            int nextVerticalRow = (entity.hitBox.getEntityTopWorldY() + motionY) / gamePanel.tileSize;
            if (nextVerticalRow >= 0 &&
                    (tileManager.tiles[tileManager.mapTileNumber[rightCol][nextVerticalRow]].collision ||
                            tileManager.tiles[tileManager.mapTileNumber[leftCol][nextVerticalRow]].collision)){
                result = false;
            }
        }

        if (motionY > 0) {
            int rightCol = entity.hitBox.getEntityRightCol();
            int leftCol = entity.hitBox.getEntityLeftCol();
            int nextVerticalRow = (entity.hitBox.getEntityBottomWorldY() + motionY) / gamePanel.tileSize;
            if (nextVerticalRow >= 0 &&
                    (tileManager.tiles[tileManager.mapTileNumber[rightCol][nextVerticalRow]].collision ||
                            tileManager.tiles[tileManager.mapTileNumber[leftCol][nextVerticalRow]].collision)){
                result = false;
            }
        }

        return result;
    }


    public boolean testMotion(Entity entity, int motionX, int motionY) {
        boolean result = true;
        TileManager tileManager = gamePanel.tileManager;

        if (motionX == 0 && motionY == 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = entity.hitBox.getEntityRightWorldX() / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].
                    collision || tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
                entity.setStackInWall(true);
            }

            int rightCol = entity.hitBox.getEntityRightCol();
            int leftCol = entity.hitBox.getEntityLeftCol();
            int nextVerticalRow = (entity.hitBox.getEntityTopWorldY()) / gamePanel.tileSize;
            if (nextVerticalRow >= 0 &&
                    (tileManager.tiles[tileManager.mapTileNumber[rightCol][nextVerticalRow]].collision ||
                            tileManager.tiles[tileManager.mapTileNumber[leftCol][nextVerticalRow]].collision)){
                result = false;
                entity.setStackInWall(true);
            }
        }

        if (motionX > 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = (entity.hitBox.getEntityRightWorldX() + motionX) / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].
                    collision || tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
            }
        }
        if (motionX < 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = (entity.hitBox.getEntityLeftWorldX() + motionX) / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].
                    collision || tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
            }
        }
        //-----
        if (motionY < 0) {
            int rightCol = entity.hitBox.getEntityRightCol();
            int leftCol = entity.hitBox.getEntityLeftCol();
            int nextVerticalRow = (entity.hitBox.getEntityTopWorldY() + motionY) / gamePanel.tileSize;
            if (nextVerticalRow >= 0 &&
                    (tileManager.tiles[tileManager.mapTileNumber[rightCol][nextVerticalRow]].collision ||
                    tileManager.tiles[tileManager.mapTileNumber[leftCol][nextVerticalRow]].collision)){
                result = false;
            }
        }

        if (motionY > 0) {
            int rightCol = entity.hitBox.getEntityRightCol();
            int leftCol = entity.hitBox.getEntityLeftCol();
            int nextVerticalRow = (entity.hitBox.getEntityBottomWorldY() + motionY) / gamePanel.tileSize;
            if (nextVerticalRow >= 0 &&
                    (tileManager.tiles[tileManager.mapTileNumber[rightCol][nextVerticalRow]].collision ||
                            tileManager.tiles[tileManager.mapTileNumber[leftCol][nextVerticalRow]].collision)){
                result = false;
            }
        }



        if (entity == gamePanel.player) {
            for (Enemy item : gamePanel.enemyManager.getEnemies()) {
                double distance = Math.sqrt(Math.pow((gamePanel.player.worldX + motionX) - item.worldX,2) +
                        Math.pow((gamePanel.player.worldY + motionY) - item.worldY,2));
                if (distance < 48) {
                    result = false;
                }
            }
        }
        else {
            int increase = 0;
            double distance = Math.sqrt(Math.pow((entity.worldX + motionX) - gamePanel.player.worldX,2) +
                    Math.pow((entity.worldY + motionY) - gamePanel.player.worldY,2));
            if (distance < 48) {
                result = false;
                entity.setPushedOut(true);
                if (distance == 0) {
                    increase = 1;
                }
                else {
                    increase = 0;
                }
                //Plus one so that if the distance is zero, it is possible to determine the direction of weaving
                entity.setPushPoint(gamePanel.player.worldX + increase, gamePanel.player.worldY + increase);
            }
            for (Enemy item : gamePanel.enemyManager.getEnemies()) {
                if (entity != item) {
                    distance = Math.sqrt(Math.pow((entity.worldX + motionX) - item.worldX,2) +
                            Math.pow((entity.worldY + motionY) - item.worldY,2));
                    if (distance < 48) {
                        result = false;
                        entity.setPushedOut(true);
                        if (distance == 0) {
                            increase = 1;
                        }
                        else {
                            increase = 0;
                        }
                        //Plus increase so that if the distance is zero, it is possible to determine the direction of weaving
                        entity.setPushPoint(item.worldX + increase, item.worldY + increase);
                    }
                }
            }
        }
        return result;
    }
}
