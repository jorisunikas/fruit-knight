package game;

import processing.core.PApplet;
import processing.core.PImage;

/*
 * Hello world!
 */
public class App extends PApplet {
    private Game game;
    public Camera cameraRelease;
    public Camera cameraEdit;
    private LevelEditor editor;
    public float cameraZoom = 4.0f;
    public int screenWidth = 1024;
    public int screenHeight = 768;
    public int worldWidth = screenWidth / (int) cameraZoom;
    public int worldHeight = screenHeight / (int) cameraZoom;
    public int tileSize = 32;
    private float cameraSmoothness = 0.05f;
    public boolean editMode = false;
    public boolean debugMode = true;
    PImage img;

    @Override
    public void settings() {
        size(screenWidth, screenHeight, JAVA2D);
        noSmooth();
    }

    @Override
    public void setup() {
        game = new Game(this);
        cameraRelease = new Camera(this, cameraSmoothness, cameraZoom, game.player);
        cameraEdit = new Camera(this);
        editor = new LevelEditor(this, cameraEdit, game.currentLevel);

        frameRate(60);
        pixelDensity(displayDensity());
        hint(DISABLE_TEXTURE_MIPMAPS);
    }

    @Override
    public void draw() {
        if (!editMode)
            drawRelease();
        else
            drawEdit();
    }

    private void drawRelease() {
        cameraRelease.begin();
        background(255);
        game.render();
        cameraRelease.end();

        if (debugMode)
            game.renderDebug();

        game.update();
        cameraRelease.followPlayer(game.player);
        cameraRelease.update();
    }

    private void drawEdit() {

        cameraEdit.begin();
        background(255);
        game.render();
        editor.draw();
        cameraEdit.end();

        editor.drawMenu();
        cameraEdit.update();
    }

    private void drawGrid() {
        for (int i = 0; i < worldHeight / 16 + 1; i++) {
            for (int j = 0; j < worldWidth / 16 + 1; j++) {
                if ((i + j) % 2 == 0)
                    continue;
                noStroke();
                fill(120);
                rect(j * 16, i * 16, 16, 16);
            }
        }
    }

    private void showMousePosition() {
        fill(0, 0, 255);
        text(String.format("X: %f\nY: %f\n", mouseX / cameraZoom, mouseY / cameraZoom), 10, 10, 100, 100);
    }

    public static void main(String[] args) {
        PApplet.main("game.App");
    }

    public void keyPressed() {
        game.player.handleKeyPressed(key, keyCode);
        if (key == 't')
            debugMode = !debugMode;
        if (key == 'e')
            editMode = !editMode;

    }

    public void keyReleased() {
        if (editMode) {

        } else {

            game.player.handleKeyReleased(key, keyCode);
        }
    }

    public void mouseWheel(processing.event.MouseEvent event) {
        // Zoom with mouse wheel
        cameraEdit.adjustZoom(-event.getCount() * 0.1f);
    }

    public void mouseDragged() {
        if (editMode) {
            float dx = mouseX - pmouseX;
            float dy = mouseY - pmouseY;
            cameraEdit.panWithMouse(dx, dy);
        }
    }

    public void mousePressed() {
        if (editMode) {
            if (mouseButton == LEFT)
                editor.handleMousePressed(mouseX, mouseY);
            else if (mouseButton == RIGHT) {
                editor.handleMouseRightClick(mouseX, mouseY); // ← Add this
            }
        }
    }

}
