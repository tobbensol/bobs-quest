package controls;

import com.badlogic.gdx.Input;

public class WASDController extends Controller{

    /**
     * Controller using WASD as key input.
     * Left - W
     * Right - D
     * Up - W
     * Down - S
     */
    public WASDController() {
        super(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S);
    }
}
