package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import model.GameModel;
import model.helper.Constants;
import model.objects.Coin;
import model.objects.IGameObject;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {

    private final GameModel gameModel;
    private final SpriteBatch batch;
    private final GameCamera camera;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public GameScreen(GameModel gameModel) {
        this.gameModel = gameModel;
        this.camera = gameModel.getCamera();
        this.batch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.orthogonalTiledMapRenderer = gameModel.getLevel().setupMap();
    }

    /**
     * the game runs in real time and updating the game often is required for it to run smoothly
     */
    private void update() {
        //TODO find better way to do this?
        if (gameModel.getReload()) {
            orthogonalTiledMapRenderer = gameModel.getLevel().setupMap();
            gameModel.setReload(false);
        }
        gameModel.update();
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
    }

    /**
     * the camera should follow the player character
     */

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render(float v) {
        this.update();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.8f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();

        for (IGameObject object : gameModel.getLevel().getGameObjects()) {
            // TODO: Add isDestroyed() for all GameObjects
            if (object instanceof Coin coin) {
                if (coin.isDestroyed()) {
                    continue;
                }
            }
            object.render(batch);
        }

        batch.end();
        box2DDebugRenderer.render(gameModel.getLevel().getWorld(), camera.combined.scl(Constants.PPM));

        batch.setProjectionMatrix(gameModel.getLevel().getHud().stage.getCamera().combined);
        gameModel.getLevel().getHud().stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
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

}
