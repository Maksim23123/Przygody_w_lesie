package Maksym_Smal.studABNS.MyOwn2DGame.world;

import java.util.ArrayList;

public class MazeGenerator {
    int map[][];
    int shapesCount;


    public MazeGenerator(int sizeX, int sizeY) {
        map = new int[sizeX][sizeY];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = -1;
            }
        }
    }

    String output = "";

    public int[][] generate(int iterations) {
        map[(int)(map.length / 2)][(int)(map[0].length / 2)] = 10;
        shapesCount = 0;

        int[] openBottomShapes = {0, 4, 7, 8, 9, 10, 13, 14};
        int[] openTopShapes = {0, 3, 6, 8, 9, 10, 11, 12};
        int[] openRightShapes = {1, 5, 6, 7, 9, 10, 12, 14};
        int[] openLeftShapes = {1, 2, 6, 7, 8, 10, 11, 13};

        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if ((map[i][j] == 0 || map[i][j] == 3 || map[i][j] == 6 || map[i][j] == 8 ||
                            map[i][j] == 9 || map[i][j] == 10 || map[i][j] == 11 || map[i][j] == 12)
                            && (j - 1) >= 0) {
                        if (map[i][j - 1] == -1) {
                            map[i][j - 1] = openBottomShapes[getRandomState(openBottomShapes.length)];
                            shapesCount++;
                        }
                    }
                    if ((map[i][j] == 0 || map[i][j] == 4 || map[i][j] == 7 || map[i][j] == 8 ||
                            map[i][j] == 9 || map[i][j] == 10 || map[i][j] == 13 || map[i][j] == 14)
                            && (j + 1) < map[0].length) {
                        if (map[i][j + 1] == -1) {
                            map[i][j + 1] = openTopShapes[getRandomState(openTopShapes.length)];
                            shapesCount++;
                        }
                    }
                    if ((map[i][j] == 1 || map[i][j] == 2 || map[i][j] == 6 || map[i][j] == 7 ||
                            map[i][j] == 8 || map[i][j] == 10 || map[i][j] == 11 || map[i][j] == 13)
                            && (i - 1) >= 0) {
                        if (map[i - 1][j] == -1) {
                            map[i - 1][j] = openRightShapes[getRandomState(openRightShapes.length)];
                            shapesCount++;
                        }
                    }
                    if ((map[i][j] == 1 || map[i][j] == 5 || map[i][j] == 6 || map[i][j] == 7 ||
                            map[i][j] == 9 || map[i][j] == 10 || map[i][j] == 12 || map[i][j] == 14)
                            && (i + 1) < map.length) {
                        if (map[i + 1][j] == -1) {
                            map[i + 1][j] = openLeftShapes[getRandomState(openLeftShapes.length)];
                            shapesCount++;
                        }
                    }

                }
            }
        }

        return map;
    }

    public int getRandomState(int divider){
        return (int)((Math.random() * 100) / (100d / (double)divider + 0.001d));
    }

    public String getOutput(){
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] == 0) {
                    output += " | ";
                }
                else if (map[j][i] == 1) {
                    output += "---";
                }
                else if (map[j][i] == 2) {
                    output += "-- ";
                }
                else if (map[j][i] == 3) {
                    output += " ' ";
                }
                else if (map[j][i] == 4) {
                    output += " , ";
                }
                else if (map[j][i] == 5) {
                    output += " --";
                }
                else if (map[j][i] == 6) {
                    output += "-'-";
                }
                else if (map[j][i] == 7) {
                    output += "-,-";
                }
                else if (map[j][i] == 8) {
                    output += "-| ";
                }
                else if (map[j][i] == 9) {
                    output += " |-";
                }
                else if (map[j][i] == 10) {
                    output += "-|-";
                }
                else if (map[j][i] == 11) {
                    output += "-' ";
                }
                else if (map[j][i] == 12) {
                    output += " '-";
                }
                else if (map[j][i] == 13) {
                    output += "-, ";
                }
                else if (map[j][i] == 14) {
                    output += " ,-";
                }
                else {
                    output += "   ";
                }
            }
            output += "\n";
        }
        return output + "ShapesCount: " + shapesCount;
    }

}
