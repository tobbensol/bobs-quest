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
import model.objects.Goomba;
import model.objects.Player;
import model.objects.Coin;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {

    private GameModel gameModel;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public GameScreen(OrthographicCamera camera, GameModel gameModel) {
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
        gameModel.update();
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
    }

    /**
     * the camera should follow the player character
     */
    private void cameraUpdate() {
        camera.position.set(new Vector3(gameModel.getPlayers().get(0).getPosition().x,gameModel.getPlayers().get(0).getPosition().y,0));
        camera.update();
    }

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
