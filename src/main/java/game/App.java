package game;

import processing.core.PApplet;
import processing.core.PImage;

/*
 * Hello world!
 */
public class App extends PApplet {
    private Game game;
    public float zoom = 4.0f;
    public int screenWidth = 1000;
    public int screenHeight = 700;
    public int worldWidth = screenWidth / (int) zoom;
    public int worldHeight = screenHeight / (int) zoom;
    public int tileSize = 32;
    PImage img;

    @Override
    public void settings() {
        size(screenWidth, screenHeight, JAVA2D);
        noSmooth();
    }

    @Override
    public void setup() {
        game = new Game(this);

        frameRate(120);
        pixelDensity(displayDensity());
        hint(DISABLE_TEXTURE_MIPMAPS);
    }

    @Override
    public void draw() {
        scale(4.0f);
        int rS = 24;
        rect(0, 0, rS, rS);
        rect(worldWidth - rS, 0, rS, rS);
        rect(worldWidth - rS, worldHeight - rS, rS, rS);
        rect(0, worldHeight - rS, rS, rS);

        game.render();
    }

    public static void main(String[] args) {
        PApplet.main("game.App");
    }
}
