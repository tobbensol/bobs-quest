package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;


public class Floater extends MovableObject implements Enemy {
    private static final float X_VELOCITY = 200f;
    private static final int attack = 10;
    private boolean playerNearby = false;
    private Vector2 playerPosition;
    int steps = 0;
    float direction = 1f;

    public Floater(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Floater.class).size()) + 1, level, x, y, 1, ContactType.ENEMY, Constants.ENEMY_BIT, Constants.ENEMY_MASK_BITS);
        texture = new Texture("Multi_Platformer_Tileset_v2/Dungeon/Details/Dungeon_Jar1.png");
        body.setGravityScale(0);
        body.setLinearDamping(3);
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    private void move() {
        if (playerNearby) {
            body.applyForceToCenter(new Vector2((playerPosition.x - x), (playerPosition.y  - y)).setLength(X_VELOCITY), true);
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
            double nextpos  = direction * steps * Math.PI/100;
            setPosition((float) (x+Math.sin(nextpos)), (float) (y + Math.cos(nextpos)));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - width/2, y - height/2, width, height);
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
