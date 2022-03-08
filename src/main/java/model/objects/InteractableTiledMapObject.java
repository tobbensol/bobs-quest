package model.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class InteractableTiledMapObject {

    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected ContactType contactType;
    protected String objectLayer;


    public InteractableTiledMapObject(World world, TiledMap map, Rectangle bounds, ContactType contactType, String objectLayer) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        this.contactType = contactType;
        this.objectLayer = objectLayer;

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

        shape.dispose();
    }

    protected void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    protected void removeCell() {
        getCell(objectLayer).setTile(null);
    }

    private TiledMapTileLayer.Cell getCell(String fromLayer) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(fromLayer);
        return layer.getCell((int) (body.getPosition().x * Constants.PPM / Constants.TILE_SIZE),
                (int) (body.getPosition().y * Constants.PPM / Constants.TILE_SIZE));
    }


    public Body getBody() {
        return body;
    }
}
