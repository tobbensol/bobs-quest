package model.objects;

import model.GameModel;
import model.helper.ContactType;

public abstract class JumpableObject extends MovableObject implements Jumpable {

    protected Boolean grounded;

    public JumpableObject(String name, GameModel gameModel, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, gameModel, x, y, density, contactType, categoryBits, maskBits);
        grounded = false;
    }

    public boolean setGrounded(boolean value) {
        grounded = value;
        return grounded;
    }
}
