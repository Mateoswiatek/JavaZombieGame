package spirites;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Zombie implements  Sprite{
    BufferedImage tape;
    int x = 500;
    int y = 500;
    double scale = 1;

    int index = 0;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 200; // z rysunku;

    /*
    public Zombie(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/walkingdead.png")));
    }*/

    public Zombie(int x, int y, double scale, BufferedImage tape){
        this.x = x;
        this.y = y;
        this.scale = scale;
        try {
            this.tape = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/walkingdead.png")));
        } catch( IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */
    @Override
    public void draw(Graphics g, JPanel parent) {

//        Image img = tape.getSubimage(0, 0, 200, 312); // pobierz klatkę
        Image img = tape.getSubimage(WIDTH * index, 0, WIDTH, HEIGHT); // pobierz klatkę

        g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */
    @Override
    public void next() {
        x -= 5 * scale;
        index = (index + 1) % 10;
    }

    @Override
    public boolean isVisble() {
        return true;
    }

    @Override
    public boolean isCloser(Sprite other) {
        return this.scale > other.getScale();
    }

    @Override
    public double getScale() {
        return scale;
    }
}