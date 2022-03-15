package model.objects;

import model.GameModel;

public class GameObjectFactory {

    private GameModel model;

    public GameObjectFactory(GameModel model) {
        this.model = model;
    }

    public GameObject create(String gameObject, float x, float y) {
        String objectString = gameObject.toUpperCase();
        return switch (objectString) {
            case "COIN" -> new Coin(objectString, model, x, y);
            case "PLAYER" -> new Player(objectString, model, x, y);
            case "GOOMBA" -> new Goomba(objectString, model, x, y);
            default -> null;
        };
    }

}
