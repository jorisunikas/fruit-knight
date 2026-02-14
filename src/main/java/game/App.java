package game;

import java.awt.MouseInfo;

import game.Entities.Ground1;
import game.Entities.Ground2;
import game.Entities.Platform1;
import game.Entities.Platform2;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

/*
 * Hello world!
 */
public class App extends PApplet {
    private Game game;
    public float zoom = 4.0f;
    public int screenWidth = 1024;
    public int screenHeight = 768;
    public int worldWidth = screenWidth / (int) zoom;
    public int worldHeight = screenHeight / (int) zoom;
    public int tileSize = 32;
    private boolean editorMode = false;
    PImage img;

    @Override
    public void settings() {
        size(screenWidth, screenHeight, JAVA2D);
        noSmooth();
    }

    @Override
    public void setup() {
        game = new Game(this);

        frameRate(60);
        pixelDensity(displayDensity());
        hint(DISABLE_TEXTURE_MIPMAPS);
    }

    @Override
    public void draw() {
        background(255);
        scale(4.0f);
        drawGrid();
        showMousePosition();

        game.render();
        game.update();
    }

    private void drawGrid() {
        for (int i = 0; i < worldHeight / 16 +1; i++) {
            for(int j=0;j<worldWidth/16+1;j++){
                if((i+j)%2 == 0) continue;
                noStroke();
                fill(120);
                rect(j*16, i*16, 16, 16);
            }
        }
    }

    private void showMousePosition(){
        fill(0, 0, 255);
        text(String.format("X: %f\nY: %f\n", mouseX/zoom, mouseY/zoom), 10, 10, 100, 100);
    }

    public static void main(String[] args) {
        PApplet.main("game.App");
    }
}
