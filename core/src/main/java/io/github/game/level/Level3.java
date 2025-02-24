package io.github.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.game.entity.*;
import io.github.game.physics.Velocity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Level3 implements Level {
    private Player player;
    private Boolean completed;
    private Boolean reset;
    private Texture playerCharacter;
    private Texture spikeTexture;
    private Texture wallTexture;
    private Texture endTexture;
    private Texture doorOpenTexture;
    private Texture doorClosedTexture;
    private Texture buttonTextureGreen;
    private Texture buttonTextureRed;
    private ArrayList<Wall> walls;
    private ArrayList<Spike> spikes;
    private ArrayList<Button> buttons;
    private HashMap<String, List<?>> entities;
    private End end;


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
        doorOpenTexture = new Texture("doorOpen.png");
        doorClosedTexture = new Texture("doorClosed.png");
        buttonTextureGreen = new Texture("buttonGreen.png");
        buttonTextureRed = new Texture("buttonRed.png");
        walls = new ArrayList<>();
        entities.put("walls", walls);
        walls.add(new Wall(0, 0, 1280, 20, wallTexture));
        walls.add(new Wall(0, 0, 20, 720, wallTexture));
        walls.add(new Wall(1260,20,20,720, wallTexture));
        walls.add(new Wall(1100,120,270,20, wallTexture));
        walls.add(new Wall(0,450,200,20, wallTexture));
        walls.add(new Wall(250,350,200,20, wallTexture));
        walls.add(new Wall(630,500,200,20, wallTexture));
        walls.add(new Wall(900,200,100,20, wallTexture));
        walls.add(new Wall(1100,300,100,20, wallTexture));
        walls.add(new Wall(900,400,100,20, wallTexture));

        buttons = new ArrayList<>();
        entities.put("buttons", buttons);
        Door door = new Door(1100,14,50,110, doorClosedTexture,doorOpenTexture);
        buttons.add(new Button(40, 470, 18, 10, buttonTextureRed,buttonTextureGreen,door));
        walls.add(door);


        end = new End(1170,20,64,64);

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

        if(walls != null) {
            for (Wall wall : walls) {
                batch.draw(wall.getTexture(), wall.x, wall.y, wall.width, wall.height);
            }
        }


        if(spikes != null) {
            for (Spike spike : spikes) {
                spike.draw(batch);
            }
        }

        if(buttons != null) {
            for (Button button : buttons) {
                batch.draw(button.getTexture(), button.x, button.y, button.width, button.height);
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
