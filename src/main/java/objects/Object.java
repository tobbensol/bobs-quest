package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.GameScreen;
import helper.BodyHelper;
import helper.Constants;
import helper.ContactType;
import org.lwjgl.system.CallbackI;

public abstract class Object {
    final private String name;
    final private Texture texture;
    private int width, height;
    protected Body body;
    protected float x, y, speed, velY;
    private GameScreen gameScreen;


    protected boolean facingRight;
    protected boolean grounded;

    public enum State {
        STANDING,
        WALKING,
        JUMPING,
        FALLING
    }
    public State currentState;
    public State previousState;


    public Object(String name, String texturePath, GameScreen gameScreen, float x, float y, int density, ContactType contactType) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.gameScreen = gameScreen;
        currentState = State.STANDING;
        previousState = State.STANDING;

        this.body = BodyHelper.BodyHelper(x, y, width, height, density, gameScreen.getWorld(), contactType);
        facingRight = true;
        grounded = false;
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM - (width / 2);
        y = body.getPosition().y * Constants.PPM - (height / 2);

        velY = body.getLinearVelocity().len();
        //velY = 0;


        previousState = currentState;
        currentState = getState();
        //System.out.println(currentState);
    }


    public void render(SpriteBatch batch) {
        if (!facingRight) {
            batch.draw(texture,x,y,width,height,0, 0, width, height, true, false);
        }
        else {
            batch.draw(texture,x,y,width,height);
        }
    }

    public String getName() {
        return name;
    }


    public Vector2 getPosition() {
        return body.getPosition().scl(Constants.PPM);
    }

    public State getState() {
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        else if (body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        else if (body.getLinearVelocity().x != 0) {
            return State.STANDING;
        }
        else  {
            return State.STANDING;
        }
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
