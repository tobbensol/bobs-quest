package model.objects;

import model.GameModel;
import model.helper.ContactType;

public abstract class JumpableObject extends MoveableObject implements Jumpable {

    protected Boolean grounded;

    public JumpableObject(String name, GameModel gameModel, float x, float y, float density, ContactType contactType) {
        super(name, gameModel, x, y, density, contactType);
        grounded = false;
    }

    public boolean setGrounded(boolean value) {
        grounded = value;
        return grounded;
    }
}
