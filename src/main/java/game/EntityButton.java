package game;

import game.Entities.*;

/**
 * EntityButton
 */
public class EntityButton {
    public int spriteWidth;
    public int spriteHeight;
    private String entityType;
    private Entity entity;
    private float x, y;
    private float size;

    public EntityButton(String entityType, float x, float y, float size) {
        this.entityType = entityType;
        this.x = x;
        this.y = y;
        this.size = size;
        this.entity = generateEntity(entityType, x, y);
    }

    private Entity generateEntity(String entityType, float x, float y) {
        Entity e;
        switch (entityType) {
            case "Ground1":
                e = new Ground1(x, y);
                spriteHeight = Ground1.spriteHeight;
                spriteWidth = Ground1.spriteWidth;
                break;
            case "Ground2":
                e = new Ground2(x, y);
                spriteHeight = Ground2.spriteHeight;
                spriteWidth = Ground2.spriteWidth;
                break;
            case "Platform1":
                e = new Platform1(x, y);
                spriteHeight = Platform1.spriteHeight;
                spriteWidth = Platform1.spriteWidth;
                break;
            case "Platform2":
                e = new Platform2(x, y);
                spriteHeight = Platform2.spriteHeight;
                spriteWidth = Platform2.spriteWidth;
                break;
            default:
                return null;
        }
        return e;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        entity.x = x;
        entity.y = y;
    }

    public void draw(App app) {
        entity.draw(app);
    }

    public void draw(App app, float scalar) {
        entity.draw(app, scalar);
    }

    public boolean contains(float px, float py) {
        return px >= x && px <= x + size && py >= y && py <= y + size;
    }

    public String getEntityType() {
        return entityType;
    }
}
