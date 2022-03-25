package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import model.GameModel;
import model.GameState;

public class GameController {
    private final GameModel gameModel;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void inputListener() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (gameModel.getState() == GameState.STARTUP || gameModel.getState() == GameState.GAME_OVER || gameModel.getState() == GameState.NEXT_LEVEL) {
                gameModel.setState(GameState.ACTIVE);
                gameModel.changeScreen();
            }
        }

    }
}
