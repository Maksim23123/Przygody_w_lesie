package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Objects;

public class GamePauseMenu extends Menu {

    Button continueButton;

    Button saveAndQuitButton;

    public GamePauseMenu(GamePanel gamePanel) {
        super(gamePanel);

        defineButtons();
    }

    @Override
    public void update() {
        if (checkHover(continueButton)) {
            continueButton.setState(1);
            if(gamePanel.mouseHandler.getClicked("mouse1")) {
                gamePanel.setGameState("gameLoop");
                gamePanel.soundManager.setPlayBackground(true);
            }
        }
        else {
            continueButton.setState(0);
        }

        if (checkHover(saveAndQuitButton)) {
            saveAndQuitButton.setState(1);
            if(gamePanel.mouseHandler.getClicked("mouse1")) {
                if (gamePanel.enemyManager.getEnemies().isEmpty()) {
                    gamePanel.fileManager.writePlayerData(gamePanel.player.getData());
                    gamePanel.fileManager.writeExploreMap(gamePanel.roomHandler.getExploreMap());
                }
                gamePanel.setMenuNumber(0);
            }
        }
        else {
            saveAndQuitButton.setState(0);
        }

        if (gamePanel.getGameState().equals("menu") && gamePanel.getMenuNumber() == 2 &&
                gamePanel.getKeyHandler().getPressedButtonsQueue().contains("Escape")) {
            gamePanel.setGameState("gameLoop");
            gamePanel.getKeyHandler().setPressedButtonsQueue(new ArrayList<>());
            gamePanel.soundManager.setPlayBackground(true);
        }
    }

    void defineButtons(){
        try {
            continueButton = new Button(255, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/continue_button.png"))));
            continueButton.addImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/continue_button_active.png"))));
            continueButton.setPosX(gamePanel.screenWidth / 2 - continueButton.size.width / 2);
            continueButton.setPosY((int)(gamePanel.screenHeight / 1.7) - continueButton.size.height / 2);
            buttons.add(continueButton);

            saveAndQuitButton = new Button(300, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/save_quit_button.png"))));
            saveAndQuitButton.addImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/save_quit_button_active.png"))));
            saveAndQuitButton.setPosX(gamePanel.screenWidth / 2 - saveAndQuitButton.size.width / 2);
            saveAndQuitButton.setPosY((int)(gamePanel.screenHeight / 1.7) + (int)(saveAndQuitButton.size.height * 2.5));
            buttons.add(saveAndQuitButton);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
