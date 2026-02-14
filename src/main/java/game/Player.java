package game;

import processing.core.PImage;

/**
 * Player
 */
public class Player implements Drawable {
    private static PImage texture;
    private static PImage texturePack;
    private int spriteWidth = 13;
    private int spriteHeight = 19;
    private float x, y;

    Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(App app) {
        app.image(texture, x, y, spriteWidth, spriteHeight);

    }

    public static void loadTexture(String path, App app) {
        texturePack = app.loadImage(path);
        Player.texture = texturePack.get(9, 9, 13, 19);
    }
}
