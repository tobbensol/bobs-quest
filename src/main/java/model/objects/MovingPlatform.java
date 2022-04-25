package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public class MovingPlatform extends KinematicObject{
    int steps = 0;
    float direction = 1.2f;
    public MovingPlatform(String name, Level level, float x, float y) {
        super(name, level, 4 * Constants.TILE_SIZE, 0.1f * Constants.TILE_SIZE, x, y, ContactType.PLATFORM, Constants.PLATFORM_BIT, Constants.DEFAULT_MASK_BITS, false, true);
        body.setType(BodyDef.BodyType.KinematicBody);
        texture = new Texture("Multi_Platformer_Tileset_v2/GrassLand/Background/GrassLand_Cloud_2.png");
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    private void move() {
        steps++;
        if (steps % 400 == 0){
            direction *= -1;
        }
        setPosition(x, y + direction);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getFrame(), x - Math.round((float) texture.getWidth()/2), y - Math.round((float) texture.getHeight()/2), texture.getWidth(), texture.getHeight());
    }

    @Override
    protected TextureRegion getFrame() {
        return new TextureRegion(texture);
    }

}
