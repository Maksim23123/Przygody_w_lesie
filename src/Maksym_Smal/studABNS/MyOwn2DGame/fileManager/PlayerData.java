package Maksym_Smal.studABNS.MyOwn2DGame.fileManager;

import Maksym_Smal.studABNS.MyOwn2DGame.entity.AttributeManager;

import java.io.Serializable;

public class PlayerData implements Serializable {
    int worldX;
    int worldY;

    int roomIndexX;
    int roomIndexY;

    AttributeManager attributeManager;

    AttributeManager defaultAttributes;

    public void setDefaultAttributes(AttributeManager defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
    }

    public void setRoomIndexX(int roomIndexX) {
        this.roomIndexX = roomIndexX;
    }

    public void setRoomIndexY(int roomIndexY) {
        this.roomIndexY = roomIndexY;
    }

    public void setAttributeManager(AttributeManager attributeManager) {
        this.attributeManager = attributeManager;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public AttributeManager getDefaultAttributes() {
        return defaultAttributes;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getRoomIndexX() {
        return roomIndexX;
    }

    public int getRoomIndexY() {
        return roomIndexY;
    }

    public AttributeManager getAttributeManager() {
        return attributeManager;
    }
}
