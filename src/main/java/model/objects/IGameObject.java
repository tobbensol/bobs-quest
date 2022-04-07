package model.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 */
public interface IGameObject {

    /**
     *
     */
    void update();

    /**
     *
     * @param batch
     */
    void render(SpriteBatch batch);

    /**
     *
     * @param x
     * @param y
     */
    void setPosition(float x, float y);

    /**
     *
     * @return
     */
    Vector2 getPosition();

    /**
     *
     * @return
     */
    Body getBody();

    /**
     *
     * @return
     */
    String toString();
}
