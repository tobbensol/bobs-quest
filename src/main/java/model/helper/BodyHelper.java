package model.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {

    private static Fixture fixture;

    public static Body BodyHelper(float x, float y, float width, float height, float density, World world, ContactType contactType) {

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();

        if (contactType == ContactType.COIN){
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }
        bodyDef.position.set(x/Constants.PPM, y/Constants.PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        circleShape.setRadius(width/2/Constants.PPM);

        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        if (contactType == ContactType.PLAYER) {
            fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
            fixtureDef.filter.maskBits = Constants.DEFAULT_BIT; // What an object can collide with.
        }
        else if (contactType == ContactType.ENEMY) {
            fixtureDef.filter.categoryBits = Constants.DEFAULT_BIT;
        }
        else if (contactType == ContactType.COIN){
            fixtureDef.isSensor = true;
        }
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);

        circleShape.dispose();

        // TODO: Proper check?
        if (contactType == ContactType.PLAYER) {
            createSensor("foot", fixtureDef,body,(width/2) *0.6f / Constants.PPM, 2/Constants.PPM, 0,-height/2/Constants.PPM);
            createSensor("head", fixtureDef,body,(width/2) *0.4f / Constants.PPM, 2/Constants.PPM, 0,height/2/Constants.PPM);
            createSensor("right", fixtureDef,body,2 / Constants.PPM, (width/2)*0.9f / Constants.PPM, width/2/Constants.PPM,0);
            createSensor("left", fixtureDef,body,2 / Constants.PPM, (width/2)*0.9f / Constants.PPM, -width/2/Constants.PPM,0);
        }
        return body;
    }

    /*
    public static Body staticBodyHelper(World world, Rectangle bounds, Fixture fixture, ContactType contactType) {
        Body body;
        Fixture fixture;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM), ((bounds.getY() + bounds.getHeight() / 2) / Constants.PPM));

        body = world.createBody(bodyDef);

        shape.setAsBox((bounds.getWidth()/2/Constants.PPM), (bounds.getHeight()/2/Constants.PPM));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);
        shape.dispose();

        return body;
    }

     */

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
