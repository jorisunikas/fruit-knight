package game;

/**
 * Camera
 */
public class Camera {
    private App app;
    private float x, y;
    private float targetX, targetY;
    private float smoothness;
    private float zoom;
    private final float zoomMin = 0.5f;
    private final float zoomMax = 8f;

    // Optional bounds
    private boolean useBounds;
    private float minX, minY, maxX, maxY;

    public Camera(App app) {
        this.app = app;
        this.x = 0;
        this.y = 0;
        this.targetX = 0;
        this.targetY = 0;
        this.smoothness = 0.15f;
        this.zoom = 1.0f;
        this.useBounds = false;
    }

    public Camera(App app, float smoothness, float zoom, Player p) {
        this(app);
        this.smoothness = smoothness;
        this.zoom = zoom;
        followPlayer(p);
    }

    public Camera(App app, boolean editMode) {
        this(app);
        this.smoothness = 0;
    }

    /**
     * Set camera bounds (prevents showing outside level)
     * 
     * @param minX Minimum X (usually 0)
     * @param minY Minimum Y (usually 0)
     * @param maxX Maximum X (levelWidth - screenWidth)
     * @param maxY Maximum Y (levelHeight - screenHeight)
     */
    public void setBounds(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.useBounds = true;
    }

    /**
     * Follow a target position (like player center)
     */
    public void follow(float targetX, float targetY) {
        // Center camera on target
        this.targetX = targetX - app.width / 2;
        this.targetY = targetY - app.height / 2;
    }

    /**
     * Follow a player entity
     */
    public void followPlayer(Player player) {
        float centerX = player.x + player.width / 2;
        float centerY = player.y + player.height / 2;
        follow(centerX, centerY);
    }

    /**
     * Update camera position (call every frame)
     */
    public void update() {
        // Smooth interpolation
        x += (targetX - x) * smoothness;
        y += (targetY - y) * smoothness;

        // Apply bounds if enabled
        if (useBounds) {
            x = constrain(x, minX, maxX);
            y = constrain(y, minY, maxY);
        }
    }

    /**
     * Apply camera transformation (call before drawing world)
     */
    public void apply() {
        app.translate(app.width / 2, app.height / 2); // Move to screen center
        app.scale(zoom); // Apply zoom
        app.translate(-x - app.width / 2, -y - app.height / 2); // Apply camera offset
    }

    /**
     * Begin camera view
     */
    public void begin() {
        app.pushMatrix();
        apply();
    }

    /**
     * End camera view
     */
    public void end() {
        app.popMatrix();
    }

    /**
     * Convert screen coordinates to world coordinates
     */
    public float screenToWorldX(float screenX) {
        return screenX + x;
    }

    public float screenToWorldY(float screenY) {
        return screenY + y;
    }

    /**
     * Convert world coordinates to screen coordinates
     */
    public float worldToScreenX(float worldX) {
        return worldX - x;
    }

    public float worldToScreenY(float worldY) {
        return worldY - y;
    }

    // Getters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // Setters
    public void setSmoothness(float smoothness) {
        this.smoothness = constrain(smoothness, 0.01f, 1.0f);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
    }

    private float constrain(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setZoom(float zoom) {
        this.zoom = constrain(zoom, zoomMin, zoomMax);
    }

    public void adjustZoom(float delta) {
        setZoom(zoom + delta);
    }

    public void panWithMouse(float dx, float dy) {
        targetX -= dx / zoom;
        targetY -= dy / zoom;
        
        x = targetX;
        y = targetY;
    }
}
