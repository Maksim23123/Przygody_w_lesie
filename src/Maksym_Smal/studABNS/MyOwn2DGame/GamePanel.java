package Maksym_Smal.studABNS.MyOwn2DGame;

import Maksym_Smal.studABNS.MyOwn2DGame.entity.Player;
import Maksym_Smal.studABNS.MyOwn2DGame.menu.MainMenu;
import Maksym_Smal.studABNS.MyOwn2DGame.tile.TileManager;
import Maksym_Smal.studABNS.MyOwn2DGame.world.MazeGenerator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {


    String gameState = "mainMenu";
    final int originalTileSize = 32; // 16X16
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public final int screenWidth = 16 * 3 * 30;
    public final int screenHeight = 16 * 3 * 15;

    public final int maxWorldCol = 90;
    public final int maxWorldRow = 90;


    //FPS
    int FPS = 60;


    //Instantiate some things
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    DeltaTime deltaTime = new DeltaTime(FPS);
    public Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    MainMenu mainMenu = new MainMenu(this);

    MazeGenerator mazeGenerator = new MazeGenerator(10, 10);

    public MouseHandler mouseHandler = new MouseHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        deltaTime.startDeltaTime();

        while (gameThread != null) {

            if (deltaTime.getIntervalProgress() >= 1) {

                switch (gameState) {
                    case "mainMenu":
                        mainMenu.update();

                        repaint();

                        break;
                    case "gameLoop":
                        // System.out.println("FPS: " + deltaTime.getCurrentInterval());
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
        player.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        switch (gameState) {
            case "mainMenu":
                mainMenu.draw(g2);
                break;
            case "gameLoop":
                tileManager.draw(g2);
                player.draw(g2);
                break;
        }

        g2.dispose();
    }
}
