package game;

/**
 * CollisionResult
 */
class CollisionResult {
    public boolean collided;
    public String side;           // "top", "bottom", "left", "right", "none"
    public float penetrationX;    // How much to push back on X axis
    public float penetrationY;    // How much to push back on Y axis
    public Entity collidedWith;   // Which entity we collided with
    
    public CollisionResult(boolean collided, String side, float penX, float penY, Entity entity) {
        this.collided = collided;
        this.side = side;
        this.penetrationX = penX;
        this.penetrationY = penY;
        this.collidedWith = entity;
    }
}
