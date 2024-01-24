package Spirites;

import javax.swing.*;
import java.awt.*;

public interface Sprite {
    /**
     * Rysuje postać
     * @param g
     * @param parent
     */
    void draw(Graphics g, JPanel parent);

    /**
     * Przechodzi do następnej klatki
     */
    void next();

    /**
     * Czy już zniknął z ekranu
     * @return
     */
    default boolean isVisble(){return true;}

    /**
     * Czy punkt o współrzędnych _x, _y leży w obszarze postaci -
     * czyli czy trafiliśmy ją strzelając w punkcie o tych współrzednych
     * @param _x
     * @param _y
     * @return
     */
    default boolean isHit(int _x,int _y){return false;}

    /** Czy jest bliżej widza niż other, czyli w naszym przypadku czy jest większy,
     * czyli ma wiekszą skalę...
     *
     * @param other
     * @return
     */
    default boolean isCloser(Sprite other){return false;}

    default double getScale() {
        return 1.0;
    }

}