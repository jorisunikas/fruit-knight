package game;

/**
 * LevelEditor
 */
public class LevelEditor {
    private App app;
    private Camera camera;
    private Menu menu;
    private Level level;

    private int gridSize = 16;
    private boolean snapToGrid = true;

    public LevelEditor(App app, Camera camera, Level level) {
        this.app = app;
        this.level = level;
        this.camera = camera;
        this.menu = new Menu(app, app.width - 242, 32);
    }

    public void draw() {
        if (snapToGrid)
            drawGrid();
        if (menu.hasSelected())
            drawPlacementPreview();

    }

    public void drawMenu() {
        menu.draw();
    }

    private void drawPlacementPreview() {
        float worldX = camera.screenToWorldX(app.mouseX);
        float worldY = camera.screenToWorldY(app.mouseY);

        if (snapToGrid) {
            worldX = (int) (worldX / gridSize) * gridSize;
            worldY = (int) (worldY / gridSize) * gridSize;
        }

        app.fill(255, 255, 0, 100);
        app.stroke(255, 255, 0);
        app.strokeWeight(2);
        app.rect(worldX, worldY, gridSize, gridSize);
        app.noStroke();

    }

    public void handleMousePressed(float mouseX, float mouseY) {
        if (menu.containsPoint(mouseX, mouseY)) {
            menu.handleMousePressed(mouseX, mouseY);
            return;
        }

        if (menu.hasSelection()) {
            placeEntity(mouseX, mouseY);
        }
    }

    private void placeEntity(float screenX, float screenY) {

        float worldX = camera.screenToWorldX(screenX);
        float worldY = camera.screenToWorldY(screenY);

        if (snapToGrid) {
            worldX = (int) (worldX / gridSize) * gridSize;
            worldY = (int) (worldY / gridSize) * gridSize;
        }

        String entityType = menu.getSelectedEntityType();
        Entity newEntity = createEntity(entityType, worldX, worldY);

        if (newEntity != null) {
            level.addEntity(newEntity);
        }
    }

    private Entity createEntity(String type, float x, float y) {
        switch (type) {
            case "Ground1":
                return new game.Entities.Ground1(x, y);
            case "Ground2":
                return new game.Entities.Ground2(x, y);
            case "Platform1":
                return new game.Entities.Platform1(x, y);
            case "Platform2":
                return new game.Entities.Platform2(x, y);
            case "Fruit":
                return new game.Entities.Fruit(x, y);
            default:
                return null;
        }
    }

    public void toggleGrid() {
        snapToGrid = !snapToGrid;
    }

    public Menu getMenu() {
        return menu;
    }

    private void drawGrid() {
        app.stroke(100, 100, 100, 50);
        app.strokeWeight(1 / camera.getZoom());

        float gridStart = -2048;
        float gridEnd = 2048;

        for (float x = gridStart; x <= gridEnd; x += gridSize) {
            app.line(x, gridStart, x, gridEnd);
            app.line(gridStart, x, gridEnd, x);
        }

        for (float y = gridStart; y <= gridEnd; y += gridSize) {
        }

        app.noStroke();
    }

    public void handleMouseRightClick(float mouseX, float mouseY) {
        if (menu.containsPoint(mouseX, mouseY)) {
            return;
        }

        float worldX = camera.screenToWorldX(mouseX);
        float worldY = camera.screenToWorldY(mouseY);

        removeEntityAt(worldX, worldY);
    }

    private void removeEntityAt(float worldX, float worldY) {
        for (int i = level.getEntities().size() - 1; i >= 0; i--) {
            Entity entity = level.getEntities().get(i);

            if (worldX >= entity.x && worldX <= entity.x + entity.width &&
                    worldY >= entity.y && worldY <= entity.y + entity.height) {

                level.removeEntity(entity);
                return;
            }
        }
    }

    public void saveLevel() {
        level.saveToJSON();
        System.out.println(String.format("Level saved as %s.json", level.name));
    }

    public void setLevel(Level l) {
        this.level = l;
    }
}
