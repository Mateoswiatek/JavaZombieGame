import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Objects;

//TODO zamiana z wyswietlania jako elementu draw, zamienic na obrazek, latwiej bedzie i mniej zdarzen bo nie trzeba za kazdym razem przemalowywac
public class CrossHair implements MouseMotionListener, MouseListener {

    DrawPanel parent;
    Image cursorImage;

    // Zmienilem, aby nie bylo zaleznosci, bo przeciez moga byc rozne celowniki
    CrossHair() {

        // TODO skorka celownika, ewentualnie dwie skorki, wgl to mozna to rowniez rozbic na bronie, aby celownik byl czescia broni, lista dostepnych broni, kazdy wgrywa swoj celownik i inne rzeczy
        try {
            cursorImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/crosshair1.png")));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
    public CrossHair setDrawPanel(DrawPanel parent){
        this.parent = parent;
        parent.addMouseMotionListener(this);
        parent.addMouseListener(this);
        return this;
    }
    void draw(Graphics g){
/*
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
*/

        // Prosty celownik
//        g.drawOval(x-20, y-20, 40, 40);
    }
    int x;
    int y;
    boolean activated = false;

    @Override
    public void mousePressed(MouseEvent e) {
        // tutaj jakis licznik w zaleznosci od uzywanej broni, np ze nie mozna zbyt czesto uzywac, takie jakby przeladowanie.
        x = e.getX();
        y = e.getY();
        System.out.println("x= " + x);
        System.out.println("y= " + y);

        // Dopiero tutaj pobieramy polozenie myszki, o wiele bardziej wydajne ???
        activated = true;
        System.out.println("Nacisnieto");
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Zwolniono");
        activated = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        x = e.getX();
//        y = e.getY();
//        parent.repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
//        x = e.getX();
//        y = e.getY();
//        parent.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("jestemy wewnatrz");

        Cursor myCursor;

        // TODO zamienic aby celownik byl jako kursor, niby dziala wzglednie, ale trzeba by przekalibrowac i podmieniac na inny obraz???
        // gdy sie to zakomenduje to bedzie ten wczytany, ale trzeba byloby sie pobawic aby obraz byl idelanie tam gdzie kursor oraz przekalibrowac

        // Ukrycie całkowicie kursora
/*
        byte[]imageByte=new byte[0];
        cursorImage=Toolkit.getDefaultToolkit().createImage(imageByte);
        myCursor=Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0,0),"cursor");
*/


        myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage , new Point(256, 256), "cursor"); // miejsce przyłożenia?

        parent.setCursor(myCursor);
    }
    @Override
    public void mouseExited(MouseEvent e) {System.out.println("wyszlismy");}
    @Override
    public void mouseClicked(MouseEvent e) {}
}