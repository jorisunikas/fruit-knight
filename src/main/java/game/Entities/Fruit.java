package game.Entities;

import game.Entity;
import processing.core.PImage;
import processing.data.JSONObject;
import game.App;

/**
 * Fruit
 */
public class Fruit extends Entity{
    private static PImage texture;
    public static final int spriteWidth = 11;
    public static final int spriteHeight = 13;
    private boolean collected;

    public Fruit(float x, float y){
        super(x, y, spriteWidth, spriteHeight);
        collected = false;
    }

    public void draw(App app){
        app.image(texture, x, y, spriteWidth, spriteHeight);
    }

    public void draw(App app, float scalar){
        app.image(texture, x, y, spriteWidth*scalar, spriteHeight*scalar);
    }

    public static void loadTexture(String path, App app) {
        texture = app.loadImage(path).get(2, 2, spriteWidth, spriteHeight);
    }

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        obj.setFloat("x", x);
        obj.setFloat("y", y);
        obj.setString("class", "Fruit"); 
        return obj;
    }
    public boolean isCollected() {
        return collected;
    }
    
    public void collect() {
        collected = true;
    }
}
