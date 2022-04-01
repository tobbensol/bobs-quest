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
    private static final int MAX_VELOCITY = 2;
    private static final float X_VELOCITY = 0.35f;
    private static final float Y_VELOCITY = 1.3f;

    private final ArrayList<TextureRegion> frames;

    protected State currentState;
    protected State previousState;

    //TODO these should be in a parent class
    private boolean rightCollision = false;
    private boolean leftCollision = false;

    private boolean headCollision = false;
    private boolean onPlatform = false;


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
    }

    private void handlePlatform() {
        if (body.getLinearVelocity().y > 0) {
            playerCanGoThroughPlatforms(true);
        }
        if (body.getLinearVelocity().y < 0 && !onPlatform && previousState != State.FALLING) {
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
    public void jump(float delta) {
        if (grounded && (previousState != State.JUMPING) && (currentState != State.FALLING)) {
            applyCenterLinearImpulse(0, delta * Y_VELOCITY);
        }
    }

    public void drop() {
        if (onPlatform) {
            playerCanGoThroughPlatforms(true);
        }
        currentState = State.FALLING;
        this.body.applyForceToCenter(0,-Y_VELOCITY*10    , true);

    }

    @Override
    public void moveHorizontally(float delta, boolean isRight) {
        if (!rightCollision && isRight && this.body.getLinearVelocity().x <= MAX_VELOCITY) {
            applyCenterLinearImpulse(delta * X_VELOCITY, 0);
            facingRight = true;
        } else if (!leftCollision && !isRight && this.body.getLinearVelocity().x >= -MAX_VELOCITY) {
            applyCenterLinearImpulse(-delta * X_VELOCITY, 0);
            facingRight = false;
        }
    }

    private void applyCenterLinearImpulse(float x, float y) {
        this.body.applyLinearImpulse(new Vector2(x, y), this.body.getWorldCenter(), true);
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
        if (body.getLinearVelocity().y < 0 && grounded) {
            return State.SLIDING;
        }
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        if (body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        if (body.getLinearVelocity().x != 0 && previousState != State.JUMPING) { // Fixes bug when jumping up in the underside of the platform -> y = 0.
            grounded = true; // If y = 0 and x != 0, the player must be grounded. "can't jump after slide bug" appears if removed.
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
    public TextureRegion getFrame() {
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
