package Maksym_Smal.studABNS.MyOwn2DGame.world;

public class Room {

    private int roomIndexX;
    private int roomIndexY;

    private boolean isUsed = false;
    private int roomMap[][] = new int[30][30];

    public boolean getUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void setRoomIndexX(int roomIndexX) {
        this.roomIndexX = roomIndexX;
    }

    public int getRoomIndexX() {
        return roomIndexX;
    }

    public void setGetRoomIndexY(int getRoomIndexY) {
        this.roomIndexY = getRoomIndexY;
    }

    public int getRoomIndexY() {
        return roomIndexY;
    }

    public void setRoomMap(int[][] roomMap) {
        this.roomMap = roomMap;
    }

    public int[][] getRoomMap() {
        return roomMap;
    }
}
