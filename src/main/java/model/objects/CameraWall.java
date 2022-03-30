package model.objects;

import model.Level;
import model.helper.Constants;
import model.helper.ContactType;

public class CameraWall extends DynamicObject{

    public CameraWall(String name, Level level, float x, float y) {
        super(name + " " + (level.getCameraWalls().size() + 1), level, 100, 10000, x, y, 99999999, ContactType.CAMERA_WALL, Constants.CAMERA_WALL_BIT, Constants.CAMERA_WALL_MASK_BITS, true);
        body.setGravityScale(0);
    }
}
