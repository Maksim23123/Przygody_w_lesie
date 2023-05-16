package Maksym_Smal.studABNS.MyOwn2DGame;

import java.awt.event.MouseEvent;

public class MouseHandler extends MouseListener {

    private boolean clicked = false;
    private int mousePosX;
    private int mousePosY;

    public int getMousePosX() {
        return mousePosX;
    }

    public int getMousePosY() {
        return mousePosY;
    }

    public void reloadClick() {
        clicked = false;
    }

    public boolean isClicked() {
        return clicked;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked = true;
    }
}
