package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import model.GameModel;
import model.GameState;

public class GameController {
    private GameModel gameModel;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void inputListener(){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (gameModel.getState() == GameState.STARTUP) {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                gameModel.setNumPlayers(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                gameModel.setNumPlayers(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                gameModel.setNumPlayers(3);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                gameModel.setState(GameState.ACTIVE);
                gameModel.changeScreen();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (gameModel.getState() == GameState.GAME_OVER || gameModel.getState() == GameState.NEXT_LEVEL) {
                gameModel.setState(GameState.ACTIVE);
                gameModel.changeScreen();
            }
        }

    }
}
