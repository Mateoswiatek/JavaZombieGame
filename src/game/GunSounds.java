package game;

import crosshair.CrossHairListener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//TODO dodać GunSounds jako lissenera przy zmianie broni, i w tedy ma zmienić dźwięk.
public class GunSounds implements CrossHairListener, Runnable{
    File soundFile;
    AudioInputStream audioInputStream;

    private volatile boolean running = true;
    public GunSounds(String filePath){
        soundFile = new File(filePath);
    }

    @Override
    public void onShotsFired(int x, int y) {
//        System.out.println("Bum Bum!");
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip shot = AudioSystem.getClip();
            shot.open(audioInputStream);
            shot.start();
            Thread.sleep(shot.getMicrosecondLength() / 1_000_000);
        } catch (InterruptedException | LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) { // musi byc, aby nie bylo problemow.
                try {
                    wait(); // czeka na powiadomienie
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
