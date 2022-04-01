package model.objects;

import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public class MapEndPoints extends DynamicObject {

    public MapEndPoints(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(MapEndPoints.class).size() + 1), level, 100, 10000, x, y, 99999999, ContactType.CAMERA_WALL, Constants.CAMERA_WALL_BIT, Constants.CAMERA_WALL_MASK_BITS, true);
        body.setGravityScale(0);
    }
}
