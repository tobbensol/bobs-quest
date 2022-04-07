package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;


public class Floater extends MovableObject{
    private static final float X_VELOCITY = 3.2f;
    private static final int attack = 40;
    private int numMoves;
    private boolean playerNearby = false;
    private Vector2 playerPosition;

    public Floater(String name, Level level, float x, float y) {
        super(name + " " + level.getGameObjects(Floater.class).size(), level, x, y, 1, ContactType.ENEMY, Constants.ENEMY_BIT, Constants.ENEMY_MASK_BITS);
        texture = new Texture("Multi_Platformer_Tileset_v2/WorldObjects/Coin.png");
        body.setGravityScale(0);
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    private void move() {
        if (playerNearby) {
            body.applyForceToCenter(new Vector2(playerPosition.x - body.getPosition().x, playerPosition.y - body.getPosition().y), true);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x , y , width, height);
    }

    public void setPlayerNearby(boolean value) {
        playerNearby = value;
    }

    public void setPlayerPostion(Vector2 position) {
        playerPosition = position;
    }

    @Override
    public void moveHorizontally(boolean isRight) {

    }
}
