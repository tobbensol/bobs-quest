package view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import model.GameModel;
import model.helper.Constants;
import model.objects.Goal;
import model.objects.Goomba;
import model.objects.Player;
import model.objects.Coin;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {

    private final GameModel gameModel;
    private SpriteBatch batch;
    private GameCamera camera;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public GameScreen(GameCamera camera, GameModel gameModel) {
        this.gameModel = gameModel;
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.orthogonalTiledMapRenderer = gameModel.setupMap();
    }

    /**
     * the game runs in real time and updating the game often is required for it to run smoothly
     */
    private void update(){
        //TODO find better way to do this?
        if (gameModel.getReload()){
            orthogonalTiledMapRenderer = gameModel.setupMap();
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

        for (Player player : gameModel.getPlayers()) {
            player.render(batch);
        }

        for (Goomba goomba : gameModel.getGoombas()) {
            goomba.render(batch);
        }

        for (Coin coin : gameModel.getCoins()){
            if (!coin.isDestroyed()) {
                coin.render(batch);
            }
        }
        for(Goal goal : gameModel.getGoals()){
            goal.render(batch);
        }


        batch.end();
        box2DDebugRenderer.render(gameModel.getWorld(), camera.combined.scl(Constants.PPM));

        batch.setProjectionMatrix(gameModel.getHud().stage.getCamera().combined);
        gameModel.getHud().stage.draw();
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
