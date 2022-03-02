package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import model.GameModel;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class StaticObject {
    final private String name;
    final private Texture texture;
    protected int width, height;
    protected Body body;
    protected float x, y, velY;

    protected boolean facingRight;
    protected boolean grounded;

    public enum State {
        STANDING,
        WALKING,
        JUMPING,
        FALLING
    }

    protected State currentState;
    protected State previousState;


    public StaticObject(String name, String texturePath, GameModel gameModel, float x, float y, int density, ContactType contactType) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.x = x;
        this.y = y;
        //this.width = texture.getWidth();
        //this.height = texture.getHeight();
        this.width = 64;
        this.height = 64;
        currentState = State.STANDING;
        previousState = State.STANDING;

        this.body = BodyHelper.BodyHelper(x, y, width, height, density, gameModel.getWorld(), contactType);
        facingRight = true;
        grounded = false;
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM - (width / 2);
        y = body.getPosition().y * Constants.PPM - (height / 2);

        velY = body.getLinearVelocity().len();

        previousState = currentState;
        currentState = getState();
    }

    public void render(SpriteBatch batch) {
        if (!facingRight) {
            batch.draw(texture,x,y,width,height,0, 0, width, height, true, false);
        } else {
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
            return State.WALKING;
        }
        else {
            return State.STANDING;
        }
    }

    public Body getBody() {
        return body;
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

    public void move(Vector2 vector2) {
        body.applyForceToCenter(vector2, true);
    }
}
