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
    private final Viewport viewport;
    private final Level level;

    private static Integer score;

    private static Label scoreLabel;
    private final Label levelLabel;

    public Hud(SpriteBatch batch, Level level) {
        this.level = level;
        score = 0;

        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(score + "/" + level.getCoins().size(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(sentenceCase(level.getLevelName()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel.setFontScale(2f);
        levelLabel.setFontScale(2f);

        // First row:
        table.add(levelLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        // Use table.row() for starting on a new line

        stage.addActor(table);
    }

    public void update() {
        score = level.getScore();
        scoreLabel.setText(score + "/" + level.getCoins().size());
        // TODO maybe not do this every frame
        levelLabel.setText(sentenceCase(level.getLevelName()));
    }

    //from https://dirask.com/posts/Java-convert-camelCase-to-Sentence-Case-jE6PZ1
    static String sentenceCase(String text) {
        if (!text.equals("")) {
            String result = text.replaceAll("([A-Z, 0-9])", " $1");
            return result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
        }
        return null;
    }
}
