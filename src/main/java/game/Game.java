package game;

import java.io.File;
import java.util.ArrayList;

import game.Entities.Fruit;
import game.Entities.Ground1;
import game.Entities.Ground2;
import game.Entities.Platform1;
import game.Entities.Platform2;

/**
 * Game
 */
public class Game {
    public Player player;
    public Level currentLevel;

    private App app;
    private ArrayList<Level> levels;
    private long startTime;
    private long additionalTime;
    private long finishTime;
    private int currentIndex;
    private boolean hasEnded;

    public Game(App app) {
        this.app = app;
        player = new Player(app);
        levels = new ArrayList<>();
        currentIndex = 0;
        additionalTime = 0;

        loadTextures();
        loadAnimations();
        loadLevels();

        currentLevel = levels.get(currentIndex);
        player.x = currentLevel.getPlayerX();
        player.y = currentLevel.getPlayerY();
        startTime = System.currentTimeMillis();
        finishTime = 0;
        hasEnded = false;
    }

    public void render() {
        currentLevel.draw();
        player.draw(app);
    }

    public void update() {
        player.update(currentLevel.getEntities());
        checkFruitCollection();
    }

    public void renderTime() {
        if (hasEnded)
            return;
        int size = 32;
        app.fill(0);
        app.textSize(size);
        app.text(String.format("Time: %.2f", (float) getCurrentTime() / 1000), size * 0.5f, size * 1.1f);
    }

    public void nextLevel() {
        if (checkIndex(currentIndex + 1)) {
            currentIndex++;
            changeLevel(currentIndex);
            player.velocityX = 0;
            player.velocityY = 0;
        } else {
            hasEnded = true;
            finishTime = getCurrentTime();
            stopTime();
        }
    }

    public void previousLevel() {
        if (checkIndex(currentIndex - 1)) {
            currentIndex--;
            changeLevel(currentIndex);
        }
    }

    private boolean checkIndex(int index) {
        return (index >= 0 && index < levels.size());
    }

    private void changeLevel(int index) {
        currentLevel = levels.get(index);
        player.x = currentLevel.getPlayerX();
        player.y = currentLevel.getPlayerY();
        app.setLevel(currentLevel);
    }

    public void resetPlayer() {
        player.x = currentLevel.getPlayerX();
        player.y = currentLevel.getPlayerY();
    }

    private void loadTextures() {
        Player.loadTexture("resources/knight.png", app);
        Ground1.loadTexture("resources/world_tileset.png", app);
        Ground2.loadTexture("resources/world_tileset.png", app);
        Platform1.loadTexture("resources/platforms.png", app);
        Platform2.loadTexture("resources/platforms.png", app);
        Fruit.loadTexture("resources/fruit.png", app);
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

    private void loadAnimations() {
        player.loadAnimations();
    }

    public void renderDebug() {
        float size = 16;
        app.fill(0, 0, 120);
        app.textSize(size);
        player.showDebugMovement(size, size);
    }

    public void checkFruitCollection() {
        for (Entity entity : currentLevel.getEntities()) {
            if (entity instanceof Fruit) {
                Fruit fruit = (Fruit) entity;
                if (!fruit.isCollected() && fruit.collidesWith(player)) {
                    fruit.collect();
                    nextLevel();
                }
            }
        }

    }

    private long getCurrentTime() {
        return System.currentTimeMillis() - startTime + additionalTime;
    }

    public void stopTime() {
        additionalTime += System.currentTimeMillis() - startTime;
    }

    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void renderResult() {
        if (hasEnded) {
            app.textSize(48);
            String text = String.format("You won! Your time is %.3f sec.", (float) finishTime / 1000);
            app.text(text, (app.width - app.textWidth(text)) * 0.5f, 0.25f * app.height);
        }
    }
}
