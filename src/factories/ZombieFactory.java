package factories;

import spirites.Spirites;
import spirites.Zombie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
/**
 * Jest Singletonem
 */
public class ZombieFactory implements SpriteFactory{
    static String defaultPathToImage = "../resources/walkingdead.png";
    BufferedImage tape;
    private static ZombieFactory instance;
    public static ZombieFactory getInstance(){
        if(instance == null) instance = new ZombieFactory(defaultPathToImage);
        return instance;
    }
    public static ZombieFactory getInstance(String customPathToImage){
        if(instance == null) defaultPathToImage = customPathToImage;
        return getInstance();
    }
    private ZombieFactory(String pathToImage) {
        try {
            tape = ImageIO.read(Objects.requireNonNull(getClass().getResource(pathToImage)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public Spirites newSprite(int x, int y, double scale) {
        return new Zombie(x, y, scale, tape);
    }
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