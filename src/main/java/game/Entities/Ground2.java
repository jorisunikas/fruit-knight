package game.Entities;

import game.Entity;
import processing.core.PImage;
import processing.data.JSONObject;
import game.App;

/**
 * Ground2
 */
public class Ground2 extends Entity {
    private static PImage texture;
    public static final int spriteWidth = 16;
    public static final int spriteHeight = 16;

    public Ground2(float x, float y){
        super(x, y, spriteWidth, spriteHeight);
    }

    public void draw(App app){
        app.image(texture, x, y, spriteWidth, spriteHeight);
    }

    public void draw(App app, float scalar){
        app.image(texture, x, y, spriteWidth*scalar, spriteHeight*scalar);
    }

    public static void loadTexture(String path, App app) {
        texture = app.loadImage(path).get(0, 16, spriteWidth, spriteHeight);
    }
    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        obj.setFloat("x", x);
        obj.setFloat("y", y);
        obj.setString("class", "Ground2"); 
        return obj;
    }
}
