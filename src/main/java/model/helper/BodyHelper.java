package model.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {


    public static Body BodyHelper(float x, float y, float width, float height, float density, World world, ContactType contactType, BodyDef.BodyType bodyType, short categoryBits, short maskBits, boolean isSensor) {

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();

        bodyDef.type = bodyType;
        bodyDef.position.set(x / Constants.PPM, y / Constants.PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        circleShape.setRadius(width/2/Constants.PPM);

        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.isSensor = isSensor;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);

        circleShape.dispose();

        if (contactType == ContactType.PLAYER) {
            createSensor("foot", fixtureDef,body,(width/2) *0.6f / Constants.PPM, 2/Constants.PPM, 0,-height/2/Constants.PPM);
            createSensor("head", fixtureDef,body,(width/2) *0.4f / Constants.PPM, 2/Constants.PPM, 0,height/2/Constants.PPM);
            createSensor("right", fixtureDef,body,2 / Constants.PPM, (width/2)*0.9f / Constants.PPM, width/2/Constants.PPM,0);
            createSensor("left", fixtureDef,body,2 / Constants.PPM, (width/2)*0.9f / Constants.PPM, -width/2/Constants.PPM,0);
        }

        return body;
    }


    private static void playerSensors(FixtureDef fixtureDef, Body body, float width, float height) {
        createSensor("foot", fixtureDef,body,(width/2) *0.6f / Constants.PPM, 2/Constants.PPM, 0,-height/2/Constants.PPM);
        createSensor("head", fixtureDef,body,(width/2) *0.4f / Constants.PPM, 2/Constants.PPM, 0,height/2/Constants.PPM);
        createSensor("right", fixtureDef,body,2 / Constants.PPM, (width/2)*0.9f / Constants.PPM, width/2/Constants.PPM,0);
        createSensor("left", fixtureDef,body,2 / Constants.PPM, (width/2)*0.9f / Constants.PPM, -width/2/Constants.PPM,0);
    }


    private static void createSensor(String name, FixtureDef fixtureDef, Body body, float hx, float hy, float x, float y) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx, hy, new Vector2(x,y), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(name);
        shape.dispose();
    }

    public static void setCategoryFilter(Body body, short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        for (Fixture f : body.getFixtureList()){
            f.setFilterData(filter);
        }
    }
}
