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

    public boolean getClicked() {
        if (clicked) {
            clicked = false;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }


}
