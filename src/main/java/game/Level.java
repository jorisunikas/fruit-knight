package game;

import java.util.ArrayList;

/**
 * Level
 */
public class Level {
    ArrayList<Drawable> objs;

    Level(){
        objs = new ArrayList<>();
    }

    public void draw(App app){
        for (Drawable drawable : objs) {
            drawable.draw(app);
        }
    }
}
