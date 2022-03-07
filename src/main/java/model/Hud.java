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

    private static Integer score;

    private static Label scoreLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label timerLabel;
    private Label countdownLabel;

    public Hud(SpriteBatch batch) {
        score = 0;

        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("Level 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(levelLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }


}
