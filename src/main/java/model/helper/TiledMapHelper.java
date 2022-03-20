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
    private final GameModel gameModel;

    public TiledMapHelper(GameModel gameModel, String level ) {
        // OBS: Map can't be infinite
        // OBS: Layers can't be in folders
        this.gameModel = gameModel;
        tiledMap = new TmxMapLoader().load("maps/" + level + ".tmx");

        // TODO: Generalize parsing different objects and mapping to right ContactType (make function/HashMap etc.)
        parseMapEnvironment( getMapObjects("Ground"), ContactType.GROUND, Constants.DEFAULT_BIT, Constants.DEFAULT_MASK_BITS, false);
        parseMapEnvironment( getMapObjects("Platforms"), ContactType.PLATFORM , Constants.DEFAULT_BIT, Constants.DEFAULT_MASK_BITS,false);
        parseMapEnvironment( getMapObjects("Death"), ContactType.DEATH , Constants.DEFAULT_BIT, Constants.DEFAULT_MASK_BITS, true);
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
        System.out.println(mapObjects.iterator().hasNext());
        return mapObjects;
    }

    /**
     * This method is parsing mapObjects into the game. The mapObjects can either be static or dynamic.
     * @param mapObjects - an iterable of mapObjects to parse.
     * @param contactType - the ContactType the mapObjects should have.
     */
    private void parseMapEnvironment(MapObjects mapObjects, ContactType contactType, short categoryBits, short maskBits, Boolean isSensor) {
        for(MapObject mapObject : mapObjects) {
            if( mapObject instanceof PolygonMapObject ) {
                Shape shape = BodyHelper.createShape((PolygonMapObject) mapObject);
                BodyHelper.createEnvironmentBody(shape, gameModel.getWorld(), contactType, categoryBits, maskBits, isSensor);
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
}
