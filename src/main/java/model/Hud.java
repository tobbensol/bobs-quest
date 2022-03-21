package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private GameModel gameModel;

    private final float fontSize;
    private static Integer score;

    private static Label scoreLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label timerLabel;
    private Label countdownLabel;

    public Hud(SpriteBatch batch, GameModel gameModel) {
        this.gameModel = gameModel;
        score = 0;

        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        fontSize = 2f;

        scoreLabel = new Label(score + "/" + gameModel.getCoins().size(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(sentenceCase(gameModel.getLevel()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //worldLabel = new Label("Epic game", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel.setFontScale(fontSize);
        levelLabel.setFontScale(fontSize);

        // First row:
        table.add(levelLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        //table.row(); // Use table.row() for starting on a new line

        // Second row:
        //table.add(worldLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void update() {
        score = gameModel.getScore();
        scoreLabel.setText(score + "/" + gameModel.getCoins().size());
        levelLabel.setText(sentenceCase(gameModel.getLevel()));
    }

    static String sentenceCase(String text) {
        if (!text.equals("")) {
            String result = text.replaceAll("([A-Z, 0-9])", " $1");
            return result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
        }
        return null;
    }
}
