package helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class TiledMapHelper {

    private TiledMap tiledMap;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;

    public TiledMapHelper() {
        tiledMap = new TmxMapLoader().load("maps/level0.tmx");
        backgroundLayer = getBoardLayer("Background");
        playerLayer = getBoardLayer("Player");
    }

    public TiledMapTileLayer getBoardLayer(String layer) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layer);
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return new OrthogonalTiledMapRenderer(tiledMap); // TODO: Use proper unit scale
    }
}
