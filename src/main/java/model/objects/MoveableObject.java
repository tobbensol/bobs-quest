package model.objects;

import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class MoveableObject extends DynamicObject implements Moveable {

    private float velY;

    public MoveableObject(String name, GameModel gameModel, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, gameModel, x, y, density, contactType, categoryBits, maskBits);
        velY = 0;
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM - (width / 2);
        y = body.getPosition().y * Constants.PPM - (height / 2);

        velY = body.getLinearVelocity().len();
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }
}
