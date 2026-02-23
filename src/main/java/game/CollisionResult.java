package game;

/**
 * CollisionResult
 */
class CollisionResult {
    public boolean collided;
    public String side;           
    public float penetrationX;   
    public float penetrationY;  
    public Entity collidedWith;
    
    public CollisionResult(boolean collided, String side, float penX, float penY, Entity entity) {
        this.collided = collided;
        this.side = side;
        this.penetrationX = penX;
        this.penetrationY = penY;
        this.collidedWith = entity;
    }
}
