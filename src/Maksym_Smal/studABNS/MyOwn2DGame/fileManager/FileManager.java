package Maksym_Smal.studABNS.MyOwn2DGame.fileManager;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.world.MazeGenerator;
import Maksym_Smal.studABNS.MyOwn2DGame.world.RoomGenerator;

import javax.swing.*;
import java.io.*;


public class FileManager {
    File saveFolder;

    String filePath;

    int[][] mazeMap = new int[10][10];;

    boolean[][] exploreMap = new boolean[10][10];;

    GamePanel gamePanel;

    PlayerData playerData;


    public FileManager(String path, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        filePath = path;
        saveFolder = new File(filePath);
        saveFolder.mkdirs();

//        MazeGenerator mazeGenerator = new MazeGenerator(9, 10);
//
//
//        makeSave();
    }

    public boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public int[][] getMazeMap() {
        return mazeMap;
    }

    public boolean[][] getExploreMap() {
        return exploreMap;
    }

    public boolean loadSave() {
        boolean result = true;

        if (checkSaveExist()) {
            try (DataInputStream inputStream = new DataInputStream(
                    new FileInputStream(filePath + "/mazeMap.bin"))) {

                for (int i = 0; i < mazeMap[0].length; i++) {
                    for (int j = 0; j < mazeMap.length; j++) {
                        mazeMap[i][j] = inputStream.readInt();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try (DataInputStream inputStream = new DataInputStream(
                    new FileInputStream(filePath + "/exploreMap.bin"))) {
                for (int i = 0; i < exploreMap[0].length; i++) {
                    for (int j = 0; j < exploreMap.length; j++) {
                        exploreMap[i][j] = inputStream.readBoolean();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            result = false;
        }


//        MazeGenerator mazeGenerator = new MazeGenerator(10, 10);
//
//        mazeGenerator.setMap(mazeMap);
//
//        System.out.println(mazeGenerator.getOutput());
        return result;
    }

    public void writeExploreMap(boolean[][] exploreMap) {
        String path = filePath + "/exploreMap.bin";
        try (DataOutputStream outputStream = new DataOutputStream(
                new FileOutputStream(path))) {
            for (boolean[] row : exploreMap) {
                for (boolean item : row) {
                    if (item) {
                        outputStream.writeBoolean(true);
                    }
                    else {
                        outputStream.writeBoolean(false);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] loadOrGenerateRoom(int indexX, int indexY) throws IOException {
        String filePath = this.filePath + "/rooms/room" + indexX + "_" + indexY + ".bin";

        File file = new File(this.filePath + "/rooms/");
        file.mkdirs();

        int[][] room = new int[30][30];
        if (indexX >= 0 && indexY >= 0 && indexX < mazeMap.length && indexY < mazeMap[0].length) {
            file = new File(filePath);
            if (file.exists()) {
                try (DataInputStream inputStream = new DataInputStream(new FileInputStream(filePath))) {
                    for (int i = 0; i < room.length; i++) {
                        for (int j = 0; j < room.length; j++) {
                            room[i][j] = inputStream.readInt();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                file.createNewFile();
                RoomGenerator roomGenerator = new RoomGenerator();
                room = roomGenerator.generate(mazeMap[indexX][indexY]);
                try (DataOutputStream outputStream = new DataOutputStream(
                        new FileOutputStream(filePath))) {
                    for (int[] row : room) {
                        for (int item : row) {
                            outputStream.writeInt(item);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return room;
    }

    public void writePlayerData(PlayerData playerData) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath + "/playerData.bin");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(playerData);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSaveExist() {
        boolean result = true;
        if (!gamePanel.fileManager.isExist(filePath + "/mazeMap.bin")) {
            result = false;
        }

        if (!gamePanel.fileManager.isExist(filePath + "/playerData.bin")) {
            result = false;
        }

        if (!gamePanel.fileManager.isExist(filePath + "/exploreMap.bin")) {
            result = false;
        }

        return result;
    }

    public PlayerData loadPlayerData() {
        if (playerData != null) {
            return playerData;
        }
        else {
            try {
                FileInputStream fileIn = new FileInputStream(filePath + "/playerData.bin");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                playerData = (PlayerData)objectIn.readObject();
                objectIn.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return playerData;
        }
    }

    void makeSave() {

        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[0].length; j++) {
                System.out.print(mazeMap[i][j] + " ");
            }
            System.out.println("");
        }

        try (DataOutputStream outputStream = new DataOutputStream(
                new FileOutputStream(filePath + "/mazeMap.bin"))) {
            // Запис масиву у бінарний файл
            for (int[] row : mazeMap) {
                for (int item : row) {
                    outputStream.writeInt(item);
                }
            }

            System.out.println("Масив успішно записано у бінарний файл.");
        } catch (IOException e) {
            System.out.println("Сталася помилка при записі масиву у бінарний файл: " + e.getMessage());
        }

    }

    public void generateNewSave() throws IOException {
        File file = new File(filePath);
        file.mkdirs();

        MazeGenerator mazeGenerator = new MazeGenerator(10, 10);
        file = new File(filePath + "/mazeMap.bin");
        if (!file.exists()) {
            file.createNewFile();
        }

        mazeMap = mazeGenerator.generate(10);
        try (DataOutputStream outputStream = new DataOutputStream(
                new FileOutputStream(filePath + "/mazeMap.bin"))) {
            // Запис масиву у бінарний файл
            for (int[] row : mazeMap) {
                for (int item : row) {
                    outputStream.writeInt(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }

        folder.delete();
    }

    public String getFilePath() {
        return filePath;
    }
}
