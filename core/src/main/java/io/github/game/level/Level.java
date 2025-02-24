package io.github.game.level;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import io.github.game.entity.End;
import java.util.HashMap;
import java.util.List;

public interface Level {
    HashMap<String, List<?>> getEntities();
    End getEnd();
    void load();
    void update(float delta);
    void render(PolygonSpriteBatch batch);
    void reset();
    Boolean checkReset();
    Boolean isCompleted();
    void complete();
}
