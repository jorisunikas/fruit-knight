package game;

import java.io.File;
import java.util.ArrayList;

import game.Entities.Ground1;
import game.Entities.Ground2;
import game.Entities.Platform1;
import game.Entities.Platform2;

/**
 * Game
 */
public class Game {
    App app;
    Player player;
    ArrayList<Level> levels;
    Level currentLevel;

    public Game(App app) {
        this.app = app;
        player = new Player(app);
        levels = new ArrayList<>();

        loadTextures();
        loadLevels();
        currentLevel = levels.get(0);
        player.x = currentLevel.getPlayerX();
        player.y = currentLevel.getPlayerY();
    }

    public void render() {
        currentLevel.draw();
        player.draw(app);
    }

    public void update() {
        player.update(currentLevel.getEntities());
    }

    private void loadTextures() {
        Player.loadTexture("resources/knight.png", app);
        Ground1.loadTexture("resources/world_tileset.png", app);
        Ground2.loadTexture("resources/world_tileset.png", app);
        Platform1.loadTexture("resources/platforms.png", app);
        Platform2.loadTexture("resources/platforms.png", app);
    }

    private void loadLevels() {
        File dir = new File("resources/levels");
        File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Directory is empty!");
            return;
        }

        for (File file : files) {
            if (file.isFile() && file.toString().endsWith(".json")) {
                Level l = new Level(file.toString(), app, file.getName().substring(0, file.getName().indexOf('.')));
                levels.add(l);
                System.out.println(String.format("Level '%s' loaded.\n", file.getName()));
            }
        }
    }

    public void renderDebug(){
        float size = 16;
        app.fill(0, 0, 120);
        app.textSize(size);
        player.showDebugMovement(size, size);
    }
}
