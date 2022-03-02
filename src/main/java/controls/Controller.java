package controls;

import com.badlogic.gdx.Gdx;
import model.objects.Player;

public abstract class Controller {
    protected final int up;
    protected final int down;
    protected final int left;
    protected final int right;
    private final int SPEED = 200;

    public Controller(int left, int right, int up, int down){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void inputListener(Player player){
//        float delta = Gdx.graphics.getDeltaTime();
        float delta = 1.0f;
        if (Gdx.input.isKeyPressed(right)) {
            player.moveHorizontally(delta, true);
        }
        if (Gdx.input.isKeyPressed(left)) {
            player.moveHorizontally(delta, false);
        }
        if (Gdx.input.isKeyPressed(up)) {
            player.jump(delta);
        }
    }



}
