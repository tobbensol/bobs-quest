package model.objects;

import model.GameModel;

public class GameObjectFactory {

    private GameModel model;

    public GameObjectFactory(GameModel model) {
        this.model = model;
    }

    /**
     * Return value must be cast to own type //TODO: Write doc
     *
     * @param gameObject the string of the object you want to make, not case sensetive
     * @param x the X coordinate of the object
     * @param y the Y coordinate of the object
     * @return the object you made
     */
    public GameObject create(String gameObject, float x, float y) {
        String objectString = gameObject.toUpperCase();
        return switch (objectString) {
            case "COIN" -> new Coin(objectString, model, x, y);
            case "PLAYER" -> new Player(objectString, model, x, y);
            case "GOOMBA" -> new Goomba(objectString, model, x, y);
            case "GOAL" -> new Goal(objectString, model, x, y);
            default -> null;
        };
    }

}
