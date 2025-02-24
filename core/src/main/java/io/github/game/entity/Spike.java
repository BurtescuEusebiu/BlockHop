package io.github.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Spike {
    private Polygon triangle;
    private PolygonSprite polygonSprite;

    public Spike(float x, float y, float width, float height, Texture texture) {
        float[] vertices = new float[]{
            0, 0,           // Bottom-left
            width, 0,       // Bottom-right
            width / 2, height // Top-center
        };

        short[] triangles = new short[]{0, 1, 2}; // Triangle indices

        // Create the polygon for collision detection
        this.triangle = new Polygon(vertices);
        this.triangle.setPosition(x, y);

        // Ensure the texture is scaled properly
        TextureRegion textureRegion = new TextureRegion(texture);
        PolygonRegion polyRegion = new PolygonRegion(textureRegion, vertices, triangles);

        this.polygonSprite = new PolygonSprite(polyRegion);
        this.polygonSprite.setSize(width, height);  // Scale the sprite
        this.polygonSprite.setPosition(x, y);
    }

    public Polygon getTriangle() {
        return triangle;
    }

    public void draw(PolygonSpriteBatch batch) {
        polygonSprite.draw(batch);
    }
}
