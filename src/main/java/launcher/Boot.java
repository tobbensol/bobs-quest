package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import model.GameModel;
import view.GameScreen;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int screenWidth, screenHeight;
    private OrthographicCamera orthographicCamera;
    private GameModel gameModel;

    public Boot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, screenWidth, screenHeight);
        this.gameModel = new GameModel();
        // TODO: Set new screens
        setScreen(new GameScreen(orthographicCamera, gameModel));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
