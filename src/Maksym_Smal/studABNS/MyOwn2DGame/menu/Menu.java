package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import Maksym_Smal.studABNS.MyOwn2DGame.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public abstract class Menu {

    ArrayList<Button> buttons = new ArrayList<>();

    GamePanel gamePanel;

    Menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public abstract void update();

    public void draw(Graphics2D g2) {
        for (Button item : buttons) {
            g2.drawImage(item.getImage(), item.posX, item.posY,
                    item.size.width , item.size.height, null);
        }
    }

    protected boolean checkHover(Button button) {
        int posX = gamePanel.mouseHandler.getMousePosX();
        int posY = gamePanel.mouseHandler.getMousePosY();
        return button.posX < posX && button.posX + button.size.width > posX &&
                button.posY < posY && button.posY + button.size.height > posY;
    }
}
