package model.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 */
public interface IGameObject {

    /**
     * This method determines what happens with the object in each iteration of the game.
     */
    void update();

    /**
     * This method renders the object in each iteration of the game.
     *
     * @param batch
     */
    void render(SpriteBatch batch);

    /**
     * This method should set the position of the object.
     *
     * @param x - The x coordinate.
     * @param y - The y coordinate.
     */
    void setPosition(float x, float y);

    /**
     * @return - Returns the position of the object.
     */
    Vector2 getPosition();

    /**
     * @return - Returns the body of the object.
     */
    Body getBody();

    /**
     * @return - Returns the name of the object.
     */
    String toString();

    /**
     *
     * @param filterAway
     * @param bit
     */
    void changeMaskBit(boolean filterAway, short bit);
}
