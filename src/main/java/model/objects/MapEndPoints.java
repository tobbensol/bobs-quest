package model.objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public class MapEndPoints extends KinematicObject {

    public MapEndPoints(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(MapEndPoints.class).size() + 1), level, 100, 10000, x, y, ContactType.CAMERA_WALL, Constants.CAMERA_WALL_BIT, Constants.INTERACTIVE_MASK_BITS, false, true);
        body.setType(BodyDef.BodyType.KinematicBody);
    }

    public float getWidth() {
        return width;
    }
}
