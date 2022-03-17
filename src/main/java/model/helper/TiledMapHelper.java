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
        parseMapEnvironment( getMapObjects("Ground"), ContactType.GROUND, false);
        parseMapEnvironment( getMapObjects("Platforms"), ContactType.PLATFORM , false);
        parseMapEnvironment( getMapObjects("Death"), ContactType.DEATH , true);
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
    private void parseMapEnvironment(MapObjects mapObjects, ContactType contactType, Boolean isSensor) {
        for(MapObject mapObject : mapObjects) {
            if( mapObject instanceof PolygonMapObject ) {
                // TODO: Use BodyHelper instead of createBody()
                createBody(mapObject , BodyDef.BodyType.StaticBody, contactType, true, false, isSensor);
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
    private void createBody(MapObject mapObject, BodyDef.BodyType bodyType, ContactType contactType, boolean polygon, boolean rectangular, Boolean isSensor) {
        if (polygon == rectangular) {
            throw new IllegalArgumentException("The shape of the body must either be of type polygon or rectangular. Can not be both.");
        }

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        Body body = gameModel.getWorld().createBody( bodyDef );
        Shape shape;
        Fixture fixture;

        bodyDef.type = bodyType;

        shape = BodyHelper.createShape((PolygonMapObject) mapObject);

        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);

        shape.dispose();
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
}
