package game;

import crosshair.CrossHairListener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GunSounds implements CrossHairListener, Runnable{
    File soundFile;
    AudioInputStream audioInputStream;
    private final boolean running = true;
    public GunSounds(String filePath){
        soundFile = new File(filePath);
    }

    @Override
    public void onShotsFired(int x, int y) {
//System.out.println("Bum Bum!");
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip shot = AudioSystem.getClip();
            shot.open(audioInputStream);
            shot.start();
            Thread.sleep(shot.getMicrosecondLength() / 1_000_000);
        } catch (InterruptedException | LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Problem z dzwiekiem, onShotsFired");
            Thread.currentThread().interrupt(); // Sonar
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) { // musi byc, aby nie bylo problemow.
                try {
                    wait(); // czeka na powiadomienie
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
//                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
