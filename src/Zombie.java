import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Zombie {
    BufferedImage tape;
    int x = 500;
    int y = 500;
    double scale = 1;

    int index = 0;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 200; // z rysunku;

    Zombie(int x, int y, double scale) throws IOException {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/walkingdead.png"))); // getClass().getResource("/resources/walkingdead.png")
    }


    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */

    public void draw(Graphics g, JPanel parent) {

//        Image img = tape.getSubimage(0, 0, 200, 312); // pobierz klatkę
        Image img = tape.getSubimage(WIDTH * index, 0, WIDTH, HEIGHT); // pobierz klatkę

        g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */

    public void next() {
        // x -= 20 * scale;
        index = (index + 1) % 10;
    }

}