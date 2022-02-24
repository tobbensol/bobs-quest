package helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {

    public static Body BodyHelper(float x, float y, float width, float height, int density, World world, ContactType contactType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/Constants.PPM, y/Constants.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef).setUserData(contactType);


        // Creating foot sensor
        shape.setAsBox(2/Constants.PPM, 2/Constants.PPM, new Vector2(0,-height/2/Constants.PPM), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("foot");

        shape.dispose();
        return body;
    }
}
