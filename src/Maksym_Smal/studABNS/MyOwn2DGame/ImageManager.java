package Maksym_Smal.studABNS.MyOwn2DGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ImageManager {
    ArrayList<Image> images = new ArrayList<>();

    ImageManager() {
        loadImages();
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public BufferedImage getImageByTag(String tag) {
        for (Image image : images) {
            if (image.getTag().equals(tag)) {
                return image.getImage();
            }
        }
        throw new IllegalArgumentException("Image not found");
    }

    void loadImages() {
        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/gray_boxes/goblin.png"))), "regularGoblinUp1"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/health_bar/healthbar_big_background.png"))), "healthBBg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/health_bar/healthbar_litle_background.png"))), "healthSBg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/gui/health_bar/healthbar.png"))), "healthbar"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/weapon/sword.png"))), "sword"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/weapon/bow.png"))), "bow"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream("/weapon/projectiles/arrow.png"))), "arrow"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
