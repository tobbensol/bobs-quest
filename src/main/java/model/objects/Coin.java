package model.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import model.helper.Constants;
import model.helper.ContactType;


public class Coin extends InteractableTiledMapObject {

    public Coin(World world, TiledMap map, Rectangle bounds) { // Make this abstract! All static object
        super(world,map,bounds,ContactType.COIN, "Objects");
    }

    public void onHit() {
        setCategoryFilter(Constants.DESTROYED_BIT);
        removeCell();
    }

}
