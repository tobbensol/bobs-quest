package view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import launcher.Boot;
import model.GameModel;


public class NewGameScreen extends AbstractScreen {

    public NewGameScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        stage.addActor(table);
        table.center().top().padTop(Boot.INSTANCE.getScreenHeight() / 4f);

        table.setFillParent(true);
        Label title = new Label("NEW GAME", font);
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

        singleplayer.addListener(Boot.INSTANCE.getGameController().newGameListener(1));
        multiplayer1.addListener(Boot.INSTANCE.getGameController().newGameListener(2));
        multiplayer2.addListener(Boot.INSTANCE.getGameController().newGameListener(3));
        back.addListener(Boot.INSTANCE.getGameController().goBackListener());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.renderBackground();
    }


}
