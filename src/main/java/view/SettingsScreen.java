package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import launcher.Boot;
import model.GameModel;

public class SettingsScreen extends AbstractScreen {

    public SettingsScreen(GameModel gameModel) {
        super(gameModel);
    }

    TextButton fullScreen = new TextButton("", skin);
    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


    @Override
    public void show() {

        Table table = new Table();
        table.center().top().padTop(viewport.getScreenHeight() / 4f);
        table.setFillParent(true);
        stage.addActor(table);

        Label settings = new Label("Settings", font);
        settings.setFontScale(4f);

        Label musicVolumeLabel = new Label("Music volume", font);
        musicVolumeLabel.setFontScale(1.5f);

        Slider musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        musicVolumeSlider.setValue(gameModel.getMusicVolume());

        Label soundEffectsVolumeLabel = new Label("Sound effects volume", font);
        soundEffectsVolumeLabel.setFontScale(1.5f);
        Slider soundEffectsVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        soundEffectsVolumeSlider.setValue(gameModel.getSoundEffectsVolume());

        Label FullScreenLabel = new Label("Full Screen toggle:", font);

        TextButton back = new TextButton("Back", skin);

        table.add(settings).center().colspan(2);
        table.row();
        table.add(musicVolumeLabel).padTop(20);
        table.add(musicVolumeSlider).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(soundEffectsVolumeLabel).padTop(20);
        table.add(soundEffectsVolumeSlider).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(FullScreenLabel).padTop(20);
        table.add(fullScreen).padTop(20).minWidth(150).minHeight(50).colspan(2);
        table.row();
        table.add(back).padTop(20).minWidth(150).minHeight(50).colspan(2);

        musicVolumeSlider.addListener(Boot.INSTANCE.getGameController().volumeListener(musicVolumeSlider, true));
        soundEffectsVolumeSlider.addListener(Boot.INSTANCE.getGameController().volumeListener(soundEffectsVolumeSlider, false));
        back.addListener(Boot.INSTANCE.getGameController().goBackListener());
        fullScreen.addListener(Boot.INSTANCE.getGameController().fullScreenListener());


    }

    @Override
    public void render(float delta) {
        if(Gdx.graphics.isFullscreen()){
            fullScreen.setLabel(new Label("Fullscreen", font));
        } else {
            fullScreen.setLabel(new Label("Windowed", font));
        }
        super.render(delta);
        this.renderBackground();
    }

}
