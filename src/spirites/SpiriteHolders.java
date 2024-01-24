package spirites;

import spirites.Sprite;
import factories.SpriteFactory;

import java.util.List;
import java.util.Random;

/**
 * Spirite Holders -przechowuje jeden typ obiektow, ma wlasna ich fabryke, albo wgl zrobic to abkstrakcyjnie.
 */
public class SpiriteHolders {
    SpriteFactory spriteFactory;
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
