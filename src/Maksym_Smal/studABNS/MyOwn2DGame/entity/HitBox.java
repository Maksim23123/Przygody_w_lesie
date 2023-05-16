package Maksym_Smal.studABNS.MyOwn2DGame.entity;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

public class HitBox {

    Entity entity;
    GamePanel gamePanel;

    int entityLeftWorldX;
    int entityRightWorldX;
    int entityTopWorldY;
    int entityBottomWorldY;

    int entityLeftCol;
    int entityRightCol;
    int entityTopRow;
    int entityBottomRow;

    HitBox(GamePanel gamePanel, Entity entity) {
        this.gamePanel = gamePanel;
        this.entity = entity;

        entityLeftWorldX = entity.worldX + entity.solidArea.x;
        entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        entityTopWorldY = entity.worldY + entity.solidArea.y;
        entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        entityRightCol = entityRightWorldX / gamePanel.tileSize;
        entityTopRow = entityTopWorldY / gamePanel.tileSize;
        entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
    }

    public void update(){
        entityLeftWorldX = entity.worldX + entity.solidArea.x;
        entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        entityTopWorldY = entity.worldY + entity.solidArea.y;
        entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        entityRightCol = entityRightWorldX / gamePanel.tileSize;
        entityTopRow = entityTopWorldY / gamePanel.tileSize;
        entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
    }

    public int getEntityLeftWorldX() {
        return entityLeftWorldX;
    }

    public int getEntityRightWorldX() {
        return entityRightWorldX;
    }

    public int getEntityBottomWorldY() {
        return entityBottomWorldY;
    }

    public int getEntityTopWorldY() {
        return entityTopWorldY;
    }

    public int getEntityBottomRow() {
        return entityBottomRow;
    }

    public int getEntityTopRow() {
        return entityTopRow;
    }

    public int getEntityRightCol() {
        return entityRightCol;
    }

    public int getEntityLeftCol() {
        return entityLeftCol;
    }
}
