package Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MiniMap {

    GamePanel gamePanel;

    BufferedImage[][] minimap = new BufferedImage[10][10];
    BufferedImage[][] unknownMap = new BufferedImage[10][10];

    MiniMap(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

    }

    public void update() {
        minimap = new BufferedImage[10][10];



        for (int i = 0; i < minimap.length; i++) {
            for (int j = 0; j < minimap.length; j++) {
                minimap[i][j] = unknownMap[i][j];

                if (gamePanel.roomHandler.testExplored(i, j)) {

                    if (i == gamePanel.player.getRoomIndexX() && j == gamePanel.player.getRoomIndexY()) {
                        minimap[i][j] = gamePanel.imageManager.getImageByTag("activeType" +
                                gamePanel.fileManager.getMazeMap()[i][j]);
                    }
                    else if (gamePanel.fileManager.getMazeMap()[i][j] >= 0) {
                        minimap[i][j] = gamePanel.imageManager.getImageByTag("type" +
                                gamePanel.fileManager.getMazeMap()[i][j]);
                    }
                }
            }
        }
    }

    public void updateUnknownRooms() {
        unknownMap = new BufferedImage[10][10];

        int[] openBottomShapes = {0, 4, 7, 8, 9, 10, 13, 14};
        int[] openTopShapes = {0, 3, 6, 8, 9, 10, 11, 12};
        int[] openRightShapes = {1, 5, 6, 7, 9, 10, 12, 14};
        int[] openLeftShapes = {1, 2, 6, 7, 8, 10, 11, 13};

        for (int i = 0; i < minimap.length; i++) {
            for (int j = 0; j < minimap.length; j++) {
                if (gamePanel.roomHandler.testExplored(i, j)) {
                    for (int openRightItem : openRightShapes) {
                        for (int openLeftItem : openLeftShapes) {
                            if (i > 0 && openLeftItem == gamePanel.fileManager.getMazeMap()[i][j] &&
                                    !gamePanel.roomHandler.testExplored(i - 1, j) &&
                                    openRightItem == gamePanel.fileManager.getMazeMap()[i - 1][j] ) {
                                unknownMap[i - 1][j] = gamePanel.imageManager.getImageByTag("unknownType5");
                            }
                        }
                    }

                    for (int openRightItem : openRightShapes) {
                        for (int openLeftItem : openLeftShapes) {
                            if (i < minimap.length - 1 && openRightItem == gamePanel.fileManager.getMazeMap()[i][j] &&
                                    !gamePanel.roomHandler.testExplored(i + 1, j) &&
                                    openLeftItem == gamePanel.fileManager.getMazeMap()[i + 1][j] ) {
                                unknownMap[i + 1][j] = gamePanel.imageManager.getImageByTag("unknownType2");
                            }
                        }
                    }

                    for (int openTopItem : openTopShapes) {
                        for (int openBottomItem : openBottomShapes) {
                            if (j > 0 && openTopItem == gamePanel.fileManager.getMazeMap()[i][j] &&
                                    !gamePanel.roomHandler.testExplored(i, j - 1) &&
                                    openBottomItem == gamePanel.fileManager.getMazeMap()[i][j - 1] ) {
                                unknownMap[i][j - 1] = gamePanel.imageManager.getImageByTag("unknownType4");
                            }
                        }
                    }

                    for (int openTopItem : openTopShapes) {
                        for (int openBottomItem : openBottomShapes) {
                            if (j < minimap.length - 1 && openBottomItem == gamePanel.fileManager.getMazeMap()[i][j] &&
                                    !gamePanel.roomHandler.testExplored(i, j + 1) &&
                                    openTopItem == gamePanel.fileManager.getMazeMap()[i][j + 1] ) {
                                unknownMap[i][j + 1] = gamePanel.imageManager.getImageByTag("unknownType3");
                            }
                        }
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(gamePanel.imageManager.getImageByTag("healthBBg"), gamePanel.getWidth() - gamePanel.tileSize * 3,
                0,gamePanel.tileSize * 3, gamePanel.tileSize * 3, null);

        g2.drawImage(gamePanel.imageManager.getImageByTag("healthSBg"), gamePanel.getWidth() -
                        gamePanel.tileSize * 3 + 10, 10,gamePanel.tileSize * 3 - 20,
                gamePanel.tileSize * 3 - 20, null);

        update();

        for (int i = 0; i < minimap.length; i++) {
            for (int j = 0; j < minimap.length; j++) {
                if (minimap[i][j] != null) {
                    g2.drawImage(minimap[i][j], gamePanel.getWidth() -
                                    gamePanel.tileSize * 3 + 10 + (((gamePanel.tileSize * 3 - 20) / 10) * i),
                            10 + (((gamePanel.tileSize * 3 - 20) / 10) * j),(gamePanel.tileSize * 3 - 20) / 10,
                            (gamePanel.tileSize * 3 - 20) / 10, null);
                }
            }
        }
    }
}
