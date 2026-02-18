package game;

import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {
    private Game game;
    public Camera cameraRelease;
    public Camera cameraEdit;
    private LevelEditor editor;
    public float cameraZoom = 3.2f;
    public int screenWidth = 1024;
    public int screenHeight = 768;
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
        cameraRelease = new Camera(this, cameraSmoothness, cameraZoom);
        cameraEdit = new Camera(this, 0, 1);
        editor = new LevelEditor(this, cameraEdit, game.currentLevel);

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
        game.renderTime();

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
        if (key == 'l') editor.saveLevel();
        if (key == 'n') game.nextLevel();
        if (key == 'p') game.previousLevel();
        if (key == 'r') game.resetPlayer();

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
                editor.handleMouseRightClick(mouseX, mouseY); 
            }
        }
    }
    
    public void setLevel(Level l){
        editor.setLevel(l);
    }
}
