package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

import java.util.ArrayList;

public class Player extends JumpableObject {
    private static final float MAX_VELOCITY = 4.2f;
    private static final float X_VELOCITY = 15f;
    private static final float Y_VELOCITY = 250f;
    private static final float DROPPING_SCALE = 0.1f;
    private static final float X_DAMPING_SCALE = 1f;
    private static final float JUMP_X_DAMPING_SCALE = 0.2f;
    private static final float Y_DAMPING_SCALE = 0.27f;

    private final ArrayList<TextureRegion> frames;

    private short maskBit = Constants.PLAYER_MASK_BITS;

    protected State currentState;
    protected State previousState;
    private boolean frozen = false;

    //TODO these should be in a parent class
    private boolean rightCollision = false;
    private boolean leftCollision = false;

    private boolean headCollision = false;
    private boolean onPlatform = false;

    private final Vector2 cumulativeForces = new Vector2(0, 0);


    private int hp;

    public Player(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Player.class).size() + 1), level, x, y, 1.1f, ContactType.PLAYER, Constants.PLAYER_BIT, Constants.PLAYER_MASK_BITS);
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

    /**
     * Constructor for testing
     *
     * @param level - level to be placed in
     * @param x     - horizontal position
     * @param y     - vertical position
     */
    public Player(Level level, float x, float y) {
        super("Test", level, x, y, 0.8f, ContactType.PLAYER, Constants.PLAYER_BIT, Constants.PLAYER_MASK_BITS);

        hp = 100;
        currentState = State.STANDING;
        previousState = State.STANDING;

        frames = new ArrayList<>();
    }

    @Override
    public void update() {
        super.update();

        previousState = currentState;
        setState();
        handlePlatform();
        groundedDamping();
        jumpDamping();

        if (cumulativeForces.x > X_VELOCITY) {
            cumulativeForces.x = X_VELOCITY;
        }
        if (cumulativeForces.y > Y_VELOCITY) {
            cumulativeForces.y = Y_VELOCITY;
        }

        this.body.applyForceToCenter(cumulativeForces, true);
        cumulativeForces.scl(0);
    }


    private void groundedDamping() {
        Vector2 currentSpeed = this.body.getLinearVelocity();
        if (grounded) {
            cumulativeForces.add(-currentSpeed.x * X_DAMPING_SCALE, 0);
        }
    }

    private void jumpDamping() {
        Vector2 currentSpeed = this.body.getLinearVelocity();
        if (!grounded) {
            cumulativeForces.add(-currentSpeed.x * JUMP_X_DAMPING_SCALE, -currentSpeed.y * Y_DAMPING_SCALE);
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
            BodyHelper.changeFilterData(body, Constants.PLAYER_BIT, (short) (maskBit ^ Constants.PLATFORM_BIT));
        } else {
            BodyHelper.changeFilterData(body, Constants.PLAYER_BIT, (short) (maskBit | Constants.PLATFORM_BIT));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x, y, width, height);
    }


    @Override
    public void jump() {
        if (grounded && previousState != State.JUMPING && previousState != State.FALLING) {
            cumulativeForces.add(0, Y_VELOCITY);
            canJump = false;
            updateGrouned();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    canJump = true;
                }
            }, 0.1f);
        }
    }

    public void drop() {
        if (currentState == State.DEAD) {
            return;
        }
        if (onPlatform) {
            playerCanGoThroughPlatforms(true);
        }
        currentState = State.FALLING;

        this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
        cumulativeForces.add(0, -Y_VELOCITY * DROPPING_SCALE);
    }

    @Override
    public void moveHorizontally(boolean isRight) {
        if (!rightCollision && isRight && this.body.getLinearVelocity().x <= MAX_VELOCITY) {
            cumulativeForces.add(X_VELOCITY, 0);
            facingRight = true;
        } else if (!leftCollision && !isRight && this.body.getLinearVelocity().x >= -MAX_VELOCITY) {
            cumulativeForces.add(-X_VELOCITY, 0);
            facingRight = false;
        }
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
    public void setState() {
        State tempState = State.STANDING;
        if (previousState == State.DEAD) {
            tempState = State.DEAD;
        } else if (body.getLinearVelocity().y < -0.5 && grounded) {
            tempState = State.SLIDING;
        } else if (body.getLinearVelocity().y > 0.5 && grounded) {
            tempState = State.WALKING;
        } else if ((body.getLinearVelocity().y > 0 && !grounded) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            tempState = State.JUMPING;
        } else if (body.getLinearVelocity().y < -0.5) {
            tempState = State.FALLING;
        } else if (body.getLinearVelocity().x != 0 && previousState != State.JUMPING) { // Fixes bug when jumping up in the underside of the platform -> y = 0.
            tempState = State.WALKING;
        }
        currentState = tempState;
    }

    /**
     * This method returns the correct texture-region for the current state the player is in.
     * It also checks wherever it should flip the texture based on the direction of movement of the player.
     *
     * @return the correct texture-region for the current state the player is in.
     */
    private TextureRegion getFrame() {
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
        return getCurrentState() == State.DEAD;
    }

    public boolean getFrozen() {
        return frozen;
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
        body.setLinearVelocity(0, 5);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                frozen = true;
            }
        }, 1.2f);
    }

    public void takeDamage(int amount) {
        // Player doesn't take damage if dead
        BodyHelper.changeFilterData(body, Constants.PLAYER_BIT, (short) (maskBit ^ Constants.ENEMY_BIT));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                BodyHelper.changeFilterData(body, Constants.PLAYER_BIT, (short) (maskBit | Constants.ENEMY_BIT));
            }
        }, 0.5f);
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
