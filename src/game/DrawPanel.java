package game;

import crosshair.CrossHair;
import crosshair.CrossHairListener;
import spirites.Spirites;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class DrawPanel  extends JPanel implements CrossHairListener {
    AnimationThread animationThread;
    BufferedImage background;
    int fps = 30;
    final List<Spirites> spiritesList;
    private JLabel label;
    int shotCounter = 0;
    int killed = 0;
    int celnosc = 100;
    static final String DEFAULT_LABEL = "Wynik: %d Celnosc: %d%%";

    public DrawPanel(URL backgroundImagageURL, CrossHair crossHair, int fps, List<Spirites> spiritesList) {
        label = new JLabel();
        label.setForeground(Color.BLACK);
        Font font = label.getFont();
        label.setFont(new Font(font.getName(), font.getStyle(), 30));
        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        showStat();

        crossHair.addCrossHairListener(this);
        crossHair.setDrawPanel(this);

        this.fps = fps;
        this.spiritesList = spiritesList;
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
        // Tabelka
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 430, 40);

        synchronized (spiritesList) {
            // TODO animacja smierci, dodac do tej metody przed usunieciem co jest wolana dla kazdego obiektu w Javie.
            spiritesList.removeIf(sprite -> {
                sprite.draw(g2d, this);
                if(!sprite.isVisble()) {
                    celnosc = (--killed*100)/shotCounter;
                    showStat();
                }
                return !sprite.isVisble();
            });
        }
    }
    @Override
    public void onShotsFired(int x, int y) {
//System.out.println("DrawPanel dostal informacje o strzale! w x,y = " + x + ", " + y);

        // Które działa szybciej???
        //spriteList.remove(spriteList.stream().filter(sprite -> sprite.isHit(x, y)).min(Comparator.comparingDouble(Sprite::getScale)).orElse(null));
        synchronized (spiritesList) {
            spiritesList.stream()
                    .filter(sprite -> sprite.isHit(x, y))
                    .max(Comparator.comparingDouble(Spirites::getScale))
                    .ifPresent(sprite -> {
                        spiritesList.remove(sprite);
                        ++killed;
                    });
        }
        celnosc = (killed*100)/++shotCounter;
        showStat();
    }
    public void showStat(){
        label.setText(DEFAULT_LABEL.formatted(killed, celnosc));
    }

    public AnimationThread getAnimationThread() {
        return animationThread;
    }

    class AnimationThread extends Thread{
        @Override
        public void run() {
            for (;;) {
                synchronized (spiritesList){
                    spiritesList.forEach(Spirites::next);
                }
                repaint();
                try {
                    sleep(1000 / fps);  // 30 klatek na sekundę
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}