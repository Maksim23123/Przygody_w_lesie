package Maksym_Smal.studABNS.MyOwn2DGame;

import Maksym_Smal.studABNS.MyOwn2DGame.entity.EnemyManager;
import Maksym_Smal.studABNS.MyOwn2DGame.entity.Player;
import Maksym_Smal.studABNS.MyOwn2DGame.fileManager.FileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.menu.MainMenu;
import Maksym_Smal.studABNS.MyOwn2DGame.menu.Menu;
import Maksym_Smal.studABNS.MyOwn2DGame.menu.SavesMenu;
import Maksym_Smal.studABNS.MyOwn2DGame.tile.TileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.ProjectileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.visualAttributes.VisualOutputManager;
import Maksym_Smal.studABNS.MyOwn2DGame.world.RoomHandler;

import javax.swing.*;
import java.awt.*;
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

    ArrayList<Menu> menus = new ArrayList<>();

    public MouseHandler mouseHandler = new MouseHandler();

    public FileManager fileManager;

    public EnemyManager enemyManager = new EnemyManager(this);

    public RoomHandler roomHandler = new RoomHandler(this);

    public VisualOutputManager visualOutputManager = new VisualOutputManager(this);

    public ProjectileManager projectileManager = new ProjectileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        menus.add(new MainMenu(this));
        menus.add(new SavesMenu(this));

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
//                System.out.println(deltaTime.getCurrentInterval());
//                System.out.println(enemyManager.getEnemies().size());
                switch (gameState) {
                    case "menu":
                        menus.get(menuNumber).update();

                        repaint();

                        break;
                    case "gameLoop":

//                        if (!enemyManager.getEnemies().isEmpty()) {
//                            System.out.println( "----\n" + enemyManager.getEnemies().
//                                    get(0).attributeManager.showAttributes());
//                        }


                        // 1 UPDATE: update information such as character positions
                        update();

                        // 2 DRAW: draw the screen with the updated information
                        repaint();

                        break;
                }
            }
        }
    }

    public void update(){
        screenHeight = getHeight();
        screenWidth = getWidth();
        enemyManager.update();
        player.update();
        projectileManager.update();

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
//                goblin.draw(g2);
                enemyManager.draw(g2);
                player.draw(g2);
                projectileManager.draw(g2);
                player.weapon.draw(g2);
                visualOutputManager.draw(g2);
                break;
        }

        g2.dispose();
    }
}