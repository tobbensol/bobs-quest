package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.GameModel;
import model.GameState;

public class GameOverScreen implements Screen {
    private final Viewport viewport;
    private final Stage stage;

    private final GameModel gameModel;
    private Skin skin;


    public GameOverScreen(GameModel gameModel) {
        this.gameModel = gameModel;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("src/main/resources/ui/uiskin.json"));


    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameOverLabel = new Label("GAME OVER", font);
        TextButton playAgain = new TextButton("Play Again", skin);
        TextButton mainMenu = new TextButton("Main menu", skin);

        gameOverLabel.setFontScale(4f);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgain).padTop(20).minWidth(200).minHeight(50);
        table.row();
        table.add(mainMenu).padTop(20).minWidth(200).minHeight(50);

        playAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.ACTIVE);
                gameModel.changeScreen();
                gameModel.resumeGame();
            }
        });

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.MAIN_MENU);
                gameModel.changeScreen();
            }
        });
    }

    @Override
    public void render(float delta) {
        gameModel.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
