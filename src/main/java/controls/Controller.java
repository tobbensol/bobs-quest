package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Controller {
    protected final int up;
    protected final int down;
    protected final int left;
    protected final int right;
    private final int SPEED = 200;
    private float delta;

    public Controller(int left, int right, int up, int down){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Vector2 inputListener(){
        delta = Gdx.graphics.getDeltaTime();
        Vector2 direction = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(right)) {
            direction.add(new Vector2(SPEED*delta, 0));
        }
        if (Gdx.input.isKeyPressed(left)) {
            direction.add(new Vector2(-SPEED*delta, 0));
        }
        if (Gdx.input.isKeyPressed(down)) {
            direction.add(new Vector2(0, -SPEED*delta));
        }
        if (Gdx.input.isKeyPressed(up)) {
            direction.add(new Vector2(0, 3000*delta));
        }
        return direction;
    }



}
