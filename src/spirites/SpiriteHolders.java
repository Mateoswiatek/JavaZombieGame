package spirites;

import factories.SpriteFactory;

import java.util.List;
import java.util.Random;

/**
 * Spirite Holders -przechowuje jeden typ obiektow, ma wlasna ich fabryke, albo wgl zrobic to abkstrakcyjnie.
 */
public class SpiriteHolders {
    public static int MAX_SPIRITES;
    SpriteFactory spriteFactory;
    //TODO zamiast listy, zrobić słownik, i tutaj zrobić schedule tworzenia, tutaj też ma być jakaś lista fabryk?
    // Tak aby można było z jednego miejsca zarządzać wszystkimi obiektami które są produkowane
    final List<Sprite> spriteList;
    Random random = new Random();
    public SpiriteHolders(SpriteFactory spriteFactory, List<Sprite> spriteList){
        this.spriteFactory = spriteFactory;
        this.spriteList = spriteList;
    }

    public void spawnSpirite() {
        synchronized (spriteList) {
            double scale = random.nextDouble(0.5, 2);
            // zawsze ponad linia horyzonut (1000, 460-(int)(scale*Zombie.HEIGHT), scale)));
            // Dotykaja ziemi dolnej: (1000, 680-(int)(scale*Zombie.HEIGHT), scale));
            if(spriteList.size() < MAX_SPIRITES) spriteList.add(spriteFactory.newSprite(1000, random.nextInt(460-(int)(scale*Zombie.HEIGHT), 680-(int)(scale*Zombie.HEIGHT)), scale)); // random.nextInt(460-(int)(scale*Zombie.HEIGHT), 700-(int)(scale*Zombie.HEIGHT)
        }
    }
}
