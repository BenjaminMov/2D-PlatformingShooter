package ui.gui;

import model.World;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic implements Runnable {

    private final World world;
    private Clip clip;

    public BackgroundMusic(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        try {
            clip = AudioSystem.getClip();
            final AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    new File("./data/RoyaltyFreeBattle.wav"));
            clip.open(inputStream);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }
}
