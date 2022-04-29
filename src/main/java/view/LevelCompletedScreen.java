package view;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import launcher.Boot;
import model.GameModel;
import model.GameState;

public class LevelCompletedScreen extends AbstractScreen {

    public LevelCompletedScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);


        Label gameCompletedLabel = new Label(gameModel.getLevel().toString() + " COMPLETED!", font);
        gameCompletedLabel.setFontScale(4f);
        table.add(gameCompletedLabel);

        TextButton nextLevel = new TextButton("Next Level", skin);
        TextButton mainMenu = new TextButton("Main menu", skin);

        table.row();
        table.add(nextLevel).padTop(20).minWidth(250).minHeight(50);
        table.row();
        table.add(mainMenu).padTop(20).minWidth(250).minHeight(50);


        nextLevel.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.ACTIVE));
        mainMenu.addListener(Boot.INSTANCE.getGameController().goToScreenListener(GameState.MAIN_MENU));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.renderBackground();
    }

}
