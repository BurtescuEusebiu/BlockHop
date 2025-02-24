package io.github.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Button extends Rectangle {
    private Texture texture;
    private Texture texture2;
    private Door door;

    public Button(int x, int y, int width, int height, Texture texture, Texture texture2, Door door) {
        super(x, y, width, height);
        this.texture = texture;
        this.texture2 = texture2;
        this.door = door;
    }

    public void press(){
        texture = texture2;
        door.change();
    }

    public Texture getTexture() {
        return texture;
    }
}
