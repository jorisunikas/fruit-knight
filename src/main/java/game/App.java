package game;

import processing.core.PApplet;

public class App extends PApplet {
    public boolean editMode = false;
    public boolean debugMode = false;
    public Camera cameraRelease;
    public Camera cameraEdit;

    private Game game;
    private LevelEditor editor;
    private final float cameraZoom = 3.2f;
    private final int screenWidth = 1024;
    private final int screenHeight = 768;
    private final float cameraSmoothness = 0.05f;

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
        game.renderResult();

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

    public static void main(String[] args) {
        PApplet.main("game.App");
    }

    public void keyPressed() {
        game.player.handleKeyPressed(key, keyCode);
        if (key == 't')
            debugMode = !debugMode;
        if (key == 'e') {
            editMode = !editMode;
            if (editMode)
                game.stopTime();
            else
                game.startTime();
        }
        if (key == 'l')
            editor.saveLevel();
        if (key == 'n')
            game.nextLevel();
        if (key == 'p')
            game.previousLevel();
        if (key == 'r')
            game.resetPlayer();
        if (key == 'o')
            game.createNewLevel();

    }

    public void keyReleased() {
        if (editMode) {

        } else {

            game.player.handleKeyReleased(key, keyCode);
        }
    }

    public void mouseWheel(processing.event.MouseEvent event) {
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

    public void setLevel(Level l) {
        editor.setLevel(l);
    }
}
