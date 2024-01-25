package factories;

import spirites.Spirites;

public interface SpriteFactory {
    Spirites newSprite(int x, int y, double scale);
}