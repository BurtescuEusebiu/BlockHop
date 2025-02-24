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

public class Level1 implements Level {
    private Player player;
    private Map<String, List<?>> entities;
    private Texture playerCharacter;
    private ArrayList<Wall> walls;
    private End end;
    private Boolean completed;
    private Boolean reset;
    private Texture wallTexture;
    private Texture endTexture;


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
        completed = false;
        reset = false;
        player = new Player(20, 20, 64,64);
        playerCharacter = new Texture("player.png");
        wallTexture = new Texture("wall.png");
        endTexture = new Texture("end.png");
        walls = new ArrayList<>();
        entities.put("walls", walls);
        walls.add(new Wall(0, 0, 500, 20, wallTexture));
        walls.add(new Wall(0, 0, 20, 600, wallTexture));
        walls.add(new Wall(500, 100, 100, 20, wallTexture));
        walls.add(new Wall(700, 200, 100, 20, wallTexture));
        walls.add(new Wall(900, 300, 100, 20, wallTexture));
        walls.add(new Wall(700, 400, 100, 20, wallTexture));
        walls.add(new Wall(500, 500, 100, 20, wallTexture));
        walls.add(new Wall(300, 600, 100, 20, wallTexture));
        walls.add(new Wall(0, 600, 200, 20, wallTexture));
        end = new End(0,620,64,64);

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
