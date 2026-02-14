package game;

import processing.data.JSONObject;

/**
 * Entity
 */
public abstract class Entity {
    protected float x, y, width, height;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean collidesWith(Entity other){
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }

    abstract public void draw(App app);
    abstract public void saveObject(App app, String filename);
}
