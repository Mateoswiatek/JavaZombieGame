package factories;

import Spirites.Sprite;

public interface SpriteFactory {
    Sprite newSprite(int x, int y);
}