package model.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class DynamicObject extends GameObject {

    public DynamicObject(String name, Level level, float width, float height, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits, boolean rectangle) {
        super(name, level, width, height, x, y, density, contactType, BodyDef.BodyType.DynamicBody, categoryBits, maskBits, false, rectangle);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
