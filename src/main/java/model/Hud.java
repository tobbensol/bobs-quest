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
import model.objects.Coin;

public class Hud {
    private final Viewport viewport;
    public Stage stage;

    private final Level level;
    private static Integer score;

    private static Label scoreLabel;
    private final Label levelLabel;
    private final Label pausedLabel;
    private final Label pauseInfoLabel;

    public Hud(SpriteBatch batch, Level level) {
        this.level = level;
        score = 0;


        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(score + "/" + level.getGameObjects(Coin.class).size(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(level.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel.setFontScale(2f);
        levelLabel.setFontScale(2f);

        // First row:
        table.add(levelLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        // Use table.row() for starting on a new line

        Table gamePausedTable = new Table();
        gamePausedTable.center();
        gamePausedTable.setFillParent(true);
        pausedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pauseInfoLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        pausedLabel.setFontScale(5f);
        pauseInfoLabel.setFontScale(2f);

        gamePausedTable.add(pausedLabel).expandX();
        gamePausedTable.row();
        gamePausedTable.add(pauseInfoLabel).expandX();

        stage.addActor(table);
        stage.addActor(gamePausedTable);
    }

    public void update() {
        score = level.getScore();
        scoreLabel.setText(score + "/" + level.getGameObjects(Coin.class).size());
        // TODO maybe not do this every frame
        levelLabel.setText(level.toString());

    }

    public void pause() {
        pausedLabel.setText("GAME PAUSED");
        pauseInfoLabel.setText("Press P To Resume");
    }

    public void resume() {
        pausedLabel.setText("");
        pauseInfoLabel.setText("");
    }
}
