package Maksym_Smal.studABNS.MyOwn2DGame.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class AnimationManager {

    private ArrayList<Animation> animations = new ArrayList<>();

    private String currentAnimation = "";

    public void update() {
        Iterator<Animation> iterator = animations.iterator();
        while (iterator.hasNext()) {
            Animation animation = iterator.next();
            animation.update();
        }
    }

    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public String getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(String currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public BufferedImage getCurrentImage() {
        BufferedImage output = null;
        for (Animation animation : animations) {
            if (animation.name.equals(currentAnimation)) {
                output = animation.getCurrentImage();
            }
        }

        if (output == null) {
            throw new IllegalArgumentException("A non-existent animation has been requested");
        }
        else {
            return output;
        }
    }
}
