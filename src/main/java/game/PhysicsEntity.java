package game;

import java.util.ArrayList;

import game.Entities.Fruit;

/**
 * PhysicsEntity
 */
public abstract class PhysicsEntity extends Entity {
    protected App app;
    protected float velocityX, velocityY;
    protected float gravity;
    protected float friction;
    protected float maxSpeedX;
    protected float maxSpeedY;
    protected float velocityThreshold;
    protected boolean onGround;
    protected boolean onGround1;
    protected boolean onGround2;

    public PhysicsEntity(App app, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.app = app;
        this.velocityX = 0;
        this.velocityY = 0;
        this.gravity = 0.42f;
        this.friction = 0.6f;
        this.maxSpeedX = 3.0f;
        this.maxSpeedY = 16.0f;
        this.velocityThreshold = 0.55f;
        this.onGround = false;
        this.onGround1 = false;
        this.onGround2 = false;
    }

    public void applyPhysics(ArrayList<Entity> solids) {
        onGround2 = onGround1;
        onGround1 = onGround;

        if (onGround)
            velocityX *= friction;

        if (!onGround && !onGround1 && !onGround2)
            velocityX *= 0.89f;

        if (!onGround)
            velocityY += gravity;

        if (App.abs(velocityX) <= velocityThreshold)
            velocityX = 0;

        velocityX = constrain(velocityX, -maxSpeedX, maxSpeedX);
        velocityY = constrain(velocityY, -maxSpeedY, maxSpeedY);

        x += velocityX;
        y += velocityY;

        resolveVerticalCollisions(solids);
        resolveHorizontalCollisions(solids);

        if (onGround) {
            y = Math.round(y);
        }
    }

    protected float constrain(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private void resolveHorizontalCollisions(ArrayList<Entity> solids) {
        for (Entity solid : solids) {
            if(solid instanceof Fruit) continue;
            CollisionResult result = checkCollision(solid);
            if (result.collided) {
                x += result.penetrationX;
                velocityX = 0;
            }
        }
    }

    private void resolveVerticalCollisions(ArrayList<Entity> solids) {
        onGround = false;

        for (Entity solid : solids) {
            if(solid instanceof Fruit) continue;
            if (collidesWith(solid)) {
                // Calculate horizontal overlap
                float leftOverlap = (x + width) - solid.x;
                float rightOverlap = (solid.x + solid.width) - x;
                float minHorizontalOverlap = Math.min(leftOverlap, rightOverlap);

                // Only handle as vertical collision if significantly overlapping horizontally
                // (at least half the player's width)
                if (minHorizontalOverlap > width * 0.2f) {
                    if (velocityY > 0) {
                        // Falling - land on top
                        y = solid.y - height;
                        velocityY = 0;
                        onGround = true;
                    } else if (velocityY < 0) {
                        // Moving up - hit ceiling
                        y = solid.y + solid.height;
                        velocityY = 0;
                    }
                }
            }
        }
    }

    public boolean isOnGround() {
        return onGround;
    }
}
