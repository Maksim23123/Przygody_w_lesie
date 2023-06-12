package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;
import Maksym_Smal.studABNS.MyOwn2DGame.world.MazeGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class EndGameMenu extends Menu{


    Button quitButton;

    String text;

    public EndGameMenu(GamePanel gamePanel) {
        super(gamePanel);

        defineButtons();
    }

    void defineButtons(){
        try {

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

        if (checkHover(quitButton)) {
            quitButton.setState(1);
            if(gamePanel.mouseHandler.getClicked("mouse1")) {
                gamePanel.setMenuNumber(0);
            }
        }
        else {
            quitButton.setState(0);
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g2) {
//        text = gamePanel.player.attributeManager.showAttributes();
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));

        int outputHeight = gamePanel.screenHeight / 3;

        String[] outputLines = text.split("\n");

        for (String outputLine : outputLines) {
            g2.drawString( outputLine, gamePanel.screenWidth / 3, outputHeight);
            outputHeight += 45;
        }


        super.draw(g2);
    }
}
