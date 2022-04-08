package controls;

import com.badlogic.gdx.Gdx;
import model.objects.Player;

public abstract class Controller {
    protected final int up;
    protected final int down;
    protected final int left;
    protected final int right;

    public Controller(int left, int right, int up, int down) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public void inputListener(Player player) {
        if (player.isDead()) {
            return;
        }
        if (Gdx.input.isKeyPressed(right)) {
            player.moveHorizontally(true);
        }
        if (Gdx.input.isKeyPressed(left)) {
            player.moveHorizontally(false);
        }
        if (Gdx.input.isKeyPressed(up)) {
            player.jump();
        }
        if (Gdx.input.isKeyPressed(down)) {
            player.drop();
        }
    }

}
