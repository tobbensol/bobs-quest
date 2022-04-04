package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

import java.util.ArrayList;

public class Player extends JumpableObject {
    private static final int MAX_VELOCITY = 4;
    private static final float X_VELOCITY = 5f;
    private static final float Y_VELOCITY = 150f;
    private final float DROPPING_SCALE = 0.2f;
    private final float DAMPING_SCALE = 1.2f;

    private final ArrayList<TextureRegion> frames;

    protected State currentState;
    protected State previousState;

    //TODO these should be in a parent class
    private boolean rightCollision = false;
    private boolean leftCollision = false;

    private boolean headCollision = false;
    private boolean onPlatform = false;

    private Vector2 acceleration = new Vector2(0,0);


    private int hp;

    public Player(String name, Level level, float x, float y) {
        super(name + " " + (level.getPlayers().size() + 1), level, x, y, 0.8f, ContactType.PLAYER, Constants.PLAYER_BIT, Constants.PLAYER_MASK_BITS);
        texturePath = "Multi_Platformer_Tileset_v2/Players/Small_Mario.png";
        texture = new Texture(texturePath);

        hp = 100;
        currentState = State.STANDING;
        previousState = State.STANDING;

        frames = new ArrayList<>();
        for (int i = 0; i < getTexture().getWidth() / Constants.TILE_SIZE; i++) {
            frames.add(new TextureRegion(getTexture(), i * Constants.TILE_SIZE, 0, Constants.TILE_SIZE, Constants.TILE_SIZE));
        }
    }

    @Override
    public void update() {
        super.update();
        previousState = currentState;
        currentState = getState();
        handlePlatform();
        damping();
        //System.out.println(currentState);
        //System.out.println(grounded);
        //System.out.println(body.getLinearVelocity().y);
        //System.out.println(grounded);

        if (acceleration.x > X_VELOCITY) {
            acceleration.x = X_VELOCITY;
        }
        if (acceleration.y > Y_VELOCITY) {
            acceleration.y = Y_VELOCITY;
        }


        if (acceleration.y > 0)
            System.out.println(acceleration);



        this.body.applyForceToCenter(acceleration,true); // Same force applied every time, Some times different heights, only when we use deltatime.... Should we use deltatime anyway here?
        acceleration.scl(0);

    }


    private void damping() {
        Vector2 currentSpeed = this.body.getLinearVelocity();
        if (grounded) {
            acceleration.add(-currentSpeed.x * DAMPING_SCALE, 0);
        }


    }

    private void handlePlatform() {
        if (body.getLinearVelocity().y > 0.5) {
            playerCanGoThroughPlatforms(true);
        }
        if (body.getLinearVelocity().y < -0.5 && !onPlatform && previousState != State.FALLING) {
            playerCanGoThroughPlatforms(false);
        }
    }

    private void playerCanGoThroughPlatforms(boolean value) {
        if (value) {
            BodyHelper.changeFilterData(body, Constants.PLAYER_PASSING_THROUGH_PLATFORM_BIT);
        } else {
            BodyHelper.changeFilterData(body, Constants.PLAYER_BIT);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x, y, width, height);
    }


    @Override
    public void jump() {
        if (grounded && previousState != State.JUMPING && previousState != State.FALLING) {
            acceleration.add(0,Y_VELOCITY); // Jump from ground takes two iterations.
            grounded = false;
        }
    }

    public void drop() {
        if (onPlatform) {
            playerCanGoThroughPlatforms(true);
        }
        currentState = State.FALLING;
        acceleration.add(0,-Y_VELOCITY * DROPPING_SCALE);
    }

    @Override
    public void moveHorizontally(boolean isRight) {
        if (!rightCollision && isRight && this.body.getLinearVelocity().x <= MAX_VELOCITY) {
            //applyForceToCenter(X_VELOCITY, 0);
            acceleration.add(X_VELOCITY,0);
            facingRight = true;
        } else if (!leftCollision && !isRight && this.body.getLinearVelocity().x >= -MAX_VELOCITY) {
            //applyForceToCenter(-X_VELOCITY, 0);
            acceleration.add(-X_VELOCITY,0);
            facingRight = false;
        }
    }

    private void applyForceToCenter(float x, float y) {
        this.body.applyForceToCenter(x, y, true);
    }

    public void setLeftCollision(boolean value) {
        this.leftCollision = value;
    }

    public void setRightCollision(boolean value) {
        this.rightCollision = value;
    }

    public void setHeadCollision(boolean value) {
        this.headCollision = value;
    }

    public void setOnPlatform(boolean value) {
        this.onPlatform = value;
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
        if (body.getLinearVelocity().y < -0.5 && grounded) {
            return State.SLIDING; // Seperate Sliding from walking? drop to slide????
        }
        if (body.getLinearVelocity().y > 0.5 && grounded) {
            return State.WALKING;
        }
        if ((body.getLinearVelocity().y > 0 && !grounded) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        if (body.getLinearVelocity().y < -0.5) {
            return State.FALLING;
        }
        if (body.getLinearVelocity().x != 0 && previousState != State.JUMPING) { // Fixes bug when jumping up in the underside of the platform -> y = 0.
            return State.WALKING;
        }
        return State.STANDING;
    }

    /**
     * This method returns the correct texture-region for the current state the player is in.
     * It also checks wherever it should flip the texture based on the direction of movement of the player.
     *
     * @return the correct texture-region for the current state the player is in.
     */
    private TextureRegion getFrame() {
        currentState = getState();

        // Specify which texture region corresponding to which state.
        TextureRegion region = switch (currentState) {
            case JUMPING -> frames.get(5);
            case FALLING, SLIDING -> frames.get(7);
            case WALKING -> frames.get(3);
            case DEAD -> frames.get(13);
            default -> frames.get(0);
        };

        if (!facingRight && !region.isFlipX()) {
            region.flip(true, false);
        } else if (facingRight && region.isFlipX()) {
            region.flip(true, false);
        }

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
        BodyHelper.changeFilterData(body, Constants.DESTROYED_BIT, Constants.DESTROYED_MASK_BITS);
        // Death "animation"
        body.setLinearVelocity(0, 5); //TODO: Make player fall through ground as well
    }

    public void takeDamage(int amount) {
        // Player doesn't take damage if dead
        if (currentState == State.DEAD) {
            return;
        }
        hp -= amount;
        if (hp <= 0) {
            setDead();
        }
        System.out.println(this + ": " + hp);
    }

    public void increaseHealth(int amount) {
        if (currentState == State.DEAD) {
            return;
        }
        hp += amount;
        if (hp > 100) {
            hp = 100;
        }
    }

    public int getHp() {
        return hp;
    }

    public enum State {
        STANDING,
        WALKING,
        JUMPING,
        FALLING,
        SLIDING,
        DEAD
    }

}
