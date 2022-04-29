package model.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import model.Level;
import model.helper.ContactType;

public abstract class KinematicObject extends GameObject {
    public KinematicObject(String name, Level level, float width, float height, float x, float y, ContactType contactType, short categoryBits, short maskBits, boolean isSensor, boolean rectangle) {
        super(name, level, width, height, x, y, 1, contactType, BodyDef.BodyType.KinematicBody, categoryBits, maskBits, isSensor, rectangle);
    }

    @Override
    public void update() {
        x = getPosition().x;
        y = getPosition().y;
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
