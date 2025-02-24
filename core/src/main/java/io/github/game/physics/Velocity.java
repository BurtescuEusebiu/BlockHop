package io.github.game.physics;

public class Velocity {
    private float playerVelocityX;
    private float playerVelocityY;

    public Velocity() {};
    public Velocity(float playerVelocityX, float playerVelocityY) {
        this.playerVelocityX = playerVelocityX;
        this.playerVelocityY = playerVelocityY;
    }

    public float getPlayerVelocityX() {
        return playerVelocityX;
    }

    public float getPlayerVelocityY() {
        return playerVelocityY;
    }

    public void update(float x, float y) {
        playerVelocityX = x;
        playerVelocityY = y;
    }

    public void setPlayerVelocityX(float playerVelocityX) {
        this.playerVelocityX = playerVelocityX;
    }

    public void setPlayerVelocityY(float playerVelocityY) {
        this.playerVelocityY = playerVelocityY;
    }


}
