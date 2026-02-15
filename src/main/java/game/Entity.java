package game;

import processing.data.JSONObject;

/**
 * Entity
 */
public abstract class Entity {
    public float x, y;
    protected float width, height;

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

    public CollisionResult checkCollision(Entity other) {
        if (!collidesWith(other)) {
            return new CollisionResult(false, "none", 0, 0, null);
        }
        
        // Calculate overlaps from all four sides
        float overlapLeft = (x + width) - other.x;
        float overlapRight = (other.x + other.width) - x;
        float overlapTop = (y + height) - other.y;
        float overlapBottom = (other.y + other.height) - y;
        
        // Find minimum overlap (the side we collided from)
        float minOverlap = Math.min(
            Math.min(overlapLeft, overlapRight),
            Math.min(overlapTop, overlapBottom)
        );
        
        String side;
        float penX = 0, penY = 0;
        
        if (minOverlap == overlapLeft) {
            side = "left";
            penX = -overlapLeft;
        } else if (minOverlap == overlapRight) {
            side = "right";
            penX = overlapRight;
        } else if (minOverlap == overlapTop) {
            side = "top";
            penY = -overlapTop;
        } else {
            side = "bottom";
            penY = overlapBottom;
        }
        
        return new CollisionResult(true, side, penX, penY, other);
    }

    abstract public void draw(App app);
    abstract public void draw(App app, float scalar);
    abstract public JSONObject toJSONObject();
}
