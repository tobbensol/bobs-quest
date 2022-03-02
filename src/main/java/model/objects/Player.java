package model.objects;

import com.badlogic.gdx.math.Vector2;
import view.GameScreen;
import model.helper.ContactType;

public class Player extends Object {
    private static final int MAX_VELOCITY = 2;
    private boolean sideCollision;
    private static final float X_VELOCITY = 0.35f;
    private static final float Y_VELOCITY = 1.3f;

    public Player(String name, String texturePath, GameScreen gameScreen, float x, float y, int density) {
        super(name, texturePath, gameScreen, x, y, density, ContactType.PLAYER);
        sideCollision = false;
    }

    public void jump(float delta) {
        if (grounded && previousState != State.JUMPING) {
            applyCenterLinearImpulse(0, delta*Y_VELOCITY);
        }
    }

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


    public boolean checkSideCollisionInAir()  {
        return (currentState.equals(State.JUMPING) && sideCollision) || (currentState.equals(State.FALLING) && sideCollision);
    }

    public boolean setSideCollision(boolean value) {
        sideCollision = value;
        return sideCollision;
    }

    public boolean setGrounded(boolean value) {
        grounded = value;
        return grounded;
    }
}
