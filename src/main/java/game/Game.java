package game;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Game
 */
public class Game {
    App app;
    Player player;
    ArrayList<Level> levels;

    public Game(App app) {
        this.app = app;
        player = new Player(0, 0);
        levels = new ArrayList<>();

        loadTextures();
    }

    public void render() {
        player.draw(app);
    }

    private void loadTextures(){
        Player.loadTexture("resources/knight.png", app);
    }
}
