package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    Button playButton;

    Button settingButton;

    Button quitButton;

    GamePanel gp;


    public MainMenu(GamePanel gamePanel) {
        this.gp = gamePanel;

        loadImages();
    }

    void loadImages(){
        try {
            playButton = new Button(144, 72, ImageIO.read(getClass().
                    getResourceAsStream("/gui/play_button_dont_active.png")));
            playButton.addImage(ImageIO.read(getClass().
                    getResourceAsStream("/gui/play_button_active.png")));
            playButton.setPosX(gp.screenWidth / 2 - playButton.size.width / 2);
            playButton.setPosY((int)(gp.screenHeight / 1.7) - playButton.size.height / 2);


            settingButton = new Button(243, 72, ImageIO.read(getClass().
                    getResourceAsStream("/gui/settings_button_dont_active.png")));
            settingButton.addImage(ImageIO.read(getClass().
                    getResourceAsStream("/gui/settings_button_active.png")));
            settingButton.setPosX(gp.screenWidth / 2 - settingButton.size.width / 2);
            settingButton.setPosY((int)(gp.screenHeight / 1.7) + settingButton.size.height);

            quitButton = new Button(144, 72, ImageIO.read(getClass().
                    getResourceAsStream("/gui/quit_button_dont_active.png")));
            quitButton.addImage(ImageIO.read(getClass().
                    getResourceAsStream("/gui/quit_button_active.png")));
            quitButton.setPosX(gp.screenWidth / 2 - quitButton.size.width / 2);
            quitButton.setPosY((int)(gp.screenHeight / 1.7) + (int)(quitButton.size.height * 2.5));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (checkHover(playButton)) {
            playButton.setState(1);
        }
        else {
            playButton.setState(0);
        }

        if (checkHover(settingButton)) {
            settingButton.setState(1);
        }
        else {
            settingButton.setState(0);
        }

        if (checkHover(quitButton)) {
            quitButton.setState(1);
        }
        else {
            quitButton.setState(0);
        }

    }

    private boolean checkHover(Button button) {
        int posX = gp.mouseHandler.getMousePosX();
        int posY = gp.mouseHandler.getMousePosY();
        if (button.posX < posX && button.posX + button.size.width > posX &&
                button.posY < posY && button.posY + button.size.height > posY) {
            return true;
        }
        else {
            return  false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(playButton.getImage(), playButton.posX, playButton.posY,
                playButton.size.width , playButton.size.height, null);
        g2.drawImage(settingButton.getImage(), settingButton.posX, settingButton.posY,
                settingButton.size.width , settingButton.size.height, null);
        g2.drawImage(quitButton.getImage(), quitButton.posX, quitButton.posY,
                quitButton.size.width , quitButton.size.height, null);
    }
}
