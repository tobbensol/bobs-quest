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
            case "COIN" -> new Coin(objectString, model, x, y, 1);
            case "PLAYER" -> new Player(objectString, model, x, y, 0.8f);
            case "GOOMBA" -> new Goomba(objectString, model, x, y, 1);
            default -> null;
        };
    }

}
