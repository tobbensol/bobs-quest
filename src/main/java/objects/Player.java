package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import core.GameScreen;
import helper.BodyHelper;
import helper.Constants;

public class Player {

    final private String name;
    final private Texture texture;
    private int width, height;
    private Body body;
    private float x, y, speed, velY;



//    private boolean facingRight;
//    private boolean grounded;

//    enum State {
//        IDLE,
//        WALKING,
//        JUMPING
//    }

    public Player(String name, String texturePath, GameScreen gameScreen, float x, float y, int density) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();

        this.body = BodyHelper.BodyHelper(x, y, width, height, density, gameScreen.getWorld());
//        facingRight = true;
//        grounded = true;
    }


    public String getName() {
        return name;
    }


    public Vector2 getPosition() {
        return body.getPosition().scl(Constants.PPM);
    }


    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setAnimation(int animation) {
        // TODO: Implement
    }

    public void move(Vector2 vector2){
        body.applyForceToCenter(vector2, true);
    }

}
