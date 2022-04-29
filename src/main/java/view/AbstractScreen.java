package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import launcher.Boot;
import model.GameModel;
import model.helper.Constants;

import java.util.Arrays;

public abstract class AbstractScreen implements Screen {

    protected final Viewport viewport;
    protected final Stage stage;
    protected final GameModel gameModel;
    protected Skin skin;
    protected SpriteBatch batch;

    private float stateTime = 0;

    private Texture background;
    private Texture player;
    private Animation<TextureRegion> animation;


    public AbstractScreen(GameModel gameModel) {
        this.gameModel = gameModel;
        this.viewport = new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), new OrthographicCamera());
        this.batch = new SpriteBatch();
        this.stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        loadBackground();
    }

    private void loadBackground() {
        this.background = new Texture("images/main-menu-background.png");
        this.player = new Texture("Multi_Platformer_Tileset_v2/Players/Adventurer_Sprite_Sheet.png");
        TextureRegion[][] frames = TextureRegion.split(player, Constants.TILE_SIZE, Constants.TILE_SIZE);
        this.animation = new Animation<>(0.0825f, Arrays.copyOf(frames[1], 8));
        System.out.println(background.getHeight());
    }

    /**
     * This method renders the scrolling background with player animation.
     * The scrolling progress is saved in the GameModel when switching between the screens.
     */
    protected void renderBackground() {
        batch.begin();
        batch.draw(background, gameModel.getBackgroundX(), 0, background.getWidth(), Gdx.graphics.getHeight());
        batch.draw(animation.getKeyFrame(stateTime, true), 200, 0.333f*Gdx.graphics.getHeight(), 80, 80);
        batch.end();
        stage.draw();

        gameModel.setBackgroundX(gameModel.getBackgroundX() - 2);
        if (gameModel.getBackgroundX() < -background.getWidth() + Boot.INSTANCE.getScreenWidth()) {
            gameModel.setBackgroundX(0);
        }
        stateTime = stateTime + Gdx.graphics.getDeltaTime();
    }

    @Override
    public abstract void show();

    @Override
    public void render(float delta) {
        gameModel.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        player.dispose();
    }
}
