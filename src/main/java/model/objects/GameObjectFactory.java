package model.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.Level;


public class GameObjectFactory {

    private final Level level;

    public GameObjectFactory(Level level) {
        this.level = level;
    }

    /**
     * Return value must be cast to own type
     *
     * @param gameObject A string with the name of the object (not case sensitive)
     * @param rectangle The rectangle to create an object on.
     * @return The object in the designated location
     */
    public GameObject create(String gameObject, Rectangle rectangle) {
        Vector2 loc = rectangle.getCenter(new Vector2());
        String objectString = gameObject.toUpperCase();
        return switch (objectString) {
            case "COIN" -> new Coin(objectString, level, loc.x, loc.y);
            case "PLAYER" -> new Player(objectString, level, loc.x, loc.y);
            case "GOOMBA" -> new Goomba(objectString, level, loc.x, loc.y);
            case "GOAL" -> new Goal(objectString, level, loc.x, loc.y);
            case "MAPENDPOINTS" -> new MapEndPoints(objectString, level, loc.x, loc.y);
            case "FLOATER" -> new Floater(objectString, level, loc.x, loc.y);
            case "MOVINGPLATFORM" -> new MovingPlatform(objectString, level, loc.x, loc.y, rectangle.height);
            default -> null;
        };
    }

}
