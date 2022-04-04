package model.objects;

import model.Level;
import model.helper.ContactType;

public abstract class JumpableObject extends MovableObject implements Jumpable {

    protected boolean grounded;
    protected int groundedcount;
    protected boolean inSlope;

    public JumpableObject(String name, Level level, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, level, x, y, density, contactType, categoryBits, maskBits);
        grounded = false;
        groundedcount = 0;
    }

    public boolean setGrounded(boolean value) {
        if (value) {
            groundedcount++;
        } else {
            groundedcount--;
        }
        grounded = groundedcount > 0;
        return grounded;
    }

}
