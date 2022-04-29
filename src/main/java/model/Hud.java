package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.objects.Coin;
import model.objects.Player;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.Map;

public class Hud {
    private final Viewport viewport;
    private final Level level;
    private final Map<Player, Label> playerLabelHashMap = new HashMap<>();
    public Stage stage;
    private Integer score;
    private Label scoreLabel;
    private Label levelLabel;
    private Label pausedLabel;
    private Label pauseInfoLabel;
    private Label mainMenuLabel;

    public Hud(SpriteBatch batch, Level level) {
        this.level = level;
        score = 0;

        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table levelInfoTable = createLevelInfoTab(level);
        Table playerHpTable = playerHpTab();
        Table gamePausedTable = createPauseTab();
        Image filter = createFilter();

        stage.addActor(filter); // NB: filter must be added first!
        stage.addActor(levelInfoTable);
        stage.addActor(playerHpTable);
        stage.addActor(gamePausedTable);

    }

    public void update() {
        score = level.getScore();
        scoreLabel.setText("Coins: " + score + "/" + level.getGameObjects(Coin.class).size());
        updateHpLabels();
    }

    private void updateHpLabels() {
        if (!level.getModel().isPaused()) {
            for (Player player : playerLabelHashMap.keySet()) {
                if (player.isDead()) {
                    playerLabelHashMap.get(player).setColor(Color.DARK_GRAY);
                } else {
                    playerLabelHashMap.get(player).setText(player.toString());

                    if (player.getHp() > 80) {
                        playerLabelHashMap.get(player).setColor(Color.GREEN);
                    } else if (player.getHp() > 40) {
                        playerLabelHashMap.get(player).setColor(Color.ORANGE);
                    } else {
                        playerLabelHashMap.get(player).setColor(Color.RED);
                    }

                }
            }
        }
    }

    public void pause() {
        pausedLabel.setText("GAME PAUSED");
        pauseInfoLabel.setText("Press ESC To Resume");
        mainMenuLabel.setText("Press M to Main Menu");
        stage.getActors().get(0).getColor().a = 0.4f;
        for (Player player : playerLabelHashMap.keySet()) {
            playerLabelHashMap.get(player).setText("");
        }
    }

    public void resume() {
        pausedLabel.setText("");
        pauseInfoLabel.setText("");
        mainMenuLabel.setText("");
        stage.getActors().get(0).getColor().a = 0f;
    }

    private Table createPauseTab() {
        Table gamePausedTable = new Table();
        gamePausedTable.center();
        gamePausedTable.setFillParent(true);
        pausedLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pauseInfoLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mainMenuLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        pausedLabel.setFontScale(5f);
        pauseInfoLabel.setFontScale(2f);
        mainMenuLabel.setFontScale(2f);

        gamePausedTable.add(pausedLabel).expandX();
        gamePausedTable.row();
        gamePausedTable.add(pauseInfoLabel).expandX();
        gamePausedTable.row();
        gamePausedTable.add(mainMenuLabel);
        return gamePausedTable;
    }

    private Table playerHpTab() {
        Table playerHpTable = new Table();
        playerHpTable.left().top().padTop(50);
        playerHpTable.setFillParent(true);

        for (Player player : level.getGameObjects(Player.class)) {
            Label label = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            label.setFontScale(1.5f);
            playerLabelHashMap.put(player, label);
            playerHpTable.add(label).left().padLeft(10);
            playerHpTable.row();
        }
        return playerHpTable;
    }

    private Table createLevelInfoTab(Level level) {
        Table table = new Table();
        table.top();
        table.setFillParent(true);


        scoreLabel = new Label("Coins: " + score + "/" + level.getGameObjects(Coin.class).size(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
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
        filter.setSize(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight());
        filter.getColor().a = 0f;
        return filter;
    }
}
