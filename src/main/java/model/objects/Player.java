package model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Player extends JumpableObject {
    private static final float MAX_WALKING_VELOCITY = 4.2f;
    private static final float MAX_X_VELOCITY = 14f;
    private static final float MAX_Y_VELOCITY = 28f;
    private static final float X_MOVEMENT_IMPULSE = 15f;
    private static final float Y_MOVEMENT_IMPULSE = 260f;
    private static final float DROPPING_SCALE = 0.1f;
    private static final float X_DAMPING_SCALE = 1f;
    private static final float JUMP_X_DAMPING_SCALE = 0.2f;
    private static final float Y_DAMPING_SCALE = 0.27f;
    private final Vector2 cumulativeForces = new Vector2(0, 0);
    private final Map<State, Animation<TextureRegion>> animationMap;
    protected State currentState;
    protected State previousState;
    private boolean rightCollision = false;
    private boolean leftCollision = false;
    private boolean onPlatform = false;
    private boolean canDrop = true;
    private int hp;
    private float stateTime;

    public Player(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Player.class).size() + 1), level, x, y, 1.1f, ContactType.PLAYER, Constants.PLAYER_BIT, Constants.PLAYER_MASK_BITS);
        texture = new Texture("Multi_Platformer_Tileset_v2/Players/Adventurer_Sprite_Sheet.png");

        hp = 100;
        currentState = State.STANDING;
        previousState = State.STANDING;

        /*
         * Which row corresponds to which state in Adventurer_Sprite_Sheet:
         * STANDING -> frames[0]
         * WALKING -> frames[1]
         * JUMPING -> frames[5]
         * takeDamage() -> frames[6] // TODO: Animate getting hurt
         * DEAD -> frames[7]
         * drop() -> frames[12]
         * */
        TextureRegion[][] frames = TextureRegion.split(getTexture(), Constants.TILE_SIZE, Constants.TILE_SIZE);
        animationMap = new HashMap<>();
        animationMap.put(State.STANDING, new Animation<>(0.384f, frames[0])); // 5s animation duration
        animationMap.put(State.WALKING, new Animation<>(0.0625f, Arrays.copyOf(frames[1], 8))); // 0.5s animation duration
        animationMap.put(State.JUMPING, new Animation<>(0.166f, Arrays.copyOf(frames[2], 6))); // 1.0s animation duration
        animationMap.put(State.FALLING, new Animation<>(0f, frames[5][1])); // 0s animation duration
        animationMap.put(State.SLIDING, new Animation<>(0.166f, Arrays.copyOfRange(frames[5], 1, 4))); // 0.5s animation duration
        animationMap.put(State.DEAD, new Animation<>(0.0714f, Arrays.copyOf(frames[4], 7))); // 0.5s animation duration

        stateTime = 0;
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
            body.setLinearVelocity(Math.copySign(MAX_X_VELOCITY, body.getLinearVelocity().x), body.getLinearVelocity().y);
        }
        if (Math.abs(body.getLinearVelocity().y) > MAX_Y_VELOCITY) {
            body.setLinearVelocity(body.getLinearVelocity().x, Math.copySign(MAX_Y_VELOCITY, body.getLinearVelocity().y));
        }
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
            canDrop = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    canJump = true;
                    canDrop = true;
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
        if (currentState == State.DEAD || (grounded && body.getLinearVelocity().y == 0 && !onPlatform) || !canDrop) {
            return;
        }

        changeMaskBit(true, Constants.PLATFORM_BIT);
        currentState = State.FALLING;

        if (!grounded) {
            this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
            currentState = State.FALLING;
        } else {
            currentState = State.SLIDING;
        }
        cumulativeForces.add(0, -Y_MOVEMENT_IMPULSE * DROPPING_SCALE);

        if (previousState != State.FALLING && !grounded || onPlatform) {
            level.getAudioHelper().getSoundEffect("drop3").play(level.getAudioHelper().getSoundEffectsVolume());
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
        batch.draw(getFrame(), x - width / 2, y - height / 2, width, height);
    }

    public void setLeftCollision(boolean value) {
        this.leftCollision = value;
    }

    public void setRightCollision(boolean value) {
        this.rightCollision = value;
    }

    public void setOnPlatform(boolean value) {
        this.onPlatform = value;
    }

    public void setCanDrop(boolean canDrop) {
        this.canDrop = canDrop;
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
        } else if (body.getLinearVelocity().y < -1.5f) {
            tempState = State.FALLING;
        } else if (body.getLinearVelocity().x != 0 && previousState != State.JUMPING) { // Fixes bug when jumping up in the underside of the platform -> y = 0.
            tempState = State.WALKING;
        }
        currentState = tempState;
    }

    @Override
    protected TextureRegion getFrame() {
        // Specify which texture region corresponding to which state.
        stateTime = currentState == previousState ? stateTime + Gdx.graphics.getDeltaTime() : 0;
        TextureRegion region = switch (currentState) { // TODO: After deciding which states should animate, merge switch cases
            case WALKING -> getKeyFrame(true);
            case JUMPING -> getKeyFrame(false);
            case FALLING, SLIDING -> getKeyFrame(true);
            case DEAD -> getKeyFrame(false);
            default -> getKeyFrame(true);
        };

        flipRegionHorizontally(region);
        return region;
    }

    private TextureRegion getKeyFrame(boolean looping) {
        return animationMap.get(currentState).getKeyFrame(stateTime, looping);
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
        body.setLinearVelocity(0, 5);
        level.getAudioHelper().getSoundEffect("deathScream").play(level.getAudioHelper().getSoundEffectsVolume());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isDestroyed = true;
            }
        }, 1.5f);
    }

    public void takeDamage(int amount) {
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

        level.getAudioHelper().getSoundEffect("hit2").play(level.getAudioHelper().getSoundEffectsVolume());
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
