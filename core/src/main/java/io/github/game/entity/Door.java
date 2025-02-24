package io.github.game.entity;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class Door extends Wall {
    private Boolean state;
    private Texture texture1;
    private Texture texture2;


    public Door(int x, int y, int width, int height, Texture texture, Texture texture2) {
        super(x, y, width, height, texture);
        state = false;
        this.texture1 = texture;
        this.texture2 = texture2;
    }

    public void change(){
        texture = texture2;
        state = true;

    }

    public Boolean getState(){
        return state;
    }
}
