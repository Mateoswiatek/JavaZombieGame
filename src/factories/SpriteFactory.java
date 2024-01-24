package factories;

import spirites.Sprite;

public interface SpriteFactory {
    Sprite newSprite(int x, int y);
}