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


    public Controller(Player player, int left, int right, int up, int down){
        this.player = player;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void inputListener(){
        if (Gdx.input.isKeyPressed(right)) {
            player.setPosition(player.getPosition().add(new Vector2(2, 0)));
        }
        if (Gdx.input.isKeyPressed(left)) {
            player.setPosition(player.getPosition().add(new Vector2(-2, 0)));
        }
        if (Gdx.input.isKeyPressed(down)) {
            player.setPosition(player.getPosition().add(new Vector2(0, -2)));
        }
        if (Gdx.input.isKeyPressed(up)) {
            player.setPosition(player.getPosition().add(new Vector2(0, 2)));
        }
    }



}
