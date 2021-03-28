package ui.gui;

import model.World;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic implements Runnable {

    private final World world;

    public BackgroundMusic(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        try {
            final Clip clip = AudioSystem.getClip();
            final AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    new File("./data/RoyaltyFreeBattle.wav"));
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Thread.sleep(10000);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
