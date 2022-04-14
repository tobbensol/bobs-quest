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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.GameModel;
import model.GameState;

public class StartScreen implements Screen {
    private final Viewport viewport;
    private final Stage stage;
    private final GameModel gameModel;
    private Skin skin;


    public StartScreen(GameModel gameModel) {
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
        stage.addActor(table);
//        table.setDebug(true);
        table.center().top().padTop(viewport.getScreenHeight()/4f);

        table.setFillParent(true);
        Label title = new Label("PLATFORM GAME", font);
//        title.debug();
        title.setFontScale(6f);
        table.add(title);

        TextButton newGame = new TextButton("New Game", skin);
        TextButton continueGame = new TextButton("Continue Game", skin);
        TextButton selectLevel = new TextButton("Select Level", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.row();
        table.add(newGame).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(continueGame).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(selectLevel).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(settings).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(exit).padTop(20).minWidth(250).minHeight(50);

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.NEWGAME);
                gameModel.changeScreen();
            }
        });

        continueGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.ACTIVE);
                gameModel.changeScreen();
                gameModel.resumeGame();
            }
        });

        selectLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.SELECTLEVEL);
                gameModel.changeScreen();
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.SETTINGS);
                gameModel.changeScreen();
            }
        });


        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
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
