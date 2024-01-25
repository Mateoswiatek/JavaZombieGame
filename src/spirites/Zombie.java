package spirites;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//TODO Ogółnie Sprite zamiast wszsytkich hitboxu, pośredni jest wewnątrz.
// najpierw sprawdzamy se, ogólny / główny hitbox, i dopiero jeśli jest w tym dużym, no to dopiero w tedy sprawdzamy konkretne hitboxy.
// dzięki temu oszczędzamy na obliczeniach. sprawdzamy najbardziej ogółny hitbox w którym zawierają się wszytkie inne.

public class Zombie implements Spirites {
    BufferedImage tape;
    int x;
    int y;
    int real_width;
    int real_heiegt;
    double scale = 1;
    int index = 0;  // numer wyświetlanego obrazka
    public static int HEIGHT = 312; // z rysunku;
    public static int WIDTH = 200; // z rysunku;

    public Zombie(int x, int y, double scale, BufferedImage tape){
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = tape; // ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/walkingdead.png")));
        this.real_width = (int) (WIDTH * scale); // +1 tak orientacyjnie, na wszelki wypadek
        this.real_heiegt = (int) (HEIGHT * scale);
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
        Image img = tape.getSubimage(WIDTH * index, 0, WIDTH, HEIGHT); // pobierz klatkę
//        g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
        g.drawImage(img, x, y, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);

        //Hitbox
        g.drawRect(x, y, real_width, real_heiegt);
        int dotSize = 10; // Rozmiar kropki
        g.fillOval(x - dotSize/2, y - dotSize/2, dotSize, dotSize);
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
        return x > -real_width; // kiedy caly wyjdzie poza ramke lewa. lewa ramka jest na 0
    }
    @Override
    public boolean isCloser(Spirites other) {
        return this.scale > other.getScale();
    }

    @Override
    public boolean isHit(int _x, int _y) {
        return (x < _x && _x < x+real_width) && (y < _y && _y < y+real_heiegt);
    }

    @Override
    public double getScale() {
        return scale;
    }
}