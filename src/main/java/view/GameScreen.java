package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import model.GameModel;
import model.helper.Constants;
import model.objects.IGameObject;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen extends AbstractScreen {

    private final GameCamera camera;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public GameScreen(GameModel gameModel) {
        super(gameModel);

        this.camera = gameModel.getCamera();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.orthogonalTiledMapRenderer = gameModel.getLevel().setupMap();
    }

    /**
     * the game runs in real time and updating the game often is required for it to run smoothly
     */
    private void update() {
        gameModel.update();
        camera.update();

        this.batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
    }


    @Override
    public void render(float v) {
        this.update();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.8f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();

        for (IGameObject object : gameModel.getLevel().getGameObjects()) {
            if (!object.isDestroyed()) {
                object.render(batch);
            }
        }

        batch.end();
        box2DDebugRenderer.render(gameModel.getLevel().getWorld(), camera.combined.scl(Constants.PPM));

        batch.setProjectionMatrix(gameModel.getLevel().getHud().stage.getCamera().combined);
        gameModel.getLevel().getHud().stage.draw();
    }

    @Override
    public void show() {

    }


}
