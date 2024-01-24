package factories;

import Spirites.Sprite;
import Spirites.Zombie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * Jest Singletonem
 */
public class ZombieFactory implements SpriteFactory{
    BufferedImage tape;
//     static boolean istnieje = false;
    Random random = new Random();

    private static ZombieFactory instance;
    private ZombieFactory(String pathToImage) { // "/resources/walkingdead.png"
        try {
            tape = ImageIO.read(Objects.requireNonNull(getClass().getResource(pathToImage)));
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            System.out.println("udalo sie");
            //istnieje = true;
        }
    }
    public static ZombieFactory getInstance(String pathToImage){
        if(instance == null) instance = new ZombieFactory(pathToImage);
        return instance;
    }

    @Override
    public Sprite newSprite(int x, int y) {
        double scale = random.nextDouble(2);
        return new Zombie(x, y, scale, tape);
    }
}
