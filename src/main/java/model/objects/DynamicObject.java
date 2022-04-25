package model.objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class DynamicObject extends GameObject implements Movable {

    public DynamicObject(String name, Level level, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, level, Constants.TILE_SIZE * 0.9f, Constants.TILE_SIZE * 0.9f, x, y, density, contactType, BodyDef.BodyType.DynamicBody, categoryBits, maskBits, false, false);
    }

    public void update() {
        x = getPosition().x;
        y = getPosition().y;
    }
}
