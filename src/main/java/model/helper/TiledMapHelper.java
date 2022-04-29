package model.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;
import model.Level;

import java.util.ArrayList;
import java.util.List;


public class TiledMapHelper {

    private final TiledMap tiledMap;
    private final Level level;

    public TiledMapHelper(Level level, String levelID) {
        // OBS: Map can't be infinite
        // OBS: Layers can't be in folders
        this.level = level;
        tiledMap = new TmxMapLoader().load("maps/" + levelID + ".tmx");

        // TODO: Generalize parsing different objects and mapping to right ContactType (make function/HashMap etc.)
        parseMapEnvironment(getMapObjects("Ground"), ContactType.GROUND, Constants.DEFAULT_BIT, Constants.DEFAULT_MASK_BITS, false);
        parseMapEnvironment(getMapObjects("Platforms"), ContactType.PLATFORM, Constants.PLATFORM_BIT, Constants.DEFAULT_MASK_BITS, false);
        parseMapEnvironment(getMapObjects("Death"), ContactType.DEATH, Constants.DEFAULT_BIT, Constants.INTERACTIVE_MASK_BITS, true);
        parseMapEnvironment(getMapObjects("Edge"), ContactType.EDGE, Constants.DEFAULT_BIT, Constants.EDGE_MASK_BITS, false);
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private MapObjects getMapObjects(String objects) {
        MapObjects mapObjects;

        if (tiledMap.getLayers().getIndex(objects) == -1) {
            return new MapObjects();
        }
        mapObjects = tiledMap.getLayers().get(objects).getObjects();

        return mapObjects;
    }

    /**
     * This method is parsing mapObjects into the game. The mapObjects can either be static or dynamic.
     *
     * @param mapObjects  - an iterable of mapObjects to parse.
     * @param contactType - the ContactType the mapObjects should have.
     */
    private void parseMapEnvironment(MapObjects mapObjects, ContactType contactType, short categoryBits, short maskBits, Boolean isSensor) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                Shape shape = BodyHelper.createShape((PolygonMapObject) mapObject);
                BodyHelper.createEnvironmentBody(shape, level.getWorld(), contactType, categoryBits, maskBits, isSensor);
            }
        }
    }

    /**
     * This method is parsing mapObjects into the game by getting the center of its object box
     *
     * @param objectLayer - string with the name of the layer you wish to get the spawn-points from
     * @return list of spawn-points
     */

    public List<Rectangle> parseMapRectangles(String objectLayer) {
        return parseMapObjects(objectLayer);
    }

    /**
     * gets all the squares of the game objects, not sure if we need this since the only other time its used is to get the center in parse map spawn points
     *
     * @param objectLayer they name of the layer you want the gameObjects from
     * @return a list of all the game objects
     */
    private List<Rectangle> parseMapObjects(String objectLayer) {
        MapObjects mapObjects = getMapObjects(objectLayer);
        List<Rectangle> objectList = new ArrayList<>();
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                objectList.add(parseObject((RectangleMapObject) mapObject));
            } else if (mapObject instanceof PolygonMapObject) {
                throw new IllegalArgumentException("Objects on map must be RectangleMapObjects, not PolygonMapObject");
            }
        }
        return objectList;
    }

    private Rectangle parseObject(RectangleMapObject mapObject) {
        return mapObject.getRectangle();
    }
}
