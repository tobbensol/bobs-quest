package model.objects;

import com.badlogic.gdx.math.Vector2;


/**
 * An enemy object should have the ability to take and receive damage towards a player object.
 */
public interface Enemy extends IGameObject {

    /**
     * This method should return the amount of damage the enemy should give.
     *
     * @return - the amount of damage.
     */
    int getAttack();

    /**
     * This method determines what should happen when this object is being hit.
     */
    void onHit();

    /**
     * This method sets the position of the nearby payer so that the object can move towards.
     *
     * @param position - The player position.
     */
    void setPlayerPosition(Vector2 position);

    /**
     * This method should set a boolean value inside the enemy objects class if a player is nearby (within a sensor)
     *
     * @param begin - True if player nearby, false otherwise.
     */
    void setPlayerNearby(boolean begin);
}
