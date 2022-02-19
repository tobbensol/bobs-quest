package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import objects.Player;

public class Controller {
    Player player;
    int up;
    int down;
    int left;
    int right;


    public Controller(int left, int right, int up, int down){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Vector2 inputListener(){
        Vector2 direction = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(right)) {
            direction = direction.add(new Vector2(2, 0));
        }
        if (Gdx.input.isKeyPressed(left)) {
            direction = direction.add(new Vector2(-2, 0));
        }
        if (Gdx.input.isKeyPressed(down)) {
            direction = direction.add(new Vector2(0, -2));
        }
        if (Gdx.input.isKeyPressed(up)) {
            direction = direction.add(new Vector2(0, 2));
        }
        return direction;
    }



}
