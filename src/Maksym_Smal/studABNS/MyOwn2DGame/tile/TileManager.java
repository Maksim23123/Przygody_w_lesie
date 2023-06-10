package Maksym_Smal.studABNS.MyOwn2DGame.tile;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNumber[][];

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];


        getTileImages();
        mapTileNumber = new int[90][90];
        fillMap(0, 4);

        mapTileNumber = new int[90][90];
    }

    public void closeRoom() {
        mapTileNumber[30][46] = 1;
        mapTileNumber[30][45] = 1;
        mapTileNumber[30][44] = 1;
        mapTileNumber[30][43] = 1;

        mapTileNumber[59][46] = 1;
        mapTileNumber[59][45] = 1;
        mapTileNumber[59][44] = 1;
        mapTileNumber[59][43] = 1;

        mapTileNumber[46][30] = 1;
        mapTileNumber[45][30] = 1;
        mapTileNumber[44][30] = 1;
        mapTileNumber[43][30] = 1;

        mapTileNumber[46][59] = 1;
        mapTileNumber[45][59] = 1;
        mapTileNumber[44][59] = 1;
        mapTileNumber[43][59] = 1;
    }

    public void openRoom() {
        mapTileNumber[30][46] = 0;
        mapTileNumber[30][45] = 0;
        mapTileNumber[30][44] = 0;
        mapTileNumber[30][43] = 0;

        mapTileNumber[59][46] = 0;
        mapTileNumber[59][45] = 0;
        mapTileNumber[59][44] = 0;
        mapTileNumber[59][43] = 0;

        mapTileNumber[46][30] = 0;
        mapTileNumber[45][30] = 0;
        mapTileNumber[44][30] = 0;
        mapTileNumber[43][30] = 0;

        mapTileNumber[46][59] = 0;
        mapTileNumber[45][59] = 0;
        mapTileNumber[44][59] = 0;
        mapTileNumber[43][59] = 0;
    }

    public void updateTileMap() throws IOException {
        gamePanel.roomHandler.resetUses();
        Player player = gamePanel.player;
        int[][] tileMap = gamePanel.roomHandler.getRoom(player.getRoomIndexX(), player.getRoomIndexY());
        for (int i = 0; i < tileMap.length; i++ ){
            for (int j = 0; j < tileMap.length; j ++) {
                mapTileNumber[30 + j][30 + i] = tileMap[j][i];
            }
        }

        if (!gamePanel.roomHandler.testExplored(player.getRoomIndexX(), player.getRoomIndexY())) {
            gamePanel.roomHandler.loadEnemies();
            gamePanel.roomHandler.setExplored(player.getRoomIndexX(), player.getRoomIndexY(),true);
        }


        if (player.getRoomIndexX() > 0) {
            tileMap = gamePanel.roomHandler.getRoom(player.getRoomIndexX() - 1, player.getRoomIndexY());
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[j][30 + i] = tileMap[j][i];
                }
            }
        }
        else {
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[j][30 + i] = 4;
                }
            }
        }

        if (player.getRoomIndexX() < gamePanel.fileManager.getMazeMap()[0].length - 1) {
            tileMap = gamePanel.roomHandler.getRoom(player.getRoomIndexX() + 1, player.getRoomIndexY());
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[30 * 2 + j][30 + i] = tileMap[j][i];
                }
            }
        }
        else {
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[30 * 2 + j][30 + i] = 4;
                }
            }
        }

        if (player.getRoomIndexY() > 0) {
            tileMap = gamePanel.roomHandler.getRoom(player.getRoomIndexX(), player.getRoomIndexY() - 1);
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[30 + j][i] = tileMap[j][i];
                }
            }
        }
        else {
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[30 + j][i] = 4;
                }
            }
        }

        if (player.getRoomIndexY() < gamePanel.fileManager.getMazeMap()[0].length - 1) {
            tileMap = gamePanel.roomHandler.getRoom(player.getRoomIndexX(), player.getRoomIndexY() + 1);
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[30 + j][30 * 2 + i] = tileMap[j][i];
                }
            }
        }
        else {
            for (int i = 0; i < tileMap.length; i++ ){
                for (int j = 0; j < tileMap.length; j ++) {
                    mapTileNumber[30 + j][30 * 2 + i] = 4;
                }
            }
        }
        gamePanel.roomHandler.clearUnusable();
    }

    //Will reuse it

    public void loadMap(String filePath) {

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {

                String line = bufferedReader.readLine();

                while (col < gamePanel.maxWorldCol) {

                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    void fillMap(int tile, int tile1) {
        for (int i = 0; i < mapTileNumber[0].length; i++) {
            for (int j = 0; j < mapTileNumber.length; j++) {
                mapTileNumber[j][i] = tile;
            }
        }

        for (int i = 0; i < 30; i++ ){
            for (int j = 0; j < 30; j ++) {
                mapTileNumber[j][i] = tile1;
            }
        }

        for (int i = 0; i < 30; i++ ){
            for (int j = 0; j < 30; j ++) {
                mapTileNumber[j][30 * 2 + i] = tile1;
            }
        }

        for (int i = 0; i < 30; i++ ){
            for (int j = 0; j < 30; j ++) {
                mapTileNumber[30 * 2 + j][i] = tile1;
            }
        }

        for (int i = 0; i < 30; i++ ){
            for (int j = 0; j < 30; j ++) {
                mapTileNumber[30 * 2 + j][30 * 2 + i] = tile1;
            }
        }

    }

    public void getTileImages() {

        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/tiles/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/tiles/wall.png")));
            tiles[1].collision = true;
            tiles[1].projectileCollision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/tiles/water.png")));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/tiles/earth.png")));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/tiles/tree.png")));
            tiles[4].collision = true;
            tiles[4].projectileCollision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/tiles/sand.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX > gamePanel.player.worldX - gamePanel.player.screenX * 2 - gamePanel.tileSize &&
                    worldX < gamePanel.player.worldX + gamePanel.player.screenX * 2 + gamePanel.tileSize &&
                    worldY > gamePanel.player.worldY - gamePanel.player.screenY * 2 - gamePanel.tileSize &&
                    worldY < gamePanel.player.worldY + gamePanel.player.screenY * 2 + gamePanel.tileSize * 7) {

                g2.drawImage(tiles[tileNum].image, screenX, screenY,
                        gamePanel.tileSize, gamePanel.tileSize, null);

            }

            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }
        }

    }

}
