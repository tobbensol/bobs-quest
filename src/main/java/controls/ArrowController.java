package controls;

import com.badlogic.gdx.Input;

public class ArrowController extends Controller {

    /**
     * Controller using the arrow keys
     * Left - LEFT-ARROW
     * Right - RIGHT-ARROW
     * Up - UP-ARROW
     * Down - DOWN-ARROW
     */
    public ArrowController() {
        super(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN);
    }

}
