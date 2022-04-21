package model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.g2d.TextureRegion.split;

public class Player extends JumpableObject {
    private static final float MAX_WALKING_VELOCITY = 4.2f;
    //TODO tweek max velocities
    private static final float MAX_X_VELOCITY = 14f;
    private static final float MAX_Y_VELOCITY = 14f;
    private static final float X_MOVEMENT_IMPULSE = 15f;
    private static final float Y_MOVEMENT_IMPULSE = 250f;
    private static final float DROPPING_SCALE = 0.2f;
    private static final float X_DAMPING_SCALE = 1f;
    private static final float JUMP_X_DAMPING_SCALE = 0.2f;
    private static final float Y_DAMPING_SCALE = 0.27f;

    private final ArrayList<TextureRegion> frames;

    protected State currentState;
    protected State previousState;

    private boolean rightCollision = false;
    private boolean leftCollision = false;

    private boolean headCollision = false;
    private boolean onPlatform = false;

    private final Vector2 cumulativeForces = new Vector2(0, 0);

    private int hp;

    private Animation<TextureRegion> walkingAnimation;
    private float stateTime;

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

        Array<TextureRegion> walkingFrames = new Array<>();
        walkingFrames.add(frames.get(3));
        walkingFrames.add(frames.get(13));

        // If we want an animation that is m seconds long and we have n frames, then time per frame is m/n.
        // With animation duration at 1 second (m=1) and 2 frames (n=2), the time per frame is 1/2.
        stateTime = 0;
        walkingAnimation = new Animation<>(0.5f, walkingFrames);

    }

    @Override
    public void update() {
        super.update();

        previousState = currentState;
        setState();
        handlePlatform();
        groundedDamping();
        jumpDamping();
        checkIfMaxVelocity();

        this.body.applyForceToCenter(cumulativeForces, true);
        cumulativeForces.scl(0);
    }

    private void checkIfMaxVelocity() {
        if (Math.abs(body.getLinearVelocity().x) > MAX_X_VELOCITY) {
            body.setLinearVelocity(MAX_X_VELOCITY, body.getLinearVelocity().y);
        }
//        if (Math.abs(body.getLinearVelocity().y) > MAX_Y_VELOCITY) {
//            body.setLinearVelocity(body.getLinearVelocity().x, MAX_Y_VELOCITY);
//        }
    }

    @Override
    public void moveHorizontally(boolean isRight) {
        if (!rightCollision && isRight && this.body.getLinearVelocity().x <= MAX_WALKING_VELOCITY) {
            cumulativeForces.add(X_MOVEMENT_IMPULSE, 0);
            facingRight = true;
        } else if (!leftCollision && !isRight && this.body.getLinearVelocity().x >= -MAX_WALKING_VELOCITY) {
            cumulativeForces.add(-X_MOVEMENT_IMPULSE, 0);
            facingRight = false;
        }
    }

    private void groundedDamping() {
        Vector2 currentSpeed = this.body.getLinearVelocity();
        if (grounded) {
            cumulativeForces.add(-currentSpeed.x * X_DAMPING_SCALE, 0);
        }
    }

    @Override
    public void jump() {
        if (grounded && previousState != State.JUMPING && previousState != State.FALLING) {
            cumulativeForces.add(0, Y_MOVEMENT_IMPULSE);
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            canJump = false;
            updateGrounded();
            level.getAudioHelper().getSoundEffect("jump").play(level.getAudioHelper().getSoundEffectsVolume());
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    canJump = true;
                }
            }, 0.1f);
        }
    }

    private void jumpDamping() {
        Vector2 currentSpeed = this.body.getLinearVelocity();
        if (!grounded) {
            cumulativeForces.add(-currentSpeed.x * JUMP_X_DAMPING_SCALE, -currentSpeed.y * Y_DAMPING_SCALE);
        }
    }

    public void drop() {
        if (currentState == State.DEAD || (grounded && body.getLinearVelocity().y == 0 && !onPlatform)) {
            return;
        }

        changeMaskBit(true, Constants.PLATFORM_BIT);
        currentState = State.FALLING;

        if (!grounded){
            this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
        }
        cumulativeForces.add(0, -Y_MOVEMENT_IMPULSE * DROPPING_SCALE);

        if (previousState != State.FALLING && !grounded || onPlatform) {
            level.getAudioHelper().getSoundEffect("drop").play(level.getAudioHelper().getSoundEffectsVolume());
        }

    }

    private void handlePlatform() {
        if (body.getLinearVelocity().y > 0.5) {
            changeMaskBit(true, Constants.PLATFORM_BIT);
        }
        if (body.getLinearVelocity().y < -0.5 && !onPlatform && previousState != State.FALLING) {
            changeMaskBit(false, Constants.PLATFORM_BIT);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x - width/2, y - height/2, width, height);
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

    public void setState() {
        State tempState = State.STANDING;
        if (previousState == State.DEAD) {
            tempState = State.DEAD;
        } else if (body.getLinearVelocity().y < -1.5f && grounded) {
            tempState = State.SLIDING;
        } else if ((body.getLinearVelocity().y > 0 && !grounded) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            tempState = State.JUMPING;
        } else if (body.getLinearVelocity().y < - 1.5f) {
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
            case DEAD -> frames.get(13);
            default -> frames.get(0);
        };

        if (currentState == State.WALKING) {
            stateTime += Gdx.graphics.getDeltaTime();
            region = walkingAnimation.getKeyFrame(stateTime, true);
        } else {
            stateTime = 0;
        }

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

    public void setDead() {
        if (previousState == State.DEAD) {
            return;
        }
        hp = -1;
        previousState = currentState;
        currentState = State.DEAD;
        maskBits = Constants.DESTROYED_MASK_BITS;
        bit = Constants.DESTROYED_BIT;
        BodyHelper.changeFilterData(body, bit, maskBits);
        // Death "animation"
        body.setLinearVelocity(0, 5);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isDestroyed = true;
            }
        }, 1.5f);
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
        changeMaskBit(true, Constants.ENEMY_BIT);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                changeMaskBit(false, Constants.ENEMY_BIT);
            }
        }, 0.5f);

        System.out.println(this + ": " + hp);
        level.getAudioHelper().getSoundEffect("hit").play(level.getAudioHelper().getSoundEffectsVolume());
    }

    public void increaseHealth(int amount) {
        if (currentState == State.DEAD) {
            return;
        }
        hp += amount;
        hp = Math.min(100, hp);
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
