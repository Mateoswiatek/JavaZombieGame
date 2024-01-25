package spirites;

import factories.SpriteFactory;

import java.util.List;
import java.util.Random;

/**
 * Spirite Holders -przechowuje jeden typ obiektow, ma wlasna ich fabryke, albo wgl zrobic to abkstrakcyjnie.
 */
public class SpiriteHolders {
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
            spriteList.add(spriteFactory.newSprite(1000, random.nextInt(400, 550))); //800 512
        }
    }
}
