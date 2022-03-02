package model.objects;

/**
 * A moveable object should be able to move horizontally (to the left and to the right).
 */
public interface Moveable {

    /**
     * This method should move an object to either right or left.
     * @param delta - the time gap between previous and current frame.
     * @param isRight - boolean parameter to indicate whether you want to move to the right or not (to the left). true -> right, false -> left.
     */
    void moveHorizontally(float delta, boolean isRight);
}
