package Maksym_Smal.studABNS.MyOwn2DGame.graphics;

import java.awt.image.BufferedImage;

public class Image {
    private String tag;
    private BufferedImage image;

    public Image(BufferedImage image, String tag) {
        this.image = image;
        this.tag = tag;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }
}
