package model.objects;

import model.GameModel;
import model.helper.ContactType;

public abstract class MoveableObject extends StaticObject implements Moveable {

    public MoveableObject(String name, String texturePath, GameModel gameModel, float x, float y, int density, ContactType contactType) {
        super(name, texturePath, gameModel, x, y, density, contactType);
    }
}
