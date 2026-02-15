package game;

import java.util.ArrayList;
import game.Entities.Ground1;
import game.Entities.Ground2;
import game.Entities.Platform1;
import game.Entities.Platform2;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * Level
 */
public class Level {
    private ArrayList<Entity> objs;
    private App app;
    public String name;

    public Level(String filepath, App app, String name) {
        this.name = name;
        this.app = app;
        objs = new ArrayList<>();
        JSONArray array = app.loadJSONArray(filepath);
        for (int i = 0; i < array.size(); i++) {
            JSONObject item = array.getJSONObject(i);
            float x = item.getInt("x"), y = item.getInt("y");
            objs.add(createEntity(item.getString("class"), x, y));
        }
    }

    public ArrayList<Entity> getEntities(){
        return objs;
    }

    private Entity createEntity(String className, float x, float y) {
        switch (className) {
            case "Ground1":
                return new Ground1(x, y);
            case "Ground2":
                return new Ground2(x, y);
            case "Platform1":
                return new Platform1(x, y);
            case "Platform2":
                return new Platform2(x, y);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown entity class '%s' at (%f, %f)",
                                className, x, y));
        }
    }

    public void draw() {
        for (Entity entity : objs) {
            entity.draw(app);
        }
    }
    public void saveToJSON(){
        JSONArray arr = new JSONArray();
        for (Entity entity : objs) {
            arr.append(entity.toJSONObject());
        }
        app.saveJSONArray(arr, String.format("./resources/levels/%s.json", name));
    }
}
