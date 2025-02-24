package io.github.game.entity;

import com.badlogic.gdx.graphics.Texture;
import io.github.game.physics.Velocity;

public class Spitstone extends Wall{
    private Velocity velocity = new Velocity(50,50);
    private float fireCooldown;
    private float fireCooldownTime;
    public Spitstone(float x, float y, float width, float height, Texture texture, Velocity velocity, float fireCooldownTime) {
        super(x, y, width, height, texture);
        this.velocity = velocity;
        this.fireCooldown = 0f;
        this.fireCooldownTime = fireCooldownTime;
    }

    public Projectile fire(float delta){
        if(fireCooldown > 0){
            fireCooldown-=delta;
        }
        if(fireCooldown <=0 ){
            fireCooldown = fireCooldownTime;
            return new Projectile(x + Math.round(width/2) - 8,y + Math.round(height/2) - 8,16,16, velocity);
        }
        return null;
    }


}
