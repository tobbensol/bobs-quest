package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import controls.GameController;
import model.GameModel;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int screenWidth, screenHeight;
    private GameModel gameModel;
    private GameController gameController;

    public Boot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.gameModel = new GameModel();
        this.gameController = new GameController(gameModel);
        this.gameModel.changeScreen();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public GameController getGameController() {
        return gameController;
    }
}
