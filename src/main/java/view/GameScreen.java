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
import model.objects.Player;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {
    private final int numPlayers = 2; // TODO: Variable number of players
    private final int numControllers = 2;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;


    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World( new Vector2( 0 , -10f ), false );
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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        for (int i = 0; i < numPlayers; i++) {
            controllers.get(i).inputListener(players.get(i));
        }
        for (Player player : players) {
            player.update();
        }
//        controller.inputListener(player1);
//        player1.update();
//        player2.update();
    }

    /**
     * the camera should follow the player character
     */
    private void cameraUpdate() {
        camera.position.set(new Vector3(getPlayer().getPosition().x,getPlayer().getPosition().y,0));
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

        for (Player player : players) {
            player.render(batch);
        }
//        player1.render(batch);
//        player2.render(batch);

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));
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

    public List<Player> getPlayers() {
        return players;
    }

    // This is just for testing.
    public Player getPlayer() {
        return players.get(0);
    }
}
