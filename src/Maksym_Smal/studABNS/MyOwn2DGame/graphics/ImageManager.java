package Maksym_Smal.studABNS.MyOwn2DGame.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ImageManager {
    ArrayList<Image> images = new ArrayList<>();

    public ImageManager() {
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

        loadImage("/gui/gray_boxes/goblin.png","regularGoblinUp1");

        loadImage("/gui/health_bar/healthbar_big_background.png","healthBBg");

        loadImage("/gui/health_bar/healthbar_litle_background.png","healthSBg");

        loadImage("/gui/health_bar/healthbar.png","healthbar");

        loadImage("/weapon/sword.png","sword");
        loadImage("/weapon/bow.png","bow");
        loadImage("/weapon/projectiles/arrow.png","arrow");
        loadImage("/weapon/staff.png","staff");
        loadImage("/weapon/wood.png","wood");

        loadImage("/gui/gray_boxes/minimap/type_0.png","type0");
        loadImage("/gui/gray_boxes/minimap/type_1.png","type1");
        loadImage("/gui/gray_boxes/minimap/type_2.png","type2");
        loadImage("/gui/gray_boxes/minimap/type_3.png","type3");
        loadImage("/gui/gray_boxes/minimap/type_4.png","type4");
        loadImage("/gui/gray_boxes/minimap/type_5.png","type5");
        loadImage("/gui/gray_boxes/minimap/type_6.png","type6");
        loadImage("/gui/gray_boxes/minimap/type_7.png","type7");
        loadImage("/gui/gray_boxes/minimap/type_8.png","type8");
        loadImage("/gui/gray_boxes/minimap/type_9.png","type9");
        loadImage("/gui/gray_boxes/minimap/type_10.png","type10");
        loadImage("/gui/gray_boxes/minimap/type_11.png","type11");
        loadImage("/gui/gray_boxes/minimap/type_12.png","type12");
        loadImage("/gui/gray_boxes/minimap/type_13.png","type13");
        loadImage("/gui/gray_boxes/minimap/type_14.png","type14");

        loadImage("/gui/gray_boxes/minimap/active_type_0.png","activeType0");
        loadImage("/gui/gray_boxes/minimap/active_type_1.png","activeType1");
        loadImage("/gui/gray_boxes/minimap/active_type_2.png","activeType2");
        loadImage("/gui/gray_boxes/minimap/active_type_3.png","activeType3");
        loadImage("/gui/gray_boxes/minimap/active_type_4.png","activeType4");
        loadImage("/gui/gray_boxes/minimap/active_type_5.png","activeType5");
        loadImage("/gui/gray_boxes/minimap/active_type_6.png","activeType6");
        loadImage("/gui/gray_boxes/minimap/active_type_7.png","activeType7");
        loadImage("/gui/gray_boxes/minimap/active_type_8.png","activeType8");
        loadImage("/gui/gray_boxes/minimap/active_type_9.png","activeType9");
        loadImage("/gui/gray_boxes/minimap/active_type_10.png","activeType10");
        loadImage("/gui/gray_boxes/minimap/active_type_11.png","activeType11");
        loadImage("/gui/gray_boxes/minimap/active_type_12.png","activeType12");
        loadImage("/gui/gray_boxes/minimap/active_type_13.png","activeType13");
        loadImage("/gui/gray_boxes/minimap/active_type_14.png","activeType14");

        loadImage("/gui/gray_boxes/minimap/unknown_type_2.png","unknownType2");
        loadImage("/gui/gray_boxes/minimap/unknown_type_3.png","unknownType3");
        loadImage("/gui/gray_boxes/minimap/unknown_type_4.png","unknownType4");
        loadImage("/gui/gray_boxes/minimap/unknown_type_5.png","unknownType5");

        loadImage("/items/heart.png","heart");
        loadImage("/gui/health_bar/gray_heart.png","grayHeart");
        loadImage("/items/power.png","hand");
        loadImage("/items/broken_gray_heart/broken_gray_heart.png","brokenGrayHeart");
        loadImage("/items/broken_gray_heart/broken_active_gray_heart.png","activeBrokenGrayHeart");

        loadImage("/entityes/chest.png","chest");

        loadImage("/player/walk_up_1.png","playerUp1");
        loadImage("/player/walk_up_2.png","playerUp2");

        loadImage("/player/walk_down_1.png","playerDown1");
        loadImage("/player/walk_down_2.png","playerDown2");

        loadImage("/player/walk_left_1.png","playerLeft1");
        loadImage("/player/walk_left_2.png","playerLeft2");
        loadImage("/player/walk_left_3.png","playerLeft3");

        loadImage("/player/walk_right_1.png","playerRight1");
        loadImage("/player/walk_right_2.png","playerRight2");
        loadImage("/player/walk_right_3.png","playerRight3");

        loadImage("/player/idle_step_1.png","playerIdle1");
        loadImage("/player/idle_step_2.png","playerIdle2");

        loadImage("/entityes/goblin/goblin_walk_down_1.png","goblinDown1");
        loadImage("/entityes/goblin/goblin_walk_down_2.png","goblinDown2");
    }

    public void loadImage(String path, String tag) {
        try {
            this.addImage(new Image(ImageIO.read(Objects.requireNonNull(getClass().
                    getResourceAsStream(path))), tag));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
