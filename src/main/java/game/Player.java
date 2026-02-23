package game;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

/**
 * Player
 */
public class Player extends PhysicsEntity {
    private static PImage texturePack;

    public final static int spriteWidth = 14;
    public final static int spriteHeight = 19;
    private PImage[] idleFrames;
    private PImage[] runFrames;

    private PImage[] currentAnimation;
    private int currentFrame = 0;
    private int frameCounter = 0;
    private int frameDelay = 16;

    private enum State {
        IDLE, RUNNING
    }

    private State currentState;
    private boolean facingRight = true;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean jumpPressed = false;
    private float acceleration = 0.8f;
    private float constanAcc = 0.0f;
    private float jumpStrength = 4.8f;

    public Player(App app, float x, float y) {
        super(app, x, y, spriteWidth, spriteHeight);

        currentFrame = 0;
        frameCounter = 0;
        frameDelay = 6;
        currentState = State.IDLE;
    }

    public Player(App app) {
        this(app, 0, 0);
    }

    public void update(ArrayList<Entity> solids) {
        if (leftPressed) {
            velocityX -= acceleration + constanAcc;
            facingRight = false;
        }
        if (rightPressed) {
            velocityX += acceleration + constanAcc;
            facingRight = true;
        }

        if (jumpPressed && onGround) {
            velocityY = -jumpStrength;
            jumpPressed = false;
            onGround = false;
        }

        applyPhysics(solids);
        updateAnimation();
    }

    public void draw(App app) {
        app.pushMatrix();
        if (!facingRight) {
            app.translate(x + spriteWidth, y);
            app.scale(-1, 1);
            app.image(currentAnimation[currentFrame], 0, 0, spriteWidth, spriteHeight);
        } else {
            app.image(currentAnimation[currentFrame], x, y, spriteWidth, spriteHeight);
        }
        app.popMatrix();
    }

    public void draw(App app, float scalar) {
        draw(app);
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
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.setFloat("x", x);
        obj.setFloat("y", y);
        obj.setString("class", "Player");
        return obj;
    }

    public void showDebugMovement(float x, float y) {
        app.text(String.format("posX: %3f, posY: %3f\nvelX: %3f, velY: %3f\nGround: %b", this.x, this.y, velocityX,
                velocityY,
                onGround), x, y);
    }

    public void loadAnimations() {

        idleFrames = new PImage[4];
        for (int i = 0; i < idleFrames.length; i++) {
            idleFrames[i] = texturePack.get(9 + 32 * i, 9, spriteWidth, spriteHeight);
        }

        System.out.println(idleFrames.length);
        runFrames = new PImage[12];
        for (int j = 0; j < 2; j++)
            for (int i = 0; i < runFrames.length / 2; i++) {
                runFrames[j * 6 + i] = texturePack.get(8 + 32 * i, 74 + 32 * j, spriteWidth, spriteHeight);
            }
        currentAnimation = idleFrames;
    }

    private void updateAnimation() {
        State previousState = currentState;

        if (velocityX != 0)
            currentState = State.RUNNING;
        else
            currentState = State.IDLE;

        if (currentState != previousState) {
            currentFrame = 0;
            frameCounter = 0;

            switch (currentState) {
                case IDLE:
                    currentAnimation = idleFrames;
                    break;
                case RUNNING:
                    currentAnimation = runFrames;
                    break;
            }
        }
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            currentFrame = (currentFrame + 1) % currentAnimation.length;
        }

    }
}
