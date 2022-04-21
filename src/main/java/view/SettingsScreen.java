package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.GameModel;

public class SettingsScreen implements Screen {

    private final Viewport viewport;
    private final Stage stage;
    private final GameModel gameModel;
    private Skin skin;

    public SettingsScreen(GameModel gameModel) {
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
        table.center().top().padTop(viewport.getScreenHeight()/4f);
//        table.setDebug(true);
        table.setFillParent(true);
        stage.addActor(table);

        Label settings = new Label("Settings", font);
        settings.setFontScale(4f);

        Label musicVolumeLabel = new Label("Music volume", font);
        musicVolumeLabel.setFontScale(1.5f);

        Slider musicVolumeSlider = new Slider(0,1,0.1f,false,skin);
        musicVolumeSlider.setValue(gameModel.getMusicVolume());

        Label soundEffectsVolumeLabel = new Label("Sound effects volume", font);
        soundEffectsVolumeLabel.setFontScale(1.5f);
        Slider soundEffectsVolumeSlider = new Slider(0,1,0.1f,false,skin);
        soundEffectsVolumeSlider.setValue(gameModel.getSoundEffectsVolume());

        TextButton back = new TextButton("Back", skin);

        table.add(settings).center().colspan(2);
        table.row();
        table.add(musicVolumeLabel).padTop(20);
        table.add(musicVolumeSlider).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(soundEffectsVolumeLabel).padTop(20);
        table.add(soundEffectsVolumeSlider).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(back).padTop(20).minWidth(150).minHeight(50).colspan(2);

        musicVolumeSlider.addListener(Boot.INSTANCE.getGameController().volumeListener(musicVolumeSlider, true));
        soundEffectsVolumeSlider.addListener(Boot.INSTANCE.getGameController().volumeListener(soundEffectsVolumeSlider, false));
        back.addListener(Boot.INSTANCE.getGameController().goBackListener());


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
