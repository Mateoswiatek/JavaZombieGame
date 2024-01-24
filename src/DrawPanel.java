import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class DrawPanel  extends JPanel {
    BufferedImage background;
    int FPS = 30;

    ArrayList<Sprite> spriteList = new ArrayList<>();
    DrawPanel(URL backgroundImagageURL) {
        try {
            background = ImageIO.read(backgroundImagageURL);
            int x = 5;
            Random random = new Random();
            while(x-- > 0){
                spriteList.add(new Zombie(800, random.nextInt(200, 512), random.nextDouble(2))); //800
            }

            new AnimationThread().start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        spriteList.forEach(x -> x.draw(g, this));
    }

    class AnimationThread extends Thread{
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