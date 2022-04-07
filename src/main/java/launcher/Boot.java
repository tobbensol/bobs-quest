package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import model.GameModel;
import view.GameCamera;
import view.StartScreen;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int screenWidth, screenHeight;
    private GameModel gameModel;

    public Boot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.gameModel = new GameModel();

        setScreen(new StartScreen(gameModel));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

}
