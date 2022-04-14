package model.objects;

import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class MovableObject extends DynamicObject implements Movable {

    public MovableObject(String name, Level level, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, level, Constants.TILE_SIZE * 0.9f, Constants.TILE_SIZE * 0.9f, x, y, density, contactType, categoryBits, maskBits, false);
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
    }
}
