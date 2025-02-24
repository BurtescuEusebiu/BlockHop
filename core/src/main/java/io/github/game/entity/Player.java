package io.github.game.entity;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import io.github.game.level.Level;
import io.github.game.physics.Velocity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Player extends Rectangle {
    private Velocity velocity;
    private Float gravity = -98f;
    private Float friction = -50f;
    private float jumpVelocity = 2000f;
    private float playerSpeed = 500f;
    private float pushSpeed = -200f;
    private float jumpCooldown = 0f;
    private final float jumpCooldownTime = 0.26f;
    private End end;

    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Velocity(0, 0);
    }

    public void updateVelocity(float x, float y) {
        if(x == 1f) x = playerSpeed;
        else if (x == -1f) x = -playerSpeed;
        if(y == 1f) y = playerSpeed;
        else if (y == -1f) y = -playerSpeed;

        velocity.update(x, y);
    }

    public Velocity getVelocity() {
        return velocity;
    }

    private boolean isGrounded(ArrayList<Wall> walls, ArrayList<MovableBox> boxes) {
        Rectangle playerRect = new Rectangle(x, y - 1, width, height);

        for (Wall wall : walls) {
            if (playerRect.overlaps(wall)) {
                return true;
            }
        }

        if(boxes!=null && boxes.size()>0) {
            for (MovableBox box : boxes) {
                if ((playerRect.x != box.getX() || playerRect.y + 1 != box.getY() || playerRect.width != box.getWidth() || playerRect.height != box.getHeight()) && playerRect.overlaps(box)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void collision(Player object, ArrayList<Wall> walls, ArrayList<Button> buttons, ArrayList<MovableBox> boxes, float delta, Level level, Boolean jump) {
        float newX = Math.round(object.getX() + object.getVelocity().getPlayerVelocityX() * delta);
        float newY = Math.round(object.getY() + object.getVelocity().getPlayerVelocityY() * delta);
        float width = object.getWidth();
        float height = object.getHeight();
        boolean grounded = object.isGrounded(walls, boxes);

        if (jumpCooldown > 0) {
            jumpCooldown -= delta;
        }

        if (grounded && jump && jumpCooldown <=0) {
            object.getVelocity().update(object.getVelocity().getPlayerVelocityX(), jumpVelocity);
            jumpCooldown = jumpCooldownTime;
        }

        if (!grounded) {
            object.getVelocity().update(object.getVelocity().getPlayerVelocityX(), object.getVelocity().getPlayerVelocityY() + gravity);
        }

        if(grounded && !jump) {
            object.getVelocity().update(object.getVelocity().getPlayerVelocityX() + Math.signum(object.getVelocity().getPlayerVelocityX()) * friction, 0);
        }

        if (walls != null) {
            if(newX != object.getX()) {
                for (Wall wall : walls) {
                    if (!(wall instanceof Door && ((Door) wall).getState())) {
                        if (new Rectangle(newX, object.getY(), width, height).overlaps(wall)) {
                            if (object.getVelocity().getPlayerVelocityX() > 0) {
                                newX = wall.x - width;
                                break;
                            } else if (object.getVelocity().getPlayerVelocityX() < 0) {
                                newX = wall.x + wall.width;
                                break;
                            }
                        }
                    }
                }
            }

            if(newY != object.getY()) {
                for (Wall wall : walls) {
                    if (!(wall instanceof Door && ((Door) wall).getState())) {
                        if (new Rectangle(object.getX(), newY, width, height).overlaps(wall)) {
                            if (object.getVelocity().getPlayerVelocityY() >= 0) {
                                newY = wall.y - height;
                                if(newY < 0)
                                    newY = wall.y + wall.getHeight();
                                object.getVelocity().update(object.getVelocity().getPlayerVelocityX(), gravity);
                                break;
                            } else if (object.getVelocity().getPlayerVelocityY() < 0) {
                                newY = wall.y + wall.getHeight();
                                object.getVelocity().update(object.getVelocity().getPlayerVelocityX(), 0);
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (boxes != null) {
            for (MovableBox box : boxes) {
                if(!object.equals(box)) {
                    if (new Rectangle(newX, object.getY(), width, height).overlaps(box)) {
                        object.getVelocity().update(object.getVelocity().getPlayerVelocityX(), object.getVelocity().getPlayerVelocityY());
                        box.getVelocity().update(object.getVelocity().getPlayerVelocityX() , 0);

                       if (object.getVelocity().getPlayerVelocityX() > 0) {
                            newX = box.getX() - width;
                        } else if (object.getVelocity().getPlayerVelocityX() < 0) {
                            newX = box.getX() + box.getWidth();
                       }
                       else{
                           newX = box.getX();
                       }
                        collision(box, walls, buttons, boxes, delta, level, false);
                    }
                        if (new Rectangle(object.getX(), newY, width, height).overlaps(box)) {
                        box.getVelocity().update(0, 0);
                        collision(box, walls, buttons, boxes, delta, level, false);

                        if (object.getVelocity().getPlayerVelocityY() >= 0) {
                            newY = box.getY() - height;
                            if(newY < 0)
                                newY = box.y + box.getHeight();
                        } else if (object.getVelocity().getPlayerVelocityY() < 0) {
                            newY = box.getY() + box.getHeight();
                            object.getVelocity().update(object.getVelocity().getPlayerVelocityX(), gravity);
                        }
                }
            }
            }
        }

        if (buttons != null) {
            for (Button button : buttons) {
                if (new Rectangle(newX, object.getY(), width, height).overlaps(button)) {
                    button.press();
                }
            }
        }
        if(newY < 0f) {
            level.reset();
            return;
        }

        object.set(newX, newY, width, height);
    }

    public void move(float delta, Boolean jump, Level level) {
        HashMap<String, List<?>> entities = level.getEntities();
        ArrayList<Wall> walls = (ArrayList<Wall>) entities.get("walls");
        ArrayList<Spike> spikes = (ArrayList<Spike>) entities.get("spikes");
        ArrayList<Button> buttons = (ArrayList<Button>) entities.get("buttons");
        ArrayList<MovableBox> boxes = (ArrayList<MovableBox>) entities.get("boxes");
        ArrayList<Spitstone> spitstones = (ArrayList<Spitstone>) entities.get("spitstones");
        ArrayList<Projectile> projectiles = (ArrayList<Projectile>) entities.get("projectiles");
        end = level.getEnd();

        if(boxes != null) {
            for (MovableBox box : boxes) {
                collision(box, walls, buttons,boxes, delta, level, false);
            }
        }

        if(spitstones != null) {
            for (Spitstone spitstone : spitstones) {
                Projectile projectile = spitstone.fire(delta);
                if (projectile != null) {
                    projectiles.add(projectile);
                }
            }
        }

        if(projectiles != null && !(this instanceof MovableBox)) {
            for (Projectile projectile : projectiles) {
                if(this.overlaps(projectile)) {
                    level.reset();
                    return;
                }
                projectile.collision(delta,walls,boxes,buttons,spikes);
            }

            Iterator<Projectile> iterator = projectiles.iterator();
            while (iterator.hasNext()) {
                Projectile projectile = iterator.next();
                if (!projectile.isExists()) {
                    iterator.remove();
                }
            }
        }

        if(new Rectangle(x, y, width, height).overlaps(end)) {
            level.complete();
        }


        if(spikes != null) {
            for (Spike spike : spikes) {
                Polygon spikePolygon = spike.getTriangle();
                Polygon playerPolygon = new Polygon(new float[] {
                    x, y,
                    x + width, y,
                    x + width, y + height,
                    x, y + height
                });

                if (Intersector.overlapConvexPolygons(spikePolygon, playerPolygon)) {
                    level.reset();
                    return;
                }
            }
        }
        collision(this, walls, buttons,boxes, delta, level, jump);
    }

}
