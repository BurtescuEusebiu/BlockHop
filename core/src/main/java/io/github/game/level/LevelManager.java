package io.github.game.level;


import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

public class LevelManager {
    private Level currentLevel;
    private int currentLevelNumber;

    public LevelManager() {
        currentLevelNumber = 1;
        loadCurrentLevel();
    }

    private void loadCurrentLevel() {
        switch (currentLevelNumber) {
            case 1:
                currentLevel = new Level1();
                break;
            case 2:
                currentLevel = new Level2();
                break;
            case 3:
                currentLevel = new Level3();
                break;
            case 4:
                currentLevel = new Level4();
                break;
            case 5:
                currentLevel = new Level5();
                break;
            default:
                currentLevel = new Level1();
        }
        currentLevel.load();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level newLevel) {
        currentLevel.reset();
        currentLevel = newLevel;
        currentLevel.load();
    }

    public void update(float delta) {
        currentLevel.update(delta);
        if (currentLevel.isCompleted()) {
            currentLevelNumber++;
            loadCurrentLevel();
        }
        if(currentLevel.checkReset()) {
            loadCurrentLevel();
        }
    }

    public void render(PolygonSpriteBatch batch) {
        currentLevel.render(batch);
    }
}
