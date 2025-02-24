package io.github.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import io.github.game.level.LevelManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private LevelManager levelManager;
    private PolygonSpriteBatch batch;

    @Override
    public void create() {
        batch = new PolygonSpriteBatch();
        levelManager = new LevelManager();
    }

    @Override
    public void render() {
        levelManager.update(Gdx.graphics.getDeltaTime());
        levelManager.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
