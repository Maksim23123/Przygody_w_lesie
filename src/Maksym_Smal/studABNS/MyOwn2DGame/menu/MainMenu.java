package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class MainMenu extends Menu{

    Button playButton;

    Button quitButton;

    public MainMenu(GamePanel gamePanel) {
        super(gamePanel);

        defineButtons();
    }

    void defineButtons(){
        try {
            playButton = new Button(144, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/play_button_dont_active.png"))));
            playButton.addImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/play_button_active.png"))));
            playButton.setPosX(gamePanel.screenWidth / 2 - playButton.size.width / 2);
            playButton.setPosY((int)(gamePanel.screenHeight / 1.7) - playButton.size.height / 2);
            buttons.add(playButton);

            quitButton = new Button(144, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/quit_button_dont_active.png"))));
            quitButton.addImage(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/quit_button_active.png"))));
            quitButton.setPosX(gamePanel.screenWidth / 2 - quitButton.size.width / 2);
            quitButton.setPosY((int)(gamePanel.screenHeight / 1.7) + (int)(quitButton.size.height * 2.5));
            buttons.add(quitButton);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        if (checkHover(playButton)) {
            playButton.setState(1);
            if(gamePanel.mouseHandler.getClicked("mouse1")) {
                gamePanel.setMenuNumber(1);
            }
        }
        else {
            playButton.setState(0);
        }

        if (checkHover(quitButton)) {
            quitButton.setState(1);
            if(gamePanel.mouseHandler.getClicked("mouse1")) {
                System.exit(0);
            }
        }
        else {
            quitButton.setState(0);
        }
    }
}
