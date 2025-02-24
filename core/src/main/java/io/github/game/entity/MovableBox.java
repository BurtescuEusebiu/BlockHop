package io.github.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MovableBox extends Player {
    private Texture texture;
    public MovableBox(float x, float y, float width, float height, Texture texture) {
        super(x, y, width, height);
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
