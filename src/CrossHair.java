import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CrossHair implements MouseMotionListener, MouseListener {

    DrawPanel parent;
    // Zmienilem, aby nie bylo zaleznosci, bo przeciez moga byc rozne celowniki
    CrossHair() {
    }
    public CrossHair setDrawPanel(DrawPanel parent){
        this.parent = parent;
        parent.addMouseMotionListener(this);
        parent.addMouseListener(this);
        return this;
    }
    void draw(Graphics g){
        if(activated)g.setColor(Color.RED);
        else g.setColor(Color.WHITE);
//        System.out.println("x=" + x);
//        System.out.println("y=" + y);
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
//        g.drawOval(x, y, 40, 40);

    }

    /* x, y to współrzedne celownika
       activated - flaga jest ustawiana po oddaniu strzału (naciśnięciu przyciku myszy)
       // jesli tak, to parent.repaint();
    */
    int x;
    int y;
    boolean activated = false;

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("weszlismy");
        x = e.getX();
        y = e.getY();
        parent.repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        parent.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Nacisnieto");
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Cos");
    }
    @Override
    public void mouseEntered(MouseEvent e) {System.out.println("Cos");}
    @Override
    public void mouseExited(MouseEvent e) {System.out.println("Cos");}

}