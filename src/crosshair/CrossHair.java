package crosshair;

import game.DrawPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.io.IOException;

public class CrossHair implements MouseMotionListener, MouseListener {
    Cursor normalCursor;
    Cursor shotCursor;
    int x;
    int y;
//    boolean activated = false; // czy jest aktywny, do dalszego rozwoju ?
    private DrawPanel parent;

    public CrossHair() {
        Image normalCursorImage;
        Image shotCursorImage;
        try {
            normalCursorImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/crosshair1_normal_32.png")));
            shotCursorImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/crosshair1_fire_32.png")));
            int xNormmal = normalCursorImage.getWidth(null);
            int yNormal = normalCursorImage.getHeight(null);
            int xShot = shotCursorImage.getWidth(null);
            int yShot = shotCursorImage.getHeight(null);
            normalCursor = Toolkit.getDefaultToolkit().createCustomCursor(normalCursorImage , new Point(xNormmal/2, yNormal/2), "cursor"); // miejsce przyłożenia wzgledem obrazka
            shotCursor = Toolkit.getDefaultToolkit().createCustomCursor(shotCursorImage , new Point(xShot/2, yShot/2), "cursor"); // miejsce przyłożenia wzgledem obrazka
//            throw new IOException("TESTOWO");
        } catch (IOException ex) {
            System.err.println("Nie udalo sie zaladowac skorek dla celownika");
            normalCursor = Cursor.getDefaultCursor();
            shotCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
        }
   }
    List<CrossHairListener> listeners = new ArrayList<>();
    public void addCrossHairListener(CrossHairListener e){
        listeners.add(e);
    }
    void notifyListeners(){
        for(var e:listeners)
            e.onShotsFired(x,y);
    }
    @Override
    public void mousePressed(MouseEvent e) {
        parent.setCursor(shotCursor);
//System.out.println("W celowniku x,y = " + x + ", " + y);
        x = e.getX();
        y = e.getY();
//        activated = true;
        notifyListeners();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            Thread.sleep(200);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        parent.setCursor(normalCursor);
//        System.out.println("Zwolniono");
//        activated = false;
    }
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        parent.setCursor(normalCursor);
    }
    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("wyszlismy");
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    public void setDrawPanel(DrawPanel drawPanel) {
        this.parent = drawPanel;
        parent.addMouseMotionListener(this);
        parent.addMouseListener(this);
    }
}

// Ukrycie całkowicie kursora
/*
        byte[]imageByte=new byte[0];
        cursorImage=Toolkit.getDefaultToolkit().createImage(imageByte);
        myCursor=Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0,0),"cursor");
        parent.setCursor(cursorImage);
*/

    /*
    public CrossHair setDrawPanel(DrawPanel parent){
        this.parent = parent;
        parent.addMouseMotionListener(this);
        parent.addMouseListener(this);
        return this;
    }
    public void draw(Graphics g){
        if(activated)g.setColor(Color.RED);
        else g.setColor(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));
        g2d.translate(x, y);
        g2d.drawOval(-20, -20, 40, 40);
        // krzyżyk
        int crossSize = 30;
        int crossSizeSpace = 10;
        g2d.drawLine(-crossSize, 0, -crossSizeSpace, 0); // Linia pozioma1
        g2d.drawLine(crossSizeSpace, 0, crossSize, 0); // Linia pozioma2

        g2d.drawLine(0, -crossSize, 0, -crossSizeSpace); // Linia pionowa1
        g2d.drawLine(0, crossSizeSpace, 0, crossSize); // Linia pionowa2

        // Prosty celownik
//        g.drawOval(x-20, y-20, 40, 40);
    }
    */