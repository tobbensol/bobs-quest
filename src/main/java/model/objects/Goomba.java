package model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public class Goomba extends DynamicObject implements Enemy {

    private static final float X_VELOCITY = 3.2f;
    private static final int attack = 40;
    private final TextureRegion[][] frames;
    private final Animation<TextureRegion> walkingAnimation;
    private final Animation<TextureRegion> attackAnimation;
    private int numMoves;
    private boolean playerNearby = false;
    private Vector2 playerPosition;
    private float stateTime;
    private boolean isDead;

    public Goomba(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Goomba.class).size() + 1), level, x, y, 1.1f, ContactType.ENEMY, Constants.ENEMY_BIT, Constants.GOOMBA_MASK_BITS);
        texture = new Texture("Multi_Platformer_Tileset_v2/Enemies/Rat_Sprite_Tileset.png");

        frames = TextureRegion.split(texture, Constants.TILE_SIZE, Constants.TILE_SIZE);
        walkingAnimation = new Animation<>(0.166f, frames[0]);
        attackAnimation = new Animation<>(0.166f, frames[1]);

        numMoves = 0;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public void onHit() {
        setDead();
        level.getAudioHelper().getSoundEffect("goombaDeath").play(level.getAudioHelper().getSoundEffectsVolume());
    }

    @Override
    public void update() {
        super.update();

        goombaMovement();
    }

    private void goombaMovement() {
        if (playerNearby) {
            moveHorizontally(playerPosition.x > x);
        } else {
            int range = 125;
            if (numMoves > 0 && numMoves < range) {
                moveHorizontally(false);
            }
            if (numMoves == range) {
                numMoves = -range;
            }
            if (numMoves < 0) {
                moveHorizontally(true);
            }
            numMoves++;
        }
    }

    @Override
    public void moveHorizontally(boolean isRight) {
        if (isRight) {
            body.applyLinearImpulse(new Vector2(X_VELOCITY, 0), body.getWorldCenter(), true);
            facingRight = true;
        } else {
            body.applyLinearImpulse(new Vector2(-X_VELOCITY, 0), body.getWorldCenter(), true);
            facingRight = false;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x - width / 2, y - height / 2, width, height);
    }

    @Override
    protected TextureRegion getFrame() {
        TextureRegion region;
        if (isDead) {
            return frames[2][0];
        }

        stateTime += Gdx.graphics.getDeltaTime();
        if (playerNearby) {
            region = attackAnimation.getKeyFrame(stateTime, true);
        } else {
            region = walkingAnimation.getKeyFrame(stateTime, true);
        }

        flipRegionHorizontally(region);
        return region;
    }

    @Override
    public void setPlayerNearby(boolean value) {
        playerNearby = value;
    }

    @Override
    public void setPlayerPosition(Vector2 position) {
        playerPosition = position;
    }

    public void setDead() {
        BodyHelper.changeFilterData(body, Constants.DESTROYED_BIT, Constants.DESTROYED_MASK_BITS);
        isDead = true;
    }
}
