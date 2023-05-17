package Maksym_Smal.studABNS.MyOwn2DGame.world;

import java.awt.*;

public class RoomGenerator {

    int[][] tileMap = new int[30][30];

    public RoomGenerator(){
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++){
                tileMap[i][j] = 0;
            }
        }
    }

    public int[][] generate(int shapeType){
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++){
                tileMap[i][j] = 0;
            }
        }
        if (shapeType == 0) {
            generateSimpleHorizontalLine(0, 2, tileMap.length / 2 + 2, 1);
            generateSimpleHorizontalLine(0, 2, tileMap.length / 2 - 3, 1);
            generateSimpleVerticalLine(tileMap[0].length / 2 + 2, tileMap[0].length - 3,
                    2, 1);
            generateSimpleVerticalLine(tileMap[0].length / 2 - 3, 2, 2, 1);
            generateSimpleHorizontalLine(2, tileMap.length - 3, tileMap.length - 3, 1);
            generateSimpleHorizontalLine(2, tileMap.length - 3,   2, 1);
            generateSimpleVerticalLine(tileMap[0].length / 2 + 2, tileMap[0].length - 3,
                    tileMap.length - 3, 1);
            generateSimpleVerticalLine(tileMap[0].length / 2 - 3, 2, tileMap.length - 3, 1);
            generateSimpleHorizontalLine(tileMap.length - 1, tileMap.length - 3,
                    tileMap.length / 2 + 2, 1);
            generateSimpleHorizontalLine(tileMap.length - 1, tileMap.length - 3,
                    tileMap.length / 2 - 3, 1);
            fill(0,0, 4);
            fill(0,tileMap.length - 1, 4);
        }


        tileMap[tileMap.length - 1][tileMap.length / 2 + 2] = 4;
        tileMap[tileMap.length - 1][tileMap.length / 2 - 3] = 4;

//        for (int i = 0; i < tileMap[0].length; i++) {
//            for (int j = 0; j < tileMap.length; j++){
//                if ( tileMap[j][i] >= 0 && tileMap[j][i] < 10){
//                    System.out.print(" ");
//                }
//                System.out.print(tileMap[j][i] + " ");
//            }
//            System.out.println();
//        }
        return tileMap;

    }

    void generateSimpleHorizontalLine(int cordX1, int cordX2, int cordY, int dispersion) {
        tileMap[cordX1][cordY] = 4;
        tileMap[cordX2][cordY] = 4;

        double randomizer;

        int futureStepsCount = 0;
        int currentCordX = 0;
        int currentDispersion = 0;
        int heightCordX = 0;


        if ( cordX1 > cordX2) {
            futureStepsCount = cordX1 - cordX2;
            heightCordX = cordX1;

            currentCordX = cordX2 + 1;
        }

        if ( cordX1 < cordX2) {
            futureStepsCount = cordX2 - cordX1;
            heightCordX = cordX2;

            currentCordX = cordX1 + 1;
        }

        for (int i = 0; i < futureStepsCount - 1; i++){
            randomizer = Math.random();
            if (heightCordX - currentCordX < Math.abs(currentDispersion)){
                if (currentDispersion > 0) {
                    currentDispersion--;
                }
                else if (currentDispersion < 0) {
                    currentDispersion++;
                }
            }
            else if (randomizer > 0.5) {
                randomizer = Math.random();
                if ((randomizer > 0.5 || currentDispersion - 1 < Math.abs(dispersion) * -1) &&
                        !(currentDispersion + 1 > Math.abs(dispersion))) {
                    currentDispersion += 1;
                }
                else if (randomizer <= 0.5 || currentDispersion + 1 > Math.abs(dispersion)) {
                    currentDispersion -= 1;
                }
            }
            
            tileMap[currentCordX][cordY + currentDispersion] = 4;

            currentCordX++;
        }
    }

    void generateSimpleVerticalLine(int cordY1, int cordY2, int cordX, int dispersion) {
        tileMap[cordX][cordY1] = 4;
        tileMap[cordX][cordY2] = 4;

        double randomizer;

        int futureStepsCount = 0;
        int currentCordY = 0;
        int currentDispersion = 0;
        int heightCordY = 0;


        if ( cordY1 > cordY2) {
            futureStepsCount = cordY1 - cordY2;
            heightCordY = cordY1;

            currentCordY = cordY2 + 1;
        }

        if ( cordY1 < cordY2) {
            futureStepsCount = cordY2 - cordY1;
            heightCordY = cordY2;

            currentCordY = cordY1 + 1;
        }

        for (int i = 0; i < futureStepsCount - 1; i++){
            randomizer = Math.random();
            if (heightCordY - currentCordY < Math.abs(currentDispersion)){
                if (currentDispersion > 0) {
                    currentDispersion--;
                }
                else if (currentDispersion < 0) {
                    currentDispersion++;
                }
            }
            else if (randomizer > 0.5) {
                randomizer = Math.random();
                if ((randomizer > 0.5 || currentDispersion - 1 < Math.abs(dispersion) * -1) &&
                        !(currentDispersion + 1 > Math.abs(dispersion))) {
                    currentDispersion += 1;
                }
                else if (randomizer <= 0.5 || currentDispersion + 1 > Math.abs(dispersion)) {
                    currentDispersion -= 1;
                }
            }

            tileMap[cordX + currentDispersion][currentCordY] = 4;

            currentCordY++;
        }
    }

    void fill(int cordX, int cordY, int fillBy) {
        int target = tileMap[cordX][cordY];
        int iterator = 0;
        tileMap[cordX][cordY] = -1;
        boolean uselessIteration = false;

        while (!uselessIteration && iterator < 1000) {
            iterator++;
            uselessIteration = true;
            for (int i = 0; i < tileMap[0].length; i++) {
                for (int j = 0; j < tileMap.length; j++) {
                    if (tileMap[j][i] == -1 && i + 1 < tileMap[0].length && tileMap[j][i + 1] == target){
                        tileMap[j][i + 1] = -1;
                        uselessIteration = false;
                    }
                    if (tileMap[j][i] == -1 && i - 1 >= 0 && tileMap[j][i - 1] == target){
                        tileMap[j][i - 1] = -1;
                        uselessIteration = false;
                    }
                    if (tileMap[j][i] == -1 && j + 1 < tileMap[0].length && tileMap[j + 1][i] == target){
                        tileMap[j + 1][i] = -1;
                        uselessIteration = false;
                    }
                    if (tileMap[j][i] == -1 && j - 1 >= 0 && tileMap[j - 1][i] == target){
                        tileMap[j - 1][i] = -1;
                        uselessIteration = false;
                    }
                }
            }
        }
        for (int i = 0; i < tileMap[0].length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                if ( tileMap[j][i] == -1) {
                    tileMap[j][i] = fillBy;
                }
            }
        }
    }

    public int[][] getTileMap() {
        return tileMap;
    }
}
