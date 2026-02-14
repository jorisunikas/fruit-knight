package game;

import processing.core.PImage;
import processing.data.JSONObject;

/**
 * Player
 */
public class Player extends Entity {
    private static PImage texture;
    private static PImage texturePack;
    private final static int spriteWidth = 13;
    private final static int spriteHeight = 19;
    private final static float dx = 1.2f;
    private App app;

    Player(App app, float x, float y) {
        super(x, y, spriteWidth, spriteHeight);

        this.app = app;
    }

    public void draw(App app) {
        app.image(texture, x, y, spriteWidth, spriteHeight);
    }

    public void move(){
        if (app.keyPressed){
            if (app.key == 'd') x += dx;
            if (app.key == 'a') x -= dx;
        }
    }

    public static void loadTexture(String path, App app) {
        texturePack = app.loadImage(path);
        Player.texture = texturePack.get(9, 9, spriteWidth, spriteHeight);
    }

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        obj.setFloat("x", x);
        obj.setFloat("y", y);
        obj.setString("class", "Player");
        return obj;
    }
}
