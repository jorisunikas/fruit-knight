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

    public Camera(App app) {
        this.app = app;
        this.x = 0;
        this.y = 0;
        this.targetX = 0;
        this.targetY = 0;
        this.smoothness = 0.15f;
        this.zoom = 1.0f;
    }

    public Camera(App app, float smoothness, float zoom) {
        this(app);
        this.smoothness = smoothness;
        this.zoom = zoom;
    }

    public void follow(float targetX, float targetY) {
        this.targetX = targetX - app.width / 2;
        this.targetY = targetY - app.height / 2;
    }

    public void followPlayer(Player player) {
        float centerX = player.x + player.width / 2;
        float centerY = player.y + player.height / 2;
        follow(centerX, centerY);
    }

    public void update() {
        x += (targetX - x) * smoothness;
        y += (targetY - y) * smoothness;
    }

    public void apply() {
        app.translate(app.width / 2, app.height / 2);
        app.scale(zoom);
        app.translate(-x - app.width / 2, -y - app.height / 2);
    }

    public void begin() {
        app.pushMatrix();
        apply();
    }

    public void end() {
        app.popMatrix();
    }

    public float screenToWorldX(float screenX) {
        return (screenX - app.width / 2) / zoom + x + app.width / 2;
    }

    public float screenToWorldY(float screenY) {
        return (screenY - app.height / 2) / zoom + y + app.height / 2;
    }

    public float worldToScreenX(float worldX) {
        return (worldX - x) * zoom + app.width / 2;
    }

    public float worldToScreenY(float worldY) {
        return (worldY - y) * zoom + app.height / 2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

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

    public float getZoom() {
        return zoom;
    }
}
