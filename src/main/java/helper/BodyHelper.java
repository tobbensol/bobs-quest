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


        // Creating the interacting shape (box or circle) around the object.
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(width/2/Constants.PPM);
        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef).setUserData(contactType);

        // Creating foot-sensor located at the bottom of the object.
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((width/2) *0.6f / Constants.PPM, 2/Constants.PPM, new Vector2(0,-height/2/Constants.PPM), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("foot");

        // Creating head-sensor located at the top of the object.
        polygonShape.setAsBox((width/2) * 0.4f / Constants.PPM, 2 / Constants.PPM, new Vector2(0, height/2/Constants.PPM), 0);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("head");

        circleShape.dispose();
        polygonShape.dispose();
        return body;
    }
}
