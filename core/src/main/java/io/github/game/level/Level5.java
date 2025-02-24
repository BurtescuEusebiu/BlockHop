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

public class Level5 implements Level {
    private Player player;
    private Boolean completed;
    private Boolean reset;
    private Texture playerCharacter;
    private Texture spikeTexture;
    private Texture wallTexture;
    private Texture endTexture;
    private Texture spitStoneTexture;
    private Texture projectileTexture;
    private HashMap<String, List<?>> entities;
    private End end;
    private ArrayList<Wall> walls;
    private ArrayList<Spike> spikes;
    private ArrayList<Spitstone> spitstones;
    private ArrayList<Projectile> projectiles;

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
        projectileTexture = new Texture("fireball.png");
        spitStoneTexture = new Texture("turret.png");
        walls = new ArrayList<>();
        entities.put("walls", walls);
        walls.add(new Wall(0, 0, 1280, 20, wallTexture));
        walls.add(new Wall(0, 0, 20, 720, wallTexture));
        walls.add(new Wall(1218, 20, 200, 170, wallTexture));

        spitstones = new ArrayList<>();
        entities.put("spitstones", spitstones);
        spitstones.add(new Spitstone(1118,20,100,100,spitStoneTexture,new Velocity(-100f,0f),0.5f));

        projectiles = new ArrayList<>();
        entities.put("projectiles", projectiles);

        spikes = new ArrayList<>();
        entities.put("spikes", spikes);
        spikes.add(new Spike(400,20,100,100, spikeTexture));
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
            batch.draw(wall.getTexture(), wall.x, wall.y, wall.width, wall.height);
        }

        if(projectiles != null && !projectiles.isEmpty()) {
            for (Projectile projectile : projectiles) {
                batch.draw(projectileTexture, projectile.x, projectile.y, projectile.width, projectile.height);
            }
        }

        if(spitstones != null && !spitstones.isEmpty()) {
            for (Spitstone spitstone : spitstones) {
                batch.draw(spitstone.getTexture(),spitstone.x, spitstone.y, spitstone.getWidth(), spitstone.getHeight());
            }
        }

        if(spikes != null && !spikes.isEmpty()) {
            for (Spike spike : spikes) {
                spike.draw(batch);
            }
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
