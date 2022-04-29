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

    private final TextureRegion[] frames;

    protected State currentState;
    protected State previousState;

    private boolean rightCollision = false;
    private boolean leftCollision = false;
    private boolean onPlatform = false;
    private boolean canDrop = true;

    private final Vector2 cumulativeForces = new Vector2(0, 0);

    private int hp;

    private final Animation<TextureRegion> walkingAnimation;
    private float stateTime;

    public Player(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Player.class).size() + 1), level, x, y, 1.1f, ContactType.PLAYER, Constants.PLAYER_BIT, Constants.PLAYER_MASK_BITS);
        texture = new Texture("Multi_Platformer_Tileset_v2/Players/Small_Mario.png");

        hp = 100;
        currentState = State.STANDING;
        previousState = State.STANDING;

        frames = TextureRegion.split(getTexture(), Constants.TILE_SIZE, Constants.TILE_SIZE)[0];
        stateTime = 0;
        walkingAnimation = new Animation<>(0.166f, frames[1], frames[2], frames[3]);
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
        if (currentState == State.DEAD || (grounded && body.getLinearVelocity().y == 0 && !onPlatform) || !canDrop) {
            return;
        }

        changeMaskBit(true, Constants.PLATFORM_BIT);
        currentState = State.FALLING;

        if (!grounded){
            this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
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
        batch.draw(getFrame(), x - width/2, y - height/2, width, height);
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
     * Animates WALKING state.
     *
     * @return the correct texture-region for the current state the player is in.
     */
    private TextureRegion getFrame() {
        // Specify which texture region corresponding to which state.
        TextureRegion region = switch (currentState) {
            case JUMPING -> frames[5];
            case FALLING, SLIDING -> frames[7];
            case DEAD -> frames[13];
            default -> frames[0];
        };

        // Animation for WALKING
        if (currentState == State.WALKING) {
            stateTime += Gdx.graphics.getDeltaTime();
            region = walkingAnimation.getKeyFrame(stateTime, true);
        } else {
            stateTime = 0;
        }

        if (facingRight == region.isFlipX()) {
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
