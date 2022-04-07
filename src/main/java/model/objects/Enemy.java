package model.objects;

import com.badlogic.gdx.math.Vector2;


/**
 *
 */
public interface Enemy extends IGameObject{

    /**
     *
     * @return
     */
    int getAttack();

    /**
     *
     */
    void onHit();

    /**
     *
     * @param position
     */
    void setPlayerPosition(Vector2 position);

    /**
     *
     * @param begin
     */
    void setPlayerNearby(boolean begin);
}
