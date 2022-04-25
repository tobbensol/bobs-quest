package model.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;


public class Floater extends MovableObject implements Enemy {
    private static final float X_VELOCITY = 200f;
    private static final int attack = 10;
    private boolean playerNearby = false;
    private Vector2 playerPosition;
    private int steps = 0;
    private float direction = 1f;
    private float stateTime;
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> attackAnimation;

    public Floater(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Floater.class).size()) + 1, level, x, y, 1, ContactType.ENEMY, Constants.ENEMY_BIT, Constants.ENEMY_MASK_BITS);
        texture = new Texture("Multi_Platformer_Tileset_v2/Enemies/Bat_Sprite_Sheet.png");
        body.setGravityScale(0);
        body.setLinearDamping(3);

        TextureRegion[][] frames = TextureRegion.split(getTexture(), Constants.TILE_SIZE / 4, Constants.TILE_SIZE / 4);
        idleAnimation = new Animation<>(0.166f/2f, frames[1]);
        attackAnimation = new Animation<>(0.166f/2f, frames[0]);
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    private void move() {
        if (playerNearby) {
            Vector2 forceVec = new Vector2((playerPosition.x - x), (playerPosition.y  - y)).setLength(X_VELOCITY);
            body.applyForceToCenter(forceVec, true);
            facingRight = forceVec.x > 0;
        }
        else{
            steps++;
            //if (steps % 200 == 0){
            //    direction *= -1;
            //}
            //setPosition(x, y + direction);
            //alternate floater movement
            if (steps % 200 == 0 && Math.random() > 0.5f){
                direction *= -1;
            }
            steps %= 200;
            double nextPos = direction * steps * Math.PI/100;
            Vector2 currentPos = new Vector2(getPosition());
            setPosition((float) (x+Math.sin(nextPos)), (float) (y + Math.cos(nextPos)));
            facingRight = getPosition().x > currentPos.x;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x - width/2, y - height/2, width, height);
    }

    @Override
    protected TextureRegion getFrame() {
        TextureRegion region;
        stateTime += Gdx.graphics.getDeltaTime();

        if (playerNearby) {
            region = attackAnimation.getKeyFrame(stateTime, true);
        } else {
            region = idleAnimation.getKeyFrame(stateTime, true);
        }

        if (facingRight == region.isFlipX()) { // TODO: Update facingRight
            region.flip(true, false);
        }

        return region;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public void onHit() {

    }

    @Override
    public void setPlayerPosition(Vector2 position) {
        playerPosition = position;
    }

    @Override
    public void setPlayerNearby(boolean value) {
        playerNearby = value;
    }


    @Override
    public void moveHorizontally(boolean isRight) {

    }
}
