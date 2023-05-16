package Maksym_Smal.studABNS.MyOwn2DGame.menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Button {

    int posX = 0;
    int posY = 0;
    Rectangle size = new Rectangle();
    private int state = 0;
    private ArrayList<BufferedImage> images = new ArrayList<>();

    Button(int width, int height, BufferedImage image) {
        size.height = height;
        size.width = width;
        images.add(image);
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setState(int state) {
        if (state < images.size() && state >= 0) {
            this.state = state;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public int getState() {
        return state;
    }


    public void addImage(BufferedImage image) {
        images.add(image);
    }

    public BufferedImage getImage() {
        return images.get(state);
    }
}
