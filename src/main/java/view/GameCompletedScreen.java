package view;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import model.GameModel;
import model.GameState;

public class GameCompletedScreen extends AbstractScreen {

    public GameCompletedScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        Label congratulations = new Label("CONGRATULATIONS!", font);
        Label gameCompleted = new Label("GAME COMPLETED", font);

        TextButton mainMenu = new TextButton("Main menu", skin);

        congratulations.setFontScale(4f);
        gameCompleted.setFontScale(3f);

        table.add(congratulations).expandX();
        table.row();
        table.add(gameCompleted).expandX();
        table.row();
        table.add(mainMenu).padTop(20).minWidth(200).minHeight(50);

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.MAIN_MENU);
                gameModel.changeScreen();
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.renderBackground();
    }

}
