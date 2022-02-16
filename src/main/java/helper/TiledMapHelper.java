package helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class TiledMapHelper {

    private TiledMap tiledMap;
    //private GameScreen gameScreen;

//    public TileMapHelper(GameScreen gameScreen) {
//        this.gameScreen = gameScreen;
//    }

    public TiledMapHelper() {

    }

    public OrthogonalTiledMapRenderer setupMap() {
        TmxMapLoader loader = new TmxMapLoader();
        //tiledMap = loader.load("maps/level0.tmx");
        tiledMap = loader.load("maps/bigTiles.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap, 1/2f);
    }

}
