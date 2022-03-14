package model.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.helper.ContactType;

import java.util.ArrayList;

public class Player extends JumpableObject {
    private static final int MAX_VELOCITY = 2;
    private boolean sideCollision;
    private static final float X_VELOCITY = 0.35f;
    private static final float Y_VELOCITY = 1.3f;
    private int hp;

    public enum State {
        STANDING,
        WALKING,
        JUMPING,
        FALLING,
        DEAD
    }

    protected State currentState;
    protected State previousState;

    /*
    private TextureRegion standing;
    private TextureRegion walking;
    private TextureRegion jumping;
    private TextureRegion falling;
     */
    //private Animation runningAnimation;
    //private float stateTimer;

    private ArrayList<TextureRegion> frames;

    public Player(String name, String texturePath, GameModel gameModel, float x, float y, int density) {
        super(name, texturePath, gameModel, x, y, density, ContactType.PLAYER);
        hp = 100;
        currentState = State.STANDING;
        previousState = State.STANDING;
        //stateTimer = 0;

        /*
        frames = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i*64,0,64,64));
        }
        runningAnimation = new Animation(0.1f,frames);
        frames.clear();

         */

        frames = new ArrayList<>();
        for(int i = 0; i < getTexture().getWidth()/64; i++){
             frames.add(new TextureRegion(getTexture(),i*64,0,64, 64));
        }

        sideCollision = false;
    }
    @Override
    public void update() {
        super.update();
        previousState = currentState;
        currentState = getState();
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(gameModel.getDelta()), x, y, width, height);
    }

    @Override
    public void jump(float delta) {
        if (grounded && previousState != State.JUMPING) {
            applyCenterLinearImpulse(0, delta*Y_VELOCITY);
        }
    }

    @Override
    public void moveHorizontally(float delta, boolean isRight) {
        if (!checkSideCollisionInAir()) {
            if (isRight && this.body.getLinearVelocity().x <= MAX_VELOCITY) {
                applyCenterLinearImpulse(delta*X_VELOCITY, 0);
                facingRight = true;
            }
            else if (!isRight && this.body.getLinearVelocity().x >= -MAX_VELOCITY) {
                applyCenterLinearImpulse(-delta*X_VELOCITY, 0);
                facingRight = false;
            }
        }
    }

    private void applyCenterLinearImpulse(float x, float y) {
        this.body.applyLinearImpulse(new Vector2(x,y), this.body.getWorldCenter(), true);
    }

    private boolean checkSideCollisionInAir()  {
        return (currentState.equals(State.JUMPING) && sideCollision) || (currentState.equals(State.FALLING) && sideCollision);
    }

    public boolean setSideCollision(boolean value) {
        sideCollision = value;
        return sideCollision;
    }

    public State getCurrentState() {
        return currentState;
    }

    /**
     * @return the current state of the player.
     */
    public State getState() {
        if (previousState == State.DEAD) {
            return State.DEAD;
        }
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

    /**
     * This method returns the correct texture-region for the current state the player is in.
     * It also checks wherever it should flip the texture based on the direction of movement of the player.
     *
     * @return the correct texture-region for the current state the player is in.
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        // Specify which texture region corresponding to which state.
        TextureRegion region = switch (currentState) {
            case JUMPING -> frames.get(4);
            case FALLING -> frames.get(5);
            case WALKING ->
                    //region = runningAnimation.getKeyFrame(stateTimer, true);
                    frames.get(1);
            case DEAD -> frames.get(6);
            // Default -> STANDING
            default -> frames.get(0);
        };
        if (!facingRight && !region.isFlipX()) {
            region.flip(true,false);
        }
        else if (facingRight && region.isFlipX()) {
            region.flip(true,false);
        }

        //stateTimer = currentState == previousState ? stateTimer + dt : 0;

        return region;
    }

    public boolean isDead() {
        return getState() == State.DEAD;
    }

    public void setDead() {
        if (previousState == State.DEAD) {
            return;
        }
        hp = -1;
        previousState = currentState;
        currentState = State.DEAD;
        // Death "animation"
        jump(2.0f); // TODO: Make player fall through ground as well
    }

    public void takeDamage(int amount){
        // Player doesn't take damage if dead
        if (currentState == State.DEAD) {
            return;
        }
        hp -= amount;
        if (hp <= 0) {
            setDead();
        }
    }

}
