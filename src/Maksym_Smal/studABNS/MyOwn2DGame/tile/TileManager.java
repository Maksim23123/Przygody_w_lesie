package Maksym_Smal.studABNS.MyOwn2DGame.tile;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.world.RoomGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNumber[][];
    RoomGenerator roomGenerator;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        roomGenerator = new RoomGenerator();

        tiles = new Tile[10];
        mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];


        getTileImages();
        mapTileNumber = new int[90][90];
        fillMap(0);
        roomGenerator.generate(0);
        generateShapeSlot(0, 1);
        generateShapeSlot(1, 1);
        generateShapeSlot(2, 1);
        //loadMap("/maps/world01.txt");
        for (int i = 0; i < mapTileNumber.length; i++) {
            for (int j = 0; j < mapTileNumber[0].length; j++){
                System.out.print(mapTileNumber[j][i] + " ");
            }
            System.out.println();
        }
    }

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



//colision diagnostic
//        for (int i = 0; i < 50; i++) {
//            for (int j = 0; j < 50; j++) {
//                if (tiles[mapTileNumber[j][i]].collision) {
//                    System.out.print("1 ");
//                }
//                else {
//                    System.out.print("0 ");
//                }
//            }
//            System.out.print("\n");
//        }
    }

    void generateShapeSlot(int slotIndexX, int slotIndexY) {
        int[][] tileMap = roomGenerator.getTileMap();
        for (int i = 0; i < tileMap[0].length; i++ ){
            for (int j = 0; j < tileMap.length; j ++) {
                mapTileNumber[30 * slotIndexX + j][30 * slotIndexY + i] = tileMap[j][i];
            }
        }
    }

    void fillMap(int tile) {
        for (int i = 0; i < mapTileNumber[0].length; i++) {
            for (int j = 0; j < mapTileNumber.length; j++) {
                mapTileNumber[j][i] = tile;
            }
        }

    }

    public void getTileImages() {

        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
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
                    worldY < gamePanel.player.worldY + gamePanel.player.screenY * 2 + gamePanel.tileSize ) {

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
