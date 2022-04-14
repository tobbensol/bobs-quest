package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.GameModel;

public class SettingsScreen implements Screen {

    private final Viewport viewport;
    private final Stage stage;

    private final GameModel gameModel;


    public SettingsScreen(GameModel gameModel) {
        this.gameModel = gameModel;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());
        Gdx.input.setInputProcessor(stage);

    }


    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.setDebug(true);
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("src/main/resources/ui/uiskin.json"));

        Label settings = new Label("Settings", font);
        settings.setFontScale(4f);

        TextButton back = new TextButton("Back", skin);

        Label musicVolumeLabel = new Label("Music volume", font);
        Slider musicVolumeSlider = new Slider(0,1,0.1f,false,skin);
        musicVolumeSlider.setValue(gameModel.getMusicVolume());

        Label soundEffectsVolumeLabel = new Label("Sound effects volume", font);
        Slider soundEffectsVolumeSlider = new Slider(0,1,0.1f,false,skin);
        soundEffectsVolumeSlider.setValue(gameModel.getSoundEffectsvolume());

        table.add(settings);
        table.row();
        table.add(musicVolumeLabel);
        table.add(musicVolumeSlider);
        table.row();
        table.add(soundEffectsVolumeLabel);
        table.add(soundEffectsVolumeSlider);
        table.row();
        table.add(back);


        musicVolumeSlider.addListener(new DragListener() {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                gameModel.setMusicVolume(musicVolumeSlider.getValue());
            }
        });

        soundEffectsVolumeSlider.addListener(new DragListener() {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                gameModel.setSoundEffectsvolume(soundEffectsVolumeSlider.getValue());
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
