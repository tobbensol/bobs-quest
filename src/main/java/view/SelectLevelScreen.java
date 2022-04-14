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
import model.Level;

import java.util.ArrayList;

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
        Label title = new Label("Select Level", font);
//        title.debug();
        title.setFontScale(4f);
        table.add(title).colspan(2);

        int levelNr = 0;
        ArrayList<TextButton> textButtonList = new ArrayList<>();

        for (String level: gameModel.getLevels()) {
            TextButton textButton = new TextButton(level,skin);
            textButtonList.add(textButton);
            if (levelNr % 2 == 0) {
                table.row();
            }
            table.add(textButton).padTop(20).minWidth(250).minHeight(50).colspan(1);
            levelNr++;
        }

        int index = 0;
        for (TextButton textButton: textButtonList) {
            int index1 = index;
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gameModel.setLevelNR(index1);
                    gameModel.setCurrentState(GameState.ACTIVE);
                    gameModel.changeScreen();
                    gameModel.resumeGame();
                }
            });
            index++;
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
