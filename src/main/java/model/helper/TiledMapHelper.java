package model.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import model.GameModel;


public class TiledMapHelper {

    private TiledMap tiledMap;
    private GameModel gameModel;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;


    public TiledMapHelper(GameModel gameModel ) {
        this.gameModel = gameModel;
        tiledMap = new TmxMapLoader().load("maps/level0.tmx");

        parseMapObjects( getMapObjects("Ground") );
        parseMapObjects( getMapObjects("Platforms") );
        backgroundLayer = getBoardLayer("WorldStructures");
        playerLayer = getBoardLayer("Player");
    }


    private MapObjects getMapObjects(String objects) {
        return tiledMap.getLayers().get( objects ).getObjects();
    }


    public TiledMapTileLayer getBoardLayer(String layer) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layer);
    }


    public OrthogonalTiledMapRenderer setupMap() {
        return new OrthogonalTiledMapRenderer(tiledMap); // TODO: Use proper unit scale
    }

    // Creates a static body for map objects
    private void parseMapObjects(MapObjects mapObjects) {
        for( MapObject mapObject : mapObjects ) {
            if( mapObject instanceof PolygonMapObject ) {
                createBody(mapObject , BodyDef.BodyType.StaticBody);
            }
            else if (mapObject instanceof RectangleMapObject) {
                createBody(mapObject , BodyDef.BodyType.DynamicBody);
            }

        }
    }

    private void createBody(MapObject mapObject, BodyDef.BodyType bodyType){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        // Adds the world object to the map
        Body body = gameModel.getWorld().createBody( bodyDef );
        Shape shape;

        if (bodyType.equals(BodyDef.BodyType.StaticBody)) {
            shape = createPolygonShape((PolygonMapObject) mapObject);
        }
        else if (bodyType.equals(BodyDef.BodyType.DynamicBody)) {
            shape = createRectangularShape((RectangleMapObject) mapObject);
        }
        else{
            throw new IllegalArgumentException("BodyType must be static or dynamic.");
        }
        // Changes the shape of the world object to match the one in the map
        body.createFixture(shape, 1000).setUserData(ContactType.GROUND);
        shape.dispose();
    }

    private Shape createRectangularShape(RectangleMapObject mapObject) {
        return null;
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
}
