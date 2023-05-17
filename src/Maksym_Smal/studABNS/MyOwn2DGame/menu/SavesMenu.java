package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.fileManager.FileManager;

import javax.imageio.ImageIO;
import java.util.Objects;

public class SavesMenu extends Menu{

    Button save1Button;

    Button save2Button;

    Button save3Button;

    public SavesMenu(GamePanel gamePanel) {
        super(gamePanel);

        defineButtons();
    }

    @Override
    public void update() {
        if (checkHover(save1Button)) {

            if (gamePanel.mouseHandler.isClicked()) {
                gamePanel.setFileManager(new FileManager("saves/save1"));
                gamePanel.mouseHandler.reloadClick();
                gamePanel.setGameState("gameLoop");
            }
        }

    }

    void defineButtons() {
        try {
            save1Button = new Button(288, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/gray_boxes/new_save_place.png"))));
            save1Button.setPosX(gamePanel.screenWidth / 2 - save1Button.size.width / 2);
            save1Button.setPosY((int)(gamePanel.screenHeight / 1.7) - save1Button.size.height / 2);
            buttons.add(save1Button);

            save2Button = new Button(288, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/gray_boxes/new_save_place.png"))));
            save2Button.setPosX(gamePanel.screenWidth / 2 - save2Button.size.width / 2);
            save2Button.setPosY((int)(gamePanel.screenHeight / 1.7) + save2Button.size.height);
            buttons.add(save2Button);

            save3Button = new Button(288, 72, ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/gray_boxes/new_save_place.png"))));
            save3Button.setPosX(gamePanel.screenWidth / 2 - save1Button.size.width / 2);
            save3Button.setPosY((int)(gamePanel.screenHeight / 1.7) + (int)(save3Button.size.height * 2.5));
            buttons.add(save3Button);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
