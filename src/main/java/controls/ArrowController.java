package controls;

import com.badlogic.gdx.Input;

public class ArrowController extends Controller {

    public ArrowController()  {
        super(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN);
    }

}
