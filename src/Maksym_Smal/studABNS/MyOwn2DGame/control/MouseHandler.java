package Maksym_Smal.studABNS.MyOwn2DGame.control;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseHandler extends MouseListener {

    ArrayList<String> activeButtons = new ArrayList<>();
    private ArrayList<String> pressedButtonsQueue = new ArrayList<>();

    private int mousePosX;
    private int mousePosY;

    public MouseHandler() {
        fillActiveButtons();
    }

    public int getMousePosX() {
        return mousePosX;
    }

    public int getMousePosY() {
        return mousePosY;
    }

    public boolean getClicked(String button) {
        if (pressedButtonsQueue.contains(button)) {
            pressedButtonsQueue.remove(button);
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
        if (!pressedButtonsQueue.contains("mouse" + e.getButton()) && activeButtons.contains("mouse" + e.getButton())) {
            pressedButtonsQueue.add("mouse" + e.getButton());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressedButtonsQueue.remove("mouse" + e.getButton());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }

    public ArrayList<String> getPressedButtonsQueue() {
        return pressedButtonsQueue;
    }

    private void fillActiveButtons() {
        activeButtons.add("mouse1");
        activeButtons.add("mouse3");
    }
}
