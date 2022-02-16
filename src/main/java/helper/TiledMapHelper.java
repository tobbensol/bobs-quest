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
        tiledMap = new TmxMapLoader().load("maps/level0.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

}
