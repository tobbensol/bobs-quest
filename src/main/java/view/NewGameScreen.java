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

public class NewGameScreen implements Screen {
    private final Viewport viewport;
    private final Stage stage;
    private final GameModel gameModel;
    private Skin skin;


    public NewGameScreen(GameModel gameModel) {
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
        Label title = new Label("NEW GAME", font);
//        title.debug();
        title.setFontScale(4f);
        table.add(title);

        TextButton singleplayer = new TextButton("Singleplayer", skin);
        TextButton multiplayer1 = new TextButton("Multiplayer (2 players)", skin);
        TextButton multiplayer2 = new TextButton("Multiplayer (3 players)", skin);
        TextButton back = new TextButton("Back", skin);


        table.row();
        table.add(singleplayer).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(multiplayer1).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(multiplayer2).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(back).padTop(20).minWidth(250).minHeight(50);
        //stage.addActor(table);

        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.resetAvailableLevels();
                gameModel.setLevelNR(0);
                gameModel.restart();
                gameModel.setCurrentState(GameState.ACTIVE);
                gameModel.changeScreen();
                gameModel.resumeGame();
            }
        });

        multiplayer1.addListener(new ChangeListener() { //TODO: ADD multiplayer option screen
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.resetAvailableLevels();
                gameModel.setLevelNR(0);
                gameModel.setNumPlayers(2);
                gameModel.restart();
                gameModel.setCurrentState(GameState.ACTIVE);
                gameModel.changeScreen();
                gameModel.resumeGame();
            }
        });

        multiplayer2.addListener(new ChangeListener() { //TODO: ADD multiplayer option screen
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.resetAvailableLevels();
                gameModel.setLevelNR(0);
                gameModel.setNumPlayers(3);
                gameModel.restart();
                gameModel.setCurrentState(GameState.ACTIVE);
                gameModel.changeScreen();
                gameModel.resumeGame();
            }
        });


        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(gameModel.getPreviousState());
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
