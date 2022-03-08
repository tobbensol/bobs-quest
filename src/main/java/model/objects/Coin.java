package model.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import model.Hud;
import model.helper.Constants;
import model.helper.ContactType;


public class Coin {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    protected String objectLayer = "CoinTex";

    public Coin(World world, TiledMap map, Rectangle bounds, ContactType contactType) { // Make this abstract! All static object
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM), ((bounds.getY() + bounds.getHeight() / 2) / Constants.PPM));

        body = world.createBody(bodyDef);

        shape.setAsBox((bounds.getWidth()/2/Constants.PPM), (bounds.getHeight()/2/Constants.PPM));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(contactType);
    }

    public void onHit() {
        setCategoryFilter(Constants.DESTROYED_BIT);
        removeCell();
        Hud.addScore(100);
    }


    private void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    private void removeCell() {
        getCell(objectLayer).setTile(null);
    }

    private TiledMapTileLayer.Cell getCell(String fromLayer) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(fromLayer);
        return layer.getCell((int) (body.getPosition().x * Constants.PPM / 64),
                (int) (body.getPosition().y * Constants.PPM / 64));
    }


    public Body getBody() {
        return body;
    }
}
