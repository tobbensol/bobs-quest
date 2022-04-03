package model.objects;

import model.Level;
import model.helper.ContactType;

public abstract class JumpableObject extends MovableObject implements Jumpable {

    private int groundedcount = 0;
    protected Boolean grounded;

    public JumpableObject(String name, Level level, float x, float y, float density, ContactType contactType, short categoryBits, short maskBits) {
        super(name, level, x, y, density, contactType, categoryBits, maskBits);
        grounded = false;
    }

    //todo make other contacts work like this as well?
    public boolean setGrounded(boolean value) {
        if(value){
            groundedcount++;
        }
        else{
            groundedcount--;
        }
        grounded = groundedcount > 0;
        return grounded;
    }
}
