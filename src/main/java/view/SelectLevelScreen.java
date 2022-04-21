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

public class SelectLevelScreen implements Screen {
    private final Viewport viewport;
    private final Stage stage;
    private final GameModel gameModel;
    private Skin skin;


    public SelectLevelScreen(GameModel gameModel) {
        this.gameModel = gameModel;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        stage.addActor(table);
//        table.setDebug(true);
        table.center().top().padTop(viewport.getScreenHeight()/4f);

        table.setFillParent(true);
        Label title = new Label("Select Level", font);
//        title.debug();
        title.setFontScale(4f);
        table.add(title).colspan(2);

        int tableIndex = 0;
        for (String level: gameModel.getAvailableLevels()) { //TODO: Reset completed levels when starting new game
            TextButton textButton = new TextButton(level,skin);
            if (tableIndex % 2 == 0) {
                table.row();
            }
            table.add(textButton).pad(10).minWidth(250).minHeight(50).colspan(1);
            textButton.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String level = String.valueOf(textButton.getText());
                    System.out.println(level);
                    int levelNR = gameModel.getLevels().indexOf(level);
                    System.out.println(levelNR);

                    gameModel.setLevelNR(levelNR);
                    gameModel.restart();
                    gameModel.setCurrentState(GameState.ACTIVE);
                    gameModel.changeScreen();
                    gameModel.resumeGame();
                }
            });
            tableIndex++;
        }



        TextButton back = new TextButton("Back", skin);
        table.row();
        table.add(back).padTop(20).minWidth(250).minHeight(50).colspan(2);

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
