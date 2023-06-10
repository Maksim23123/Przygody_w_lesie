package Maksym_Smal.studABNS.MyOwn2DGame.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoundManager {

    private ArrayList<Clip> sounds = new ArrayList<>();

    private int backgroundSoundIndex = 4;

    private Clip background;

    private boolean playBackground = false;

    public SoundManager() {
        loadSounds();
        try {
            background = AudioSystem.getClip();
            background = sounds.get(backgroundSoundIndex);
            background.setFramePosition(0);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    void loadSounds() {
        loadSound("/sounds/hit_hurt.wav");
        loadSound("/sounds/player_hurt.wav");
        loadSound("/sounds/powerup.wav");
        loadSound("/sounds/powerUpPermanent.wav");
        loadSound("/sounds/background_sound.wav");
    }

    public void update() {
        if (background.getFrameLength() <= background.getFramePosition()) {
            background.setFramePosition(0);
        }

        if (playBackground) {
            background.start();
        }
        else {
            background.stop();
        }
    }

    public void loadSound(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(getClass().getResourceAsStream(filename)));

            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            sounds.add(clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(int index) {
        if (index >= 0 && index < sounds.size()) {
            Clip clip = sounds.get(index);
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void setPlayBackground(boolean playBackground) {
        this.playBackground = playBackground;
    }
}