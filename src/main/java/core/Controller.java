package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import objects.Player;

public class Controller {
    private final int up;
    private final int down;
    private final int left;
    private final int right;
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
            player.move(new Vector2(SPEED*delta, 0));
        }
        if (Gdx.input.isKeyPressed(left)) {
            player.move(new Vector2(-SPEED*delta, 0));
        }
        if (Gdx.input.isKeyPressed(down)) {
            player.move(new Vector2(0, -SPEED*delta));
        }
        if (Gdx.input.isKeyPressed(up)) {
            player.move(new Vector2(0, SPEED*delta));
        }
        return direction;
    }



}
