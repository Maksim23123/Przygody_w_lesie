package Maksym_Smal.studABNS.MyOwn2DGame.fileManager;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.world.MazeGenerator;
import Maksym_Smal.studABNS.MyOwn2DGame.world.RoomGenerator;

import java.io.*;


public class FileManager {
    File saveFolder;

    String filePath;

    int[][] mazeMap;

    GamePanel gamePanel;


    public FileManager(String path, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        filePath = path;
        saveFolder = new File(filePath);
        saveFolder.mkdirs();

//        MazeGenerator mazeGenerator = new MazeGenerator(9, 10);
//
        mazeMap = new int[10][10];
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

    public void loadSave() {

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(filePath + "/mazeMap.bin"))) {
            // Зчитування даних з бінарного файлу
            for (int i = 0; i < mazeMap[0].length; i++) {
                for (int j = 0; j < mazeMap.length; j++) {
                    mazeMap[i][j] = inputStream.readInt();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        MazeGenerator mazeGenerator = new MazeGenerator(10, 10);
//
//        mazeGenerator.setMap(mazeMap);
//
//        System.out.println(mazeGenerator.getOutput());

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
        MazeGenerator mazeGenerator = new MazeGenerator(10, 10);
        File file = new File(filePath + "/mazeMap.bin");
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
}
