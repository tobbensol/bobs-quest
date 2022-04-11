package model.objects;

import model.Level;
import model.helper.ContactType;

public abstract class JumpableObject extends MovableObject implements Jumpable {

    protected int groundedcount = 0;
    protected boolean canJump = true;
    protected boolean grounded = false;

    public JumpableObject(String name, Level level, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, level, x, y, density, contactType, categoryBits, maskBits);
    }

    //todo make other contacts work like this as well?
    public void setGrounded(boolean value) {
        if (value) {
            groundedcount++;
        } else {
            groundedcount--;
        }
        updateGrouned();
    }

    public void updateGrouned() {
        grounded = groundedcount > 0 && canJump;
    }
}
