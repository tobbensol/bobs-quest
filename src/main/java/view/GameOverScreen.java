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

public class GameOverScreen extends AbstractScreen {

    public GameOverScreen(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameOverLabel = new Label("GAME OVER", font);
        TextButton playAgain = new TextButton("Play Again", skin);
        TextButton mainMenu = new TextButton("Main menu", skin);

        gameOverLabel.setFontScale(4f);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgain).padTop(20).minWidth(200).minHeight(50);
        table.row();
        table.add(mainMenu).padTop(20).minWidth(200).minHeight(50);

        playAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameModel.setCurrentState(GameState.ACTIVE);
                gameModel.changeScreen();
                gameModel.resumeGame();
            }
        });

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
