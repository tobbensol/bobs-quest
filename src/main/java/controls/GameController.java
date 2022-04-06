package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import model.ControllableModel;
import model.GameState;

public class GameController {
    private final ControllableModel model;
    private boolean pauseHelper;

    public GameController(ControllableModel model) {
        this.model = model;
    }

    public void inputListener() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P) && model.getState() == GameState.ACTIVE) {
            // Use a helper so that a held-down button does not continuously switch between states with every tick
            if (pauseHelper) {
                if (model.isPaused()) {
                    model.resumeGame();
                    System.out.println("Game Resumed");
                }
                else {
                    model.pauseGame();
                    System.out.println("Game Paused");
                }
                pauseHelper = false;
            }
        }
        else {
            pauseHelper = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            model.restart();
        }

        if (model.getState() == GameState.STARTUP) {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                model.setNumPlayers(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                model.setNumPlayers(2);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                model.setNumPlayers(3);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                model.setState(GameState.ACTIVE);
                model.changeScreen();
                model.restart();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (model.getState() == GameState.GAME_OVER || model.getState() == GameState.NEXT_LEVEL) {
                model.setState(GameState.ACTIVE);
                model.changeScreen();
                model.restart();
            }
        }

    }
}
