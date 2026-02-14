package game;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Level
 */
public class Level {
    ArrayList<Drawable> objs;

    Level(){
        objs = new ArrayList<>();
    }

    public void draw(PApplet app){
        for (Drawable drawable : objs) {
            drawable.draw(app);
        }
    }
}
