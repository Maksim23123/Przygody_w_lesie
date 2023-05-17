package Maksym_Smal.studABNS.MyOwn2DGame.fileManager;

import Maksym_Smal.studABNS.MyOwn2DGame.world.MazeGenerator;

import java.io.*;


public class FileManager {
    File saveFolder;

    String filePath;

    int[][] mazeMap;




    public FileManager(String path) {
        filePath = path;
        saveFolder = new File(filePath);
        saveFolder.mkdirs();

//        MazeGenerator mazeGenerator = new MazeGenerator(9, 10);
//
        mazeMap = new int[10][10];
//
//        makeSave();

        loadSave();

    }

    void loadSave() {

        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[0].length; j++) {
                mazeMap[i][j] = 0;
                System.out.print(mazeMap[i][j] + " ");
            }
            System.out.println("");
        }

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(filePath + "/mazeMap.bin"))) {
            // Зчитування даних з бінарного файлу
            for (int i = 0; i < mazeMap[0].length; i++) {
                for (int j = 0; j < mazeMap.length; j++) {
                    mazeMap[j][i] = inputStream.readInt();
                }
            }

        } catch (IOException e) {
            System.out.println("Сталася помилка при зчитуванні з бінарного файлу: " + e.getMessage());
        }

        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[0].length; j++) {
                System.out.print(mazeMap[i][j] + " ");
            }
            System.out.println("");
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


}
