package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.objects.Coin;

public class Hud {
    private final Viewport viewport;
    private final Level level;
    private Integer score;
    public Stage stage;
    private Label scoreLabel;
    private Label levelLabel;
    private Label pausedLabel;
    private Label pauseInfoLabel;

    public Hud(SpriteBatch batch, Level level) {
        this.level = level;
        score = 0;

        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table levelInfoTable = createLevelInfoTab(level);
        Table gamePausedTable = createPauseTab();
        Image filter = createFilter();

        stage.addActor(filter); // NB: filter must be added first!
        stage.addActor(levelInfoTable);
        stage.addActor(gamePausedTable);
    }

    public void update() {
        score = level.getScore();
        scoreLabel.setText(score + "/" + level.getGameObjects(Coin.class).size());
    }

    public void pause() {
        pausedLabel.setText("GAME PAUSED");
        pauseInfoLabel.setText("Press P To Resume");
        stage.getActors().get(0).getColor().a = 0.4f;
    }

    public void resume() {
        pausedLabel.setText("");
        pauseInfoLabel.setText("");
        stage.getActors().get(0).getColor().a = 0f;
    }

    private Table createPauseTab() {
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
        return gamePausedTable;
    }

    private Table createLevelInfoTab(Level level) {
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(score + "/" + level.getGameObjects(Coin.class).size(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(level.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel.setFontScale(2f);
        levelLabel.setFontScale(2f);

        table.add(levelLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);



        return table;
    }

    private Image createFilter() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        Image filter = new Image(texture);
        filter.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        filter.getColor().a = 0f;
        return filter;
    }
}
