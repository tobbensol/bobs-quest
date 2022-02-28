package core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import controls.ArrowController;
import controls.CustomController;
import controls.WASDController;
import helper.Constants;
import helper.TiledMapHelper;
import objects.Player;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public World getWorld() {
        return world;
    }

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private GameContactListener gameContactListener;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;

    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;

    private Player player1;
    private Player player2;


    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World( new Vector2( 0 , -10f ), false );
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();

        backgroundLayer = tiledMapHelper.getBoardLayer("Background");
        playerLayer = tiledMapHelper.getBoardLayer("Player");

        player1 = new Player("Player1", "player_stick.png", this, 0, 400, 1, new ArrowController());
        player2 = new Player("Player2", "player_stick.png", this, 100, 500, 1, new WASDController());

    }

    /**
     * the game runs in real time and updating the game often is required for it to run smoothly
     */
    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        player1.update();
        player2.update();
    }

    /**
     * the camera should follow the player character
     */
    private void cameraUpdate() {
        camera.position.set(new Vector3(player1.getPosition().x,player1.getPosition().y,0));
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
        //TODO: Render player

        player1.render(batch);
        player2.render(batch);
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

    // This is just for testing.
    public Player getPlayer() {
        return player1;
    }
}
