import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DrawPanel  extends JPanel {
    BufferedImage background;
    Zombie zombie;
    int FPS = 30;
    DrawPanel(URL backgroundImagageURL) {
        try {
            background = ImageIO.read(backgroundImagageURL);
            zombie = new Zombie(500, 300, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        new AnimationThread().start();
        zombie.draw(g, this);
        //g.drawImage(zombie.tape, 0, 0, getWidth(), getHeight(), this);

    }

    class AnimationThread extends Thread{
        public void run() {
            for (int i = 0; ; i++) {
                zombie.next();
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