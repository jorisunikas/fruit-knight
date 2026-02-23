package game;

import java.util.ArrayList;

/**
 * Menu
 */
public class Menu {
    private App app;

    private final int columns = 4;
    private final int buttonSize = 32;
    private final int defaultTileSize = 16;

    private float x, y;
    private float width, height;
    private float padding = 10;

    ArrayList<EntityButton> buttons;
    private EntityButton selectedButton;

    public Menu(App app, float x, float y) {
        this.app = app;
        this.x = x;
        this.y = y;
        buttons = new ArrayList<>();
        buttons.add(new EntityButton("Ground1", 0, 0, buttonSize));
        buttons.add(new EntityButton("Ground2", 0, 0, buttonSize));
        buttons.add(new EntityButton("Platform2", 0, 0, buttonSize));
        buttons.add(new EntityButton("Platform1", 0, 0, buttonSize));
        buttons.add(new EntityButton("Fruit", 0, 0, buttonSize));

        updateButtonPositions();
    }

    private void updateButtonPositions() {
        int curWidth = (int)padding, curHeight = (int)padding, maxHeight = 0, maxWidth = 0;
        for (int i = 0; i < buttons.size(); i++) {
            if(i % columns == 0){
                if(curWidth > maxWidth) maxWidth = curWidth;
                curWidth = (int)padding;
                curHeight += maxHeight + padding;
                maxHeight = 0;
            }
            float tileWidth = constrain(buttonSize/defaultTileSize * buttons.get(i).spriteWidth, buttonSize, 4*buttonSize);
            float tileHeight = constrain(buttonSize/defaultTileSize * buttons.get(i).spriteHeight, buttonSize, 4*buttonSize);
            if(tileHeight > maxHeight) maxHeight = (int)tileHeight;

            float posX = x + curWidth;
            float posY = y + curHeight;

            curWidth += tileWidth + padding;

            buttons.get(i).setPosition(posX, posY);
        }
        if(curWidth > maxWidth) maxWidth = curWidth;
        curHeight += maxHeight + padding;
        this.height = curHeight;
        this.width = maxWidth;
    }

    public boolean hasSelected() {
        return false;
    }

    public void draw() {
        app.stroke(48, 47, 44);
        app.fill(111);
        app.rect(x, y, width, height);

        for (EntityButton entityButton : buttons) {
            entityButton.draw(app, (float)buttonSize/defaultTileSize);
        }
    }

    public void handleMousePressed(float mouseX, float mouseY) {
        for (EntityButton btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                selectedButton = btn;
                return;
            }
        }
    }

    public boolean containsPoint(float px, float py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    public String getSelectedEntityType() {
        return selectedButton != null ? selectedButton.getEntityType() : null;
    }
    
    public boolean hasSelection() {
        return selectedButton != null;
    }
    
    public void clearSelection() {
        selectedButton = null;
    }

    private float constrain(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
