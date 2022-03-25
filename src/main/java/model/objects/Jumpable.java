package model.objects;

/**
 * A jumpable object extends movable and should be able to both move horizontally and vertically (jumping).
 */
public interface Jumpable extends Movable {

    /**
     * This method should make an object jump.
     *
     * @param delta - the time gap between previous and current frame
     */
    void jump(float delta);
}
