package Maksym_Smal.studABNS.MyOwn2DGame;

import Maksym_Smal.studABNS.MyOwn2DGame.control.KeyHandler;
import Maksym_Smal.studABNS.MyOwn2DGame.control.MouseHandler;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.EnemyManager;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Player;
import Maksym_Smal.studABNS.MyOwn2DGame.fileManager.FileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.graphics.ImageManager;
import Maksym_Smal.studABNS.MyOwn2DGame.items.ItemManager;
import Maksym_Smal.studABNS.MyOwn2DGame.menu.*;
import Maksym_Smal.studABNS.MyOwn2DGame.menu.Menu;
import Maksym_Smal.studABNS.MyOwn2DGame.sound.SoundManager;
import Maksym_Smal.studABNS.MyOwn2DGame.tile.TileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.ProjectileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.VisualOutputManager;
import Maksym_Smal.studABNS.MyOwn2DGame.world.MazeGenerator;
import Maksym_Smal.studABNS.MyOwn2DGame.world.RoomHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {


    String gameState = "menu";
    final int originalTileSize = 32; // 16X16
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public int screenWidth = 1500;//16 * 3 * 30;
    public int screenHeight = 700;//16 * 3 * 15;

    public final int maxWorldCol = 90;
    public final int maxWorldRow = 90;

    private int menuNumber = 0;

    private JFrame window;


    //FPS
    int FPS = 60;


    public void setGameState(String gameState) {
        this.gameState = gameState;
    }


    //Instantiate some things
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    DeltaTime deltaTime = new DeltaTime(FPS);

    public ImageManager imageManager = new ImageManager();

    public Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public ArrayList<Menu> menus = new ArrayList<>();

    public MouseHandler mouseHandler = new MouseHandler();

    public FileManager fileManager;

    public EnemyManager enemyManager = new EnemyManager(this);

    public RoomHandler roomHandler = new RoomHandler(this);

    public VisualOutputManager visualOutputManager = new VisualOutputManager(this);

    public ProjectileManager projectileManager = new ProjectileManager(this);

    public ItemManager itemManager = new ItemManager(this);

    public SoundManager soundManager = new SoundManager();

    private int changeGameStateReload = 0;

    public GamePanel(JFrame window) {
        this.window = window;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        menus.add(new MainMenu(this));
        menus.add(new SavesMenu(this));
        menus.add(new GamePauseMenu(this));
        menus.add(new EndGameMenu(this));

        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.setFocusable(true);
        this.requestFocus();
    }

    public void updateTileMap() {
        try {
            tileManager.updateTileMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setMenuNumber(int menuNumber) {
        this.menuNumber = menuNumber;
    }

    @Override
    public void run() {

        deltaTime.startDeltaTime();


        while (gameThread != null) {
            if (deltaTime.getIntervalProgress() >= 1) {
                switch (gameState) {
                    case "menu":
                        menus.get(menuNumber).update();

                        repaint();

                        break;
                    case "gameLoop":

                        if (roomHandler.getExploredRoomsCount() >=
                                MazeGenerator.getShapesCount(fileManager.getMazeMap()) &&
                                enemyManager.getEnemies().isEmpty()) {
                            setGameState("menu");
                            EndGameMenu endGameMenu = (EndGameMenu) menus.get(3);

                            endGameMenu.setText("YOU WIN\n" +
                                    "Explored rooms count: " + roomHandler.getExploredRoomsCount() + "/" +
                                    MazeGenerator.getShapesCount(fileManager.getMazeMap()) + "\n" +
                                    "Improved statistic\n" +
                                    "Damage: " + player.attributeManager.getDamage() + "\n" +
                                    "Health: " + player.attributeManager.getHealth() + "/" +
                                    player.attributeManager.getMaxHealth()+ "\n" +
                                    "Input damage multiple: " +
                                    player.defaultAttributes.getInputDamageMultiple());
                            setMenuNumber(3);
                            FileManager.deleteFolder(new File(fileManager.getFilePath()));
                            soundManager.setPlayBackground(false);
                            enemyManager.setEnemies(new ArrayList<>());
                            itemManager.itemsOnTheFloor = new ArrayList<>();
                            projectileManager.setProjectiles(new ArrayList<>());
                        }
//
//                        System.out.println( "----\n" + player.attributeManager.getHealth());

//
//                        System.out.println("ExploredRoomsCount:" + roomHandler.getExploredRoomsCount());

                        // 1 UPDATE: update information such as character positions
                        update();

                        // 2 DRAW: draw the screen with the updated information
                        repaint();

                        break;
                }

                if (getChangeGameStateReload() > 0) {
                    changeGameStateReload--;
                }
            }
        }
    }

    public void update(){
        if (gameState.equals("gameLoop") && keyHandler.getPressedButtonsQueue().contains("Escape") &&
                !(getChangeGameStateReload() > 0)) {
            gameState = "menu";
            setMenuNumber(2);
            keyHandler.setPressedButtonsQueue(new ArrayList<>());
            soundManager.setPlayBackground(false);
        }

//        screenHeight = getHeight();
//        screenWidth = getWidth();
        enemyManager.update();
        player.update();
        projectileManager.update();
        itemManager.update();
        soundManager.update();

        if (!enemyManager.getEnemies().isEmpty()) {
            tileManager.closeRoom();
        }
        else {
            tileManager.openRoom();
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        switch (gameState) {
            case "menu":
                menus.get(menuNumber).draw(g2);
                break;
            case "gameLoop":
                tileManager.draw(g2);
                itemManager.draw(g2);
                enemyManager.draw(g2);
                player.draw(g2);
                projectileManager.draw(g2);
                player.weapon.draw(g2);
                visualOutputManager.draw(g2);
                break;
        }

        g2.dispose();
    }

    public String getGameState() {
        return gameState;
    }

    public int getMenuNumber() {
        return menuNumber;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public int getChangeGameStateReload() {
        return changeGameStateReload;
    }

    public void setChangeGameStateReload(int changeGameStateReload) {
        this.changeGameStateReload = changeGameStateReload;
    }
}