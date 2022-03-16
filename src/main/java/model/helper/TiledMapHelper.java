package model.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TiledMapHelper {

    private TiledMap tiledMap;
    private GameModel gameModel;

    public TiledMapHelper(GameModel gameModel ) {
        // OBS: map cant be infinite
        // OBS: layers cant be in folders
        this.gameModel = gameModel;
        tiledMap = new TmxMapLoader().load("maps/level1.tmx");

        // TODO: Generalize parsing different objects and mapping to right ContactType (make function/HashMap etc.)
        parseMapEnvironment( getMapObjects("Ground"), ContactType.GROUND );
        parseMapEnvironment( getMapObjects("Platforms"), ContactType.PLATFORM );
        parseDeathPlane( getMapObjects("Death")); // TODO: Make death plane use parseMapEnvironment()
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return new OrthogonalTiledMapRenderer(tiledMap);
    }


    private MapObjects getMapObjects(String objects) {
        // OBS: If objects doesn't exist -> NullPointerException
        MapObjects mapObjects;

        try {
            mapObjects = tiledMap.getLayers().get(objects).getObjects();
        }
        catch (NullPointerException e) {
            throw new NullPointerException("Objects with type '" + objects + "' doesn't exist.");
        }

        return mapObjects;
    }

    /**
     * This method is parsing mapObjects into the game. The mapObjects can either be static or dynamic.
     * @param mapObjects - an iterable of mapObjects to parse.
     * @param contactType - the ContactType the mapObjects should have.
     */
    private void parseMapEnvironment(MapObjects mapObjects, ContactType contactType) {
        for(MapObject mapObject : mapObjects) {
            if( mapObject instanceof PolygonMapObject ) {
                // TODO: Use BodyHelper instead of createBody()
                createBody(mapObject , BodyDef.BodyType.StaticBody, contactType, true, false);
            }
        }
    }

    /**
     * This method is parsing mapObjects into the game by getting the centrer of its object box
     * @param objectLayer - string with the name of the layer you wish to get the spawnpoints from
     * @return list of spawnpoints
     */
    public List<Vector2> parseMapSpawnPoints(String objectLayer) {
        List<Vector2> center = new ArrayList<>();
        for (Rectangle r : parseMapObjects(objectLayer)){
            center.add(r.getCenter(new Vector2()));
        }
        return center;
    }

    private List<Rectangle> parseMapObjects(String objectLayer){
        MapObjects mapObjects = getMapObjects(objectLayer);
        List<Rectangle> objectList = new ArrayList<>();
        for(MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                objectList.add(parseObject((RectangleMapObject) mapObject));
            }
            else if (mapObject instanceof PolygonMapObject) {
                throw new IllegalArgumentException("Objects on map must be RectangleMapObjects, not PolygonMapObject");
            }
        }
        return objectList;
    }

    private Rectangle parseObject(RectangleMapObject mapObject) {
        return mapObject.getRectangle();
    }

    // TODO: Remove method
    private void parseDeathPlane(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            Body body;
            Fixture fixture;
            BodyDef bodyDef = new BodyDef();
            FixtureDef fixtureDef = new FixtureDef();
            PolygonShape shape = new PolygonShape();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(((rectangle.getX() + rectangle.getWidth() / 2) / Constants.PPM), ((rectangle.getY() + rectangle.getHeight() / 2) / Constants.PPM));

            body = gameModel.getWorld().createBody(bodyDef);

            shape.setAsBox((rectangle.getWidth()/2/Constants.PPM), (rectangle.getHeight()/2/Constants.PPM));
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;
            //fixtureDef.filter.categoryBits = Constants.DESTROYED_BIT;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(ContactType.DEATH);

            shape.dispose();
        }
    }

    /**
     * This method creates a body for a given mapObject with a given BodyType and ContactType.
     * The creation of the body depends on the BodyType:
     * StaticBody -> PolygonShape (PolygonMapObject)
     * DynamicBody -> RectangularShape (RectangularMapObject)
     *
     * @param mapObject - the mapObject.
     * @param bodyType - The BodyType of the mapObject. Either Static og Dynamic.
     * @param contactType - The ContactType of the mapObject.
     */
    // TODO: Remove method (?) get using BodyHelper
    private void createBody(MapObject mapObject, BodyDef.BodyType bodyType, ContactType contactType, boolean polygon, boolean rectangular) {
        if (polygon == rectangular) {
            throw new IllegalArgumentException("The shape of the body must either be of type polygon or rectangular. Can not be both.");
        }

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Body body = gameModel.getWorld().createBody( bodyDef );
        Shape shape;
        Fixture fixture;

        bodyDef.type = bodyType;

        if (polygon) {
            shape = createPolygonShape((PolygonMapObject) mapObject);
        }
        else {
            shape = createRectangularShape((RectangleMapObject) mapObject, bodyDef);
        }
        // Changes the shape of the world object to match the one in the map

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);
        shape.dispose();
    }

    // TODO: Remove method
    private Shape createRectangularShape(RectangleMapObject mapObject, BodyDef bodyDef) {
        Rectangle rectangle = mapObject.getRectangle();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(((rectangle.getX() + rectangle.getWidth() / 2) / Constants.PPM), ((rectangle.getY() + rectangle.getHeight() / 2) / Constants.PPM));
        shape.setAsBox((rectangle.getWidth()/2/Constants.PPM), (rectangle.getHeight()/2/Constants.PPM));
        return shape;
    }


    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {

        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[ vertices.length / 2 ];

        // Retrieves all the vertices of the object
        for ( int i = 0 ; i < vertices.length / 2 ; i++ ) {
            Vector2 current = new Vector2( vertices[ i * 2 ] / Constants.PPM , vertices[ i * 2 + 1 ] / Constants.PPM );
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set( worldVertices );
        return shape;
    }

    // TODO: Remove method
    public TiledMapTileLayer getBoardLayer(String layer) {
        // OBS: If layer is object layer -> ClassCastException
        // OBS: Do not name a layer the same as an object
        TiledMapTileLayer boardLayer = null;
        try {
            boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layer);
        } catch (ClassCastException e) {
            throw new ClassCastException("Cannot cast to TiledMapTileLayer because '" + layer + "' is an object layer.");
        }
        if (boardLayer == null) {
            throw new NullPointerException("Layer '" + layer + "' doesn't exist.");
        }
        return boardLayer;
    }

    // TODO: Remove method
    public ArrayList<Vector2> parseSpawnPoint(String Object) {
        ArrayList<Vector2> spawnLocations = new ArrayList<>();
        MapObjects spawnPoints = getMapObjects(Object);

        for ( MapObject mapObject : spawnPoints ) {
            RectangleMapObject spawnPoint = (RectangleMapObject) mapObject;
            float x = spawnPoint.getRectangle().getX();
            float y = spawnPoint.getRectangle().getY();
            spawnLocations.add( new Vector2(x, y) );
        }
        return spawnLocations;
    }
}
