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
import java.util.Comparator;
import java.util.List;

public class DrawPanel  extends JPanel implements CrossHairListener {
    public AnimationThread animationThread;
    BufferedImage background;
    int FPS = 30;
    final List<Sprite> spriteList;

    //TODO to jest do wywalenia celnik
    // Tutaj tylko ustawiamy ze ten obiekt bedzie wyswietal celownik, bo sam celownik moze byc modyfikowany i ustawiany zupelnie niezaleznie.
    CrossHair crossHair;
    private JLabel label;
    int shotCounter = 0;
    int killed = 0;

    public DrawPanel(URL backgroundImagageURL, CrossHair crossHair, int fps, List<Sprite> spriteList) {
        label = new JLabel("Wynik: " + killed + " Celnosc:");
        label.setForeground(Color.BLACK);
        Font font = label.getFont();
        label.setFont(new Font(font.getName(), font.getStyle(), 30));

        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);

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
        animationThread = new AnimationThread();
        animationThread.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        synchronized (spriteList) {
//            spriteList.forEach(x -> x.draw(g2d, this));
//            System.out.println(spriteList.size());
//            spriteList.forEach(x -> System.out.println(x.isVisble()));
//            spriteList.removeIf(x -> !x.isVisble());

            // TODO animacja smierci, dodac do tej metody przed usunieciem co jest wolana dla kazdego obiektu w Javie.
            spriteList.removeIf(sprite -> {
                sprite.draw(g2d, this);
                if(!sprite.isVisble()) label.setText("Wynik: " + --killed);
                return !sprite.isVisble(); // usuwa jesli jest false
            });

        }

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 430, 40);

        //TODO to jest do wywalenia
        crossHair.draw(g2d);
    }

    @Override
    public void onShotsFired(int x, int y) {
        shotCounter++;
        //System.out.println("DrawPanel dostal informacje o strzale! w x,y = " + x + ", " + y);
        //TODO dokonczyc to
        // bierzemy wszystkie ktorych trafil, i usuwamy najblizszego., przekazujemy strumien do usuwania.
        // Jesli nie znajdzie takiego jakiego trafilismy, to nulla, nic nie usunie, pytanie czy przeszukuje jesl ma usunac nulla?

//        spriteList.stream().filter(sprite -> sprite.isHit(x, y)).forEach(sprite -> System.out.println("trafiono"));

        // Które działa szybciej???
        //spriteList.remove(spriteList.stream().filter(sprite -> sprite.isHit(x, y)).min(Comparator.comparingDouble(Sprite::getScale)).orElse(null));
        spriteList.stream()
                .filter(sprite -> sprite.isHit(x, y))
                .max(Comparator.comparingDouble(Sprite::getScale))
                .ifPresent(sprite -> {
                    spriteList.remove(sprite);
                    killed++;
                });
        label.setText("Wynik: " + killed + " Celnosc: " + 100*killed/shotCounter + "%");
    }

    class AnimationThread extends Thread{
        @Override
        public void run() {
            for (;;) {
                synchronized (spriteList){
                    spriteList.forEach(Sprite::next);
                }
                repaint();
                try {
                    sleep(1000 / FPS);  // 30 klatek na sekundę
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}