package Maksym_Smal.studABNS.MyOwn2DGame;

import Maksym_Smal.studABNS.MyOwn2DGame.entity.Entity;
import Maksym_Smal.studABNS.MyOwn2DGame.tile.TileManager;

public class CollisionChecker {

    GamePanel gamePanel;

    CollisionChecker(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public boolean testMotion(Entity entity, int motionX, int motionY) {
        boolean result = true;
        TileManager tileManager = gamePanel.tileManager;

        if (motionX > 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = (entity.hitBox.getEntityRightWorldX() + motionX) / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].collision ||
                    tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
                result = false;
            }
        }
        if (motionX < 0) {
            int topRow = entity.hitBox.getEntityTopRow();
            int bottomRow = entity.hitBox.getEntityBottomRow();
            int nextHorizontalRow = (entity.hitBox.getEntityLeftWorldX() + motionX) / gamePanel.tileSize;
            if (nextHorizontalRow >= 0 && (tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][topRow]].collision ||
                    tileManager.tiles[tileManager.mapTileNumber[nextHorizontalRow][bottomRow]].collision)){
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
}
