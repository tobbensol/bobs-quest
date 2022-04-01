package model.objects;

import model.Level;

public class GameObjectFactory {

    private final Level level;

    public GameObjectFactory(Level level) {
        this.level = level;
    }

    /**
     * Return value must be cast to own type //TODO: Write doc
     *
     * @param gameObject a string with the name of the object (not case sensitive)
     * @param x          the x position of the object
     * @param y          the y position of the object
     * @return the object in the designated location
     */
    public GameObject create(String gameObject, float x, float y) {
        String objectString = gameObject.toUpperCase();
        return switch (objectString) {
            case "COIN" -> new Coin(objectString, level, x, y);
            case "PLAYER" -> new Player(objectString, level, x, y);
            case "GOOMBA" -> new Goomba(objectString, level, x, y);
            case "GOAL" -> new Goal(objectString, level, x, y);
            case "CAMERAWALL" -> new CameraWall(objectString, level, x, y);
            default -> null;
        };
    }

}
