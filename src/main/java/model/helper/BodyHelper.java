package model.helper;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {


    public static Body createObjectBody(float x, float y, float width, float height, float density, World world, ContactType contactType, BodyDef.BodyType bodyType, short categoryBits, short maskBits, boolean isSensor, boolean rectangle) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = bodyType;
        bodyDef.position.set(x / Constants.PPM, y / Constants.PPM);
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        Shape shape = createShape(width, height, rectangle);

        FixtureDef fixtureDef = setFixture(shape, density, categoryBits, maskBits, isSensor, body, contactType);

        shape.dispose();

        if (contactType == ContactType.PLAYER) {
            playerSensors(fixtureDef, body, width, height);
        }

        return body;
    }

    public static void createEnvironmentBody(Shape shape, World world, ContactType contactType, short categoryBits, short maskBits, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        Body body = world.createBody(bodyDef);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        setFixture(shape, 1, categoryBits, maskBits, isSensor, body, contactType);
        shape.dispose();
    }


    public static Shape createShape(float width, float height, boolean rectangle) {
        if (rectangle) {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);
            return polygonShape;
        } else {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(width / 2 / Constants.PPM);
            return circleShape;
        }
    }

    public static Shape createShape(PolygonMapObject polygonMapObject) {

        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        // Retrieves all the vertices of the object
        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    private static FixtureDef setFixture(Shape shape, float density, short categoryBits, short maskBits, boolean isSensor, Body body, ContactType contactType) {
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        fixtureDef.isSensor = isSensor;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);

        return fixtureDef;
    }

    /**
     * makes sensors above, below, right and left of an object, currently its only used by the player, but it can be used by other objects in the future
     *
     * @param fixtureDef the fixture you want to give the sensors, so that they can have the same properties as the body
     * @param body       the body you want to apply the sensors to
     * @param width      the width of the object you want to give a sensor
     * @param height     the height of the object you want to give a sensor
     */
    private static void playerSensors(FixtureDef fixtureDef, Body body, float width, float height) {
        createSensor("foot", fixtureDef, body, (width / 2) * 0.6f / Constants.PPM, 2 / Constants.PPM, 0, -height / 2 / Constants.PPM);
        createSensor("head", fixtureDef, body, (width / 2) * 0.4f / Constants.PPM, 2 / Constants.PPM, 0, height / 2 / Constants.PPM);
        createSensor("right", fixtureDef, body, 2 / Constants.PPM, (width / 2) * 0.9f / Constants.PPM, width / 2 / Constants.PPM, 0);
        createSensor("left", fixtureDef, body, 2 / Constants.PPM, (width / 2) * 0.9f / Constants.PPM, -width / 2 / Constants.PPM, 0);
    }


    private static void createSensor(String name, FixtureDef fixtureDef, Body body, float hx, float hy, float x, float y) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx, hy, new Vector2(x, y), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(name);
        shape.dispose();
    }

    public static void setCategoryFilter(Body body, short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        for (Fixture f : body.getFixtureList()) {
            f.setFilterData(filter);
        }
    }
}
