import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CrossHair  { // implements MouseMotionListener, MouseListener

    DrawPanel parent;
    // Zmienilem, aby nie bylo zaleznosci, bo przeciez moga byc rozne celowniki
    CrossHair() {
    }
    public CrossHair setDrawPanel(DrawPanel parent){
        this.parent = parent;
        return this;
    }
    void draw(Graphics g){

        if(activated)g.setColor(Color.RED);
        else g.setColor(Color.WHITE);
    }

    /* x, y to współrzedne celownika
       activated - flaga jest ustawiana po oddaniu strzału (naciśnięciu przyciku myszy)
       // jesli tak, to parent.repaint();
    */
    int x;
    int y;
    boolean activated = false;
}