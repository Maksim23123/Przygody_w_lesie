package Maksym_Smal.studABNS.MyOwn2DGame.world;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.io.IOException;
import java.util.ArrayList;

public class RoomHandler {

    GamePanel gamePanel;
    ArrayList<Room> rooms = new ArrayList<>();

    boolean[][] exploreMap = new boolean[10][10];

    int exploredRoomsCount = 1;

    public RoomHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        for (int i = 0; i < exploreMap.length; i++ ) {
            for (int j = 0; j < exploreMap.length; j++) {
                exploreMap[i][j] = false;
            }
        }
    }

    public boolean testExplored(int indexX, int indexY) {
        return exploreMap[indexX][indexY];
    }

    public void setExploreMap(boolean[][] exploreMap) {
        this.exploreMap = exploreMap;
    }

    public int getExploredRoomsCount() {
        exploredRoomsCount = 0;
        for (boolean[] i : exploreMap) {
            for (boolean j : i) {
                if (j) {
                    exploredRoomsCount++;
                }
            }
        }
        return exploredRoomsCount;
    }

    public boolean[][] getExploreMap() {
        return exploreMap;
    }

    public void setExplored(int indexX, int indexY, boolean value) {
        this.exploreMap[indexX][indexY] = value;
        gamePanel.visualOutputManager.getMiniMap().updateUnknownRooms();
    }

    public void loadEnemies() {
        gamePanel.enemyManager.generateEnemies(getExploredRoomsCount());
    }


    public void resetUses() {
        for(Room item : rooms) {
            item.setUsed(false);
        }
    }

    public void clearUnusable() {
        ArrayList<Room> used = new ArrayList<>();
        for(Room item : rooms) {
            if (item.getUsed()) {
                used.add(item);
            }
        }
        rooms = used;
    }

    public int[][] getRoom(int indexX, int indexY) throws IOException {
        Room room = new Room();
        boolean roomIsFound = false;
        for(Room item : rooms) {
            if (item.getRoomIndexX() == indexX && item.getRoomIndexY() == indexY){
                room = item;
                roomIsFound = true;
                item.setUsed(true);
            }
        }
        if (!roomIsFound) {
            room.setRoomMap(gamePanel.fileManager.loadOrGenerateRoom(indexX, indexY));
        }
        return room.getRoomMap();
    }
}
