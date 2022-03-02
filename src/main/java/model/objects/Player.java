package model.objects;

import com.badlogic.gdx.Gdx;
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
        //frames.clear();


        //standing = new TextureRegion(getTexture(),0,0,64,64);
        //walking = new TextureRegion(getTexture(),3*64,0,64,64);
        //jumping = new TextureRegion(getTexture(),4*64,0,64,64);
        //falling = new TextureRegion(getTexture(),5*64,0,64,64);

        //System.out.println(frames.size);
        //TextureRegion[][] frames = new TextureRegion(getTexture()).split(64,64);

        sideCollision = false;
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

    public boolean checkSideCollisionInAir()  {
        return (currentState.equals(State.JUMPING) && sideCollision) || (currentState.equals(State.FALLING) && sideCollision);
    }

    public boolean setSideCollision(boolean value) {
        sideCollision = value;
        return sideCollision;
    }


    /**
     * This function returns the correct texture-region for the current state the player is in.
     * It also checks wherever it should flip the texture based on the direction of movement of the player.
     *
     * @return the correct texture-region for the current state the player is in.
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        // Specify which texture region corresponding to which state.
        TextureRegion region;
        switch (currentState) {
            case STANDING:
                region = frames.get(0);
                break;
            case JUMPING:
                region = frames.get(4);
                break;
            case FALLING:
                region = frames.get(5);
                break;
            case WALKING:
                //region = runningAnimation.getKeyFrame(stateTimer, true);
                region = frames.get(1);
                break;
            default:
                region = frames.get(0);
        };

        if ((body.getLinearVelocity().x < 0 || !facingRight) && !region.isFlipX()) {
            region.flip(true, false);
            facingRight = false;
        }
        else if ((body.getLinearVelocity().x > 0 || facingRight) && region.isFlipX()) {
            region.flip(true, false);
            facingRight = true;
        }
        else {
            region.flip(false,false);
        }
        //stateTimer = currentState == previousState ? stateTimer + dt : 0;

        return region;
    }





}
