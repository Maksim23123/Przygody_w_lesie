package Maksym_Smal.studABNS.MyOwn2DGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

    ArrayList<Integer> activeButtons = new ArrayList<>();
    ArrayList<String> pressedButtonsQueue = new ArrayList<>();

    KeyHandler() {
        fillActiveButtons();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (int i : activeButtons) {
            if (e.getKeyCode() == i && !pressedButtonsQueue.contains(KeyEvent.getKeyText(i))) {
                pressedButtonsQueue.add(KeyEvent.getKeyText(e.getKeyCode()));
                break;
            }
        }
    }

    public ArrayList<String> getPressedButtonsQueue() {
        return pressedButtonsQueue;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedButtonsQueue.remove(KeyEvent.getKeyText(e.getKeyCode()));
    }



    private void fillActiveButtons() {
        activeButtons.add(KeyEvent.VK_W);
        activeButtons.add(KeyEvent.VK_S);
        activeButtons.add(KeyEvent.VK_D);
        activeButtons.add(KeyEvent.VK_A);
        activeButtons.add(KeyEvent.VK_SPACE);
    }
}
