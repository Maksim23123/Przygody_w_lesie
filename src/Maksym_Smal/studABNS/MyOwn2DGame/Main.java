package Maksym_Smal.studABNS.MyOwn2DGame;

import Maksym_Smal.studABNS.MyOwn2DGame.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Przygody w lesie");

        GamePanel gp = new GamePanel(window);
        window.add(gp);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.startGameThread();
    }
}