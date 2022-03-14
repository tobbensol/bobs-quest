package model.objects;

import model.GameModel;
import model.helper.ContactType;


public class GameObjectFactory {

    private GameModel model;

    public GameObjectFactory(GameModel model) {
        this.model = model;
    }

    public StaticObject create(String gameObject, float x, float y) {
        String objectString = gameObject.toUpperCase();
        return switch (objectString) {
            //case "COIN" -> new Coin();
            case "PLAYER" -> new Player(objectString, model, x, y, 1);
            case "GOOMBA" -> new Goomba(objectString, model, x, y, 1, ContactType.ENEMY);
            default -> null;
        };
    }

}
