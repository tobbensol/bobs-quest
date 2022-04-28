package view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import launcher.Boot;
import model.GameModel;
import model.GameState;
public class MainMenuScreen extends AbstractScreen {


    public MainMenuScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        stage.addActor(table);
        table.center().top().padTop(viewport.getScreenHeight()/4f);

        table.setFillParent(true);
        Label title = new Label("PLATFORM GAME", font);
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

        newGame.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.NEW_GAME));
        continueGame.addListener(Boot.INSTANCE.getGameController().continueGameListener());
        selectLevel.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.SELECT_LEVEL));
        settings.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.SETTINGS));
        exit.addListener(Boot.INSTANCE.getGameController().exitListener());
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        this.renderBackground();
    }

}
