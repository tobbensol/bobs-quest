package controls;

import com.badlogic.gdx.Input;

public class WASDController extends Controller{

    public WASDController() {
        super(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S);
    }
}
