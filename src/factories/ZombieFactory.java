package factories;

import spirites.Sprite;
import spirites.Zombie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * Jest Singletonem
 */
//TODO dodać jakieś parametry tylko dla zoombie, ewentualnie ogólnie jakieś inne interfejsy np dźwięk czy coś.
public class ZombieFactory implements SpriteFactory{
    public static String defaultPathToImage = "../resources/walkingdead.png";
    BufferedImage tape;
    Random random = new Random();


    // Normalny sposób Singleton-a
    private static ZombieFactory instance;
    public static ZombieFactory getInstance(){
        if(instance == null) instance = new ZombieFactory(defaultPathToImage);
        return instance;
    }
    public static ZombieFactory getInstance(String customPathToImage){
        if(instance == null) defaultPathToImage = customPathToImage;
        return getInstance();
    }


/*
    // czy można tak przekazywać parametry ? tutaj pathToImage
    // chyba coś z tym podejściem jest nie halo
    private static class ZombieFactoryHelper {
        private static final ZombieFactory INSTANCE = new ZombieFactory(defaultPathToImage);
    }
    public static ZombieFactory getInstance() {
        return ZombieFactoryHelper.INSTANCE;
    }
    public static ZombieFactory getInstance(String customPathToImage){
        defaultPathToImage = customPathToImage;
        return getInstance();
    }
 */
    private ZombieFactory(String pathToImage) {
        try {
            tape = ImageIO.read(Objects.requireNonNull(getClass().getResource(pathToImage)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public Sprite newSprite(int x, int y, double scale) {
        return new Zombie(x, y, scale, tape);
    }
}
