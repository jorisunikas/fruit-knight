package game.Entities;

import game.Entity;
import processing.core.PImage;
import processing.data.JSONObject;
import game.App;

/**
 * Ground1
 */
public class Ground1 extends Entity {
    private static PImage texture;
    private static final int spriteWidth = 16;
    private static final int spriteHeight = 16;

    public Ground1(float x, float y){
        super(x, y, spriteWidth, spriteHeight);
    }

    public void draw(App app){
        app.image(texture, x, y, spriteWidth, spriteHeight);
    }

    public static void loadTexture(String path, App app) {
        texture = app.loadImage(path).get(0, 0, spriteWidth, spriteHeight);
    }

    public void saveObject(App app, String filename){
        JSONObject obj = new JSONObject();
        obj.setFloat("x", x);
        obj.setFloat("y", y);
        obj.setString("class", "Ground1"); 
        app.saveJSONObject(obj, filename);
    }
}
