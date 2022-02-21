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
import helper.TiledMapHelper;
import objects.Player;

import static helper.Constants.PPM;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;

    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;

    private Player player1;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World( new Vector2( 0 , 0 ), false );
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();

        backgroundLayer = tiledMapHelper.getBoardLayer("Background");
        playerLayer = tiledMapHelper.getBoardLayer("Player");

        player1 = new Player("Player1", "player_stick.png", 0, 128, 100, 100);
    }

    /**
     * the game runs in real time and updating the game often is required for it to run smoothly
     */
    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        keyInputs();

    }

    private void keyInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player1.setPosition(player1.getPosition().add(new Vector2(2, 0)));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player1.setPosition(player1.getPosition().add(new Vector2(-2, 0)));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player1.setPosition(player1.getPosition().add(new Vector2(0, -2)));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player1.setPosition(player1.getPosition().add(new Vector2(0, 2)));
        }
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
        batch.draw(player1.getTexture(), player1.getPosition().x, player1.getPosition().y);
        batch.end();

        // Debug renderer
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
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

    public World getWorld() {
        return world;
    }
}
