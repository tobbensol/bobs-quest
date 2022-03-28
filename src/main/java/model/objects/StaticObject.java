package model.objects;


import com.badlogic.gdx.physics.box2d.BodyDef;
import model.GameModel;
import model.Level;
import model.helper.ContactType;

public abstract class StaticObject extends GameObject {


    public StaticObject(String name, Level level, float width, float height, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits, boolean isSensor, boolean rectangle) {
        super(name, level, width, height, x, y, density, contactType, BodyDef.BodyType.StaticBody, categoryBits, maskBits, isSensor, rectangle);
    }

}
