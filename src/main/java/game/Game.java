package game;

import java.util.ArrayList;
import java.util.logging.Level;

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

    public Game(App app) {
        this.app = app;
        player = new Player(app, 0, 0);
        levels = new ArrayList<>();

        loadTextures();
    }

    public void render() {
        player.draw(app);
    }

    public void update(){
        player.move();
    }

    private void loadTextures(){
        Player.loadTexture("resources/knight.png", app);
        Ground1.loadTexture("resources/world_tileset.png", app);
        Ground2.loadTexture("resources/world_tileset.png", app);
        Platform1.loadTexture("resources/platforms.png", app);
        Platform2.loadTexture("resources/platforms.png", app);
    }
}
