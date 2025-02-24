package io.github.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.game.entity.*;
import io.github.game.physics.Velocity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level4 implements Level {
    private Player player;
    private Map<String, List<?>> entities;
    private Texture playerCharacter;
    private ArrayList<Wall> walls;
    private ArrayList<MovableBox> boxes;
    private End end;
    private Boolean completed;
    private Boolean reset;
    private Texture wallTexture;
    private Texture endTexture;
    private Texture crateTexture;

    @Override
    public HashMap<String, List<?>> getEntities() {
        return (HashMap<String, List<?>>) entities;
    }

    @Override
    public End getEnd() {
        return end;
    }


    @Override
    public void load() {
        entities = new HashMap<String, List<?>>();
        reset = false;
        completed = false;
        player = new Player(20, 20, 64,64);
        playerCharacter = new Texture("player.png");
        wallTexture = new Texture("wall.png");
        endTexture = new Texture("end.png");
        crateTexture = new Texture("crate.png");
        walls = new ArrayList<>();
        entities.put("walls", walls);
        walls.add(new Wall(0, 0, 1280, 20, wallTexture));
        walls.add(new Wall(0, 0, 20, 800, wallTexture));
        walls.add(new Wall(1000, 20, 500, 220, wallTexture));

        boxes = new ArrayList<>();
        entities.put("boxes", boxes);
        boxes.add(new MovableBox(500,20,100,100,crateTexture));


        end = new End(1200,240,64,64);

    }

    @Override
    public void update(float delta) {
        boolean jumping = false;
        Velocity velocity = player.getVelocity();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.updateVelocity(1f,velocity.getPlayerVelocityY());
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.updateVelocity(-1f,velocity.getPlayerVelocityY());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jumping = true;
        }

        player.move(delta,jumping,this);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    }

    @Override
    public void render(PolygonSpriteBatch batch) {
        batch.begin();
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(playerCharacter, player.x, player.y, player.getWidth(), player.getHeight());

        for (Wall wall : walls) {
            batch.draw(wall.getTexture(), wall.x, wall.y, wall.width, wall.height);
        }

        for (MovableBox box : boxes) {
            batch.draw(box.getTexture(), box.x, box.y, box.width, box.height);
        }

        batch.draw(endTexture, end.x, end.y, end.width, end.height);

        batch.end();

    }

    @Override
    public void reset() {
        reset = true;
    }

    @Override
    public Boolean checkReset() {
        return reset;
    }

    @Override
    public Boolean isCompleted() {
        return completed;
    }

    @Override
    public void complete() {
        completed = true;
    }
}
