package game;

import crosshair.CrossHair;
import crosshair.CrossHairListener;
import spirites.Sprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DrawPanel  extends JPanel implements CrossHairListener {
    BufferedImage background;
    int FPS = 30;
    final List<Sprite> spriteList;

    //TODO to jest do wywalenia celnik
    // Tutaj tylko ustawiamy ze ten obiekt bedzie wyswietal celownik, bo sam celownik moze byc modyfikowany i ustawiany zupelnie niezaleznie.
    CrossHair crossHair;

    public DrawPanel(URL backgroundImagageURL, CrossHair crossHair, int fps, List<Sprite> spriteList) {
        this.FPS = fps;
        // nie musimy zapisywać informacji na stale o celowniku.
        crossHair.addCrossHairListener(this);

        //TODO to jest do wywalenia
        this.crossHair = crossHair.setDrawPanel(this);
        this.spriteList = spriteList;
        try {
            background = ImageIO.read(backgroundImagageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AnimationThread().start();

        // Zrobione w celowniku, bo to celownik okrela tak nparwde co chce robic?
        // dodawanie naszego obiektu jako Listener
//        this.addMouseMotionListener(crossHair);
//        this.addMouseListener(crossHair);
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        spriteList.forEach(x -> x.draw(g2d, this));

        //TODO to jest do wywalenia
        crossHair.draw(g2d);
    }

    @Override
    public void onShotsFired(int x, int y) {
        System.out.println("DrawPanel dostal informacje o strzale! w x,y = " + x + ", " + y);
    }

    class AnimationThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; ; i++) {
                synchronized (spriteList){
                    spriteList.forEach(Sprite::next);
                }
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