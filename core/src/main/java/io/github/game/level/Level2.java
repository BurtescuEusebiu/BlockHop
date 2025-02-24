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

public class Level2 implements Level {
    private Player player;
    private Boolean completed;
    private Boolean reset;
    private Texture playerCharacter;
    private Texture spikeTexture;
    private Texture wallTexture;
    private Texture endTexture;
    private HashMap<String, List<?>> entities;
    private End end;
    private ArrayList<Wall> walls;
    private ArrayList<Spike> spikes;


    @Override
    public HashMap<String, List<?>> getEntities() {
        return entities;
    }

    @Override
    public End getEnd() {
        return end;
    }


    @Override
    public void load() {
        entities = new HashMap<>();
        completed = false;
        reset = false;
        player = new Player(20, 20, 64,64);
        playerCharacter = new Texture("player.png");
        spikeTexture = new Texture("spike.png");
        endTexture = new Texture("end.png");
        wallTexture = new Texture("wall.png");
        walls = new ArrayList<>();
        entities.put("walls", walls);
        walls.add(new Wall(0, 0, 1280, 20, wallTexture));
        walls.add(new Wall(0, 0, 20, 720, wallTexture));
        walls.add(new Wall(200, 20, 200, 75, wallTexture));
        walls.add(new Wall(562, 20, 200, 75, wallTexture));
        walls.add(new Wall(922, 20, 200, 75, wallTexture));
        walls.add(new Wall(1218, 20, 200, 170, wallTexture));


        spikes = new ArrayList<>();
        entities.put("spikes", spikes);
        spikes.add(new Spike(400,20,32,32, spikeTexture));
        spikes.add(new Spike(432,20,32,32, spikeTexture));
        spikes.add(new Spike(464,20,32,32, spikeTexture));
        spikes.add(new Spike(498,20,32,32, spikeTexture));
        spikes.add(new Spike(530,20,32,32, spikeTexture));

        spikes.add(new Spike(762, 20, 32, 32, spikeTexture));
        spikes.add(new Spike(794, 20, 32, 32, spikeTexture));
        spikes.add(new Spike(826, 20, 32, 32, spikeTexture));
        spikes.add(new Spike(858, 20, 32, 32, spikeTexture));
        spikes.add(new Spike(890, 20, 32, 32, spikeTexture));

        spikes.add(new Spike(1122, 20, 32, 32, spikeTexture));
        spikes.add(new Spike(1154, 20, 32, 32, spikeTexture));
        spikes.add(new Spike(1186, 20, 32, 32, spikeTexture));

        end = new End(1220,190,64,64);

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
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height);
        }

        for (Spike spike : spikes) {
            spike.draw(batch);
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
        completed=true;
    }
}
