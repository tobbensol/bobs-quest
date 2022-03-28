package model.objects;

import model.Level;
import model.helper.ContactType;

public abstract class JumpableObject extends MovableObject implements Jumpable {

    protected Boolean grounded;

    public JumpableObject(String name, Level level, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, level, x, y, density, contactType, categoryBits, maskBits);
        grounded = false;
    }

    public boolean setGrounded(boolean value) {
        grounded = value;
        return grounded;
    }
}
