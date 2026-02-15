package game;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

/**
 * Player
 */
public class Player extends PhysicsEntity {
    private static PImage texture;
    private static PImage texturePack;
    private final static int spriteWidth = 13;
    private final static int spriteHeight = 19;
    private App app;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean jumpPressed = false;

    private float acceleration = 0.8f;
    private float constanAcc = 0.0f;
    private float jumpStrength = 4.8f;

    Player(App app, float x, float y) {
        super(x, y, spriteWidth, spriteHeight);

        this.app = app;
    }

    public void update(ArrayList<Entity> solids) {
        // Handle movement
        if (leftPressed) {
            velocityX -= acceleration + constanAcc;
        }
        if (rightPressed) {
            velocityX += acceleration + constanAcc;
        }

        // Handle jump
        if (jumpPressed && onGround) {
            velocityY = -jumpStrength;
            jumpPressed = false;
            onGround = false;
        }

        // Apply physics
        applyPhysics(solids);
    }

    public void draw(App app) {
        showPhysics();
        app.image(texture, x, y, spriteWidth, spriteHeight);
    }

    public void handleKeyPressed(char k, int kc) {
        if (k == 'a' || k == 'A' || kc == PApplet.LEFT)
            leftPressed = true;
        if (k == 'd' || k == 'D' || kc == PApplet.RIGHT)
            rightPressed = true;
        if (k == ' ' || k == 'w' || k == 'W' || kc == PApplet.UP)
            jumpPressed = true;
    }

    public void handleKeyReleased(char k, int kc) {
        if (k == 'a' || k == 'A' || kc == PApplet.LEFT)
            leftPressed = false;
        if (k == 'd' || k == 'D' || kc == PApplet.RIGHT)
            rightPressed = false;
    }

    public static void loadTexture(String path, App app) {
        texturePack = app.loadImage(path);
        Player.texture = texturePack.get(9, 9, spriteWidth, spriteHeight);
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.setFloat("x", x);
        obj.setFloat("y", y);
        obj.setString("class", "Player");
        return obj;
    }

    private void showPhysics() {
        app.fill(0, 0, 120);
        app.text(String.format("posX: %3f, posY: %3f\nvelX: %3f, velY: %3f\ngrnd: %b", x, y, velocityX, velocityY, onGround), 10, 10);

    }
}
