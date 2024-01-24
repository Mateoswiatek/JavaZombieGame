import Spirites.Sprite;
import factories.SpriteFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class DrawPanel  extends JPanel {
    SpriteFactory factory;
    BufferedImage background;
    int FPS = 30;

    ArrayList<Sprite> spriteList = new ArrayList<>();
    Random random = new Random();
    public DrawPanel(URL backgroundImagageURL, SpriteFactory factory) {
        this.factory=factory;

        try {
            background = ImageIO.read(backgroundImagageURL);
            // int x = 5; while(x-- > 0){ spawnSpirite(); }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AnimationThread().start();
    }

    public void spawnSpirite() {
        spriteList.add(factory.newSprite(1000, random.nextInt(400, 550))); //800 512
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        spriteList.forEach(x -> x.draw(g2d, this));
    }

    class AnimationThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; ; i++) {
                spriteList.forEach(Sprite::next);
                repaint();
                try {
                    sleep(1000 / FPS);  // 30 klatek na sekundę
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}