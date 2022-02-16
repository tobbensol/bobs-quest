package core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static helper.Constants.PPM;

/**
 * the screen of the game, where everything is rendered onto and where all visual elements reside
 */
public class GameScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.setColor(Color.RED);
        this.world = new World( new Vector2( 0 , 0 ), false );
        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    /**
     * the game runs in real time and updating the game often is required for it to run smoothly
     */
    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * the camera should follow the player character
     */
    private void cameraUpdate() {
        camera.position.set(new Vector3(200,200,0));
        camera.update();
    }


    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render(float v) {
        this.update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end();
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
}
