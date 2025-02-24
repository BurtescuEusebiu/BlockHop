package io.github.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.game.physics.Velocity;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class Projectile extends Rectangle {
    private Velocity velocity;
    private Boolean exists;
    public Projectile(float x, float y, float width, float height, Velocity velocity) {
        super(x, y, width, height);
        this.velocity = velocity;
        this.exists = true;
    }

    public Boolean isExists() {
        return exists;
    }

    public void collision(float delta, ArrayList<Wall> walls, ArrayList<MovableBox> boxes, ArrayList<Button> buttons, ArrayList<Spike> spikes) {
        float newX = Math.round(x + velocity.getPlayerVelocityX() * delta);
        float newY = Math.round(y + velocity.getPlayerVelocityY() * delta);


        if (walls != null) {
            if(newX != x) {
                for (Wall wall : walls) {
                    if (!(wall instanceof Door && ((Door) wall).getState())) {
                        if (new Rectangle(newX, y, width, height).overlaps(wall)) {
                            this.exists = false;
                            return;
                        }
                    }
                }
            }

            if(newY != y) {
                for (Wall wall : walls) {
                    if (!(wall instanceof Door && ((Door) wall).getState())) {
                        if (new Rectangle(x, newY, width, height).overlaps(wall)) {
                            this.exists = false;
                            return;
                        }
                    }
                }
            }
        }

        if (boxes != null) {
            for (MovableBox box : boxes) {
                if (new Rectangle(newX, y, width, height).overlaps(box)) {
                    this.exists = false;
                    return;
                }
                if (new Rectangle(x, newY, width, height).overlaps(box)) {
                    this.exists = false;
                    return;
                }
            }
        }

        if (buttons != null) {
            for (Button button : buttons) {
                if (new Rectangle(newX, y, width, height).overlaps(button)) {
                    button.press();
                    this.exists = false;
                    return;
                }

                if (new Rectangle(x, newY, width, height).overlaps(button)) {
                    button.press();
                    this.exists = false;
                    return;
                }
            }
        }

            if (spikes != null) {
                for (Spike spike : spikes) {
                    Polygon spikePolygon = spike.getTriangle();

                    Polygon playerPolygonX = new Polygon(new float[] {
                        newX, y,
                        newX + width, y,
                        newX + width, y + height,
                        newX, y + height
                    });

                    if (Intersector.overlapConvexPolygons(spikePolygon, playerPolygonX)) {
                        this.exists = false;
                        return;
                    }

                    Polygon playerPolygonY = new Polygon(new float[] {
                        x, newY,
                        x + width, newY,
                        x + width, newY + height,
                        x, newY + height
                    });

                    if (Intersector.overlapConvexPolygons(spikePolygon, playerPolygonY)) {
                        this.exists = false;
                        return;
                    }
                }
            }

        x = newX;
        y = newY;
    }
}
