package model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import model.helper.Constants;
import model.helper.ContactType;
import model.objects.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameContactListener implements ContactListener {

    private final Level level;
    private final Map<String, short[]> contactCategoryBits;

    public GameContactListener(Level level) {
        this.level = level;

        contactCategoryBits = new HashMap<>();
        contactCategoryBits.put("Player", new short[]{Constants.PLAYER_BIT, Constants.PLAYER_PASSING_THROUGH_PLATFORM_BIT});
        contactCategoryBits.put("Coin", new short[]{Constants.COIN_BIT});
        contactCategoryBits.put("Goal", new short[]{Constants.GOAL_BIT});
        contactCategoryBits.put("Goomba", new short[]{Constants.ENEMY_BIT});
        contactCategoryBits.put("Floater", new short[]{Constants.ENEMY_BIT});
        contactCategoryBits.put("Death", new short[]{Constants.DEFAULT_BIT});
        contactCategoryBits.put("MapEndPoints", new short[]{Constants.CAMERA_WALL_BIT});
        contactCategoryBits.put("Enemy", new short[]{Constants.ENEMY_BIT});
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (notValidFixtures(a, b)) return;

        groundContact(a, b, true);
        platformContact(a, b, true);
        sideContact(a, b, true, true);
        sideContact(a, b, false, true);

        enemyContact(a, b);
        enemyRadar(a, b, true);

        goalContact(a, b);
        coinContact(a, b);

        deathContact(a, b);

        cameraWallContact(a, b, true);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (notValidFixtures(a, b)) return;

        groundContact(a, b, false);
        platformContact(a, b, false);
        sideContact(a, b, true, false);
        sideContact(a, b, false, false);

        enemyRadar(a, b, false);

        cameraWallContact(a, b, false);
    }

    private boolean notValidFixtures(Fixture a, Fixture b) {
        if (a == null || b == null)
            return true;
        if (a.getUserData() == null || b.getUserData() == null)
            return true;
        return a.getFilterData().categoryBits == Constants.DESTROYED_BIT || b.getFilterData().categoryBits == Constants.DESTROYED_BIT;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    /**
     * This method should return the category bits of given class.
     *
     * @param type - The class you want to find the contact type of.
     * @param <T>  - Generic type that extends from IGameObject.
     * @return - The category bits of the given class.
     */
    private <T extends IGameObject> short[] getCategoryBits(Class<T> type) {
        return contactCategoryBits.get(level.getClassName(type));
    }

    /**
     * This method finds and return the correct contactObject of a given type involved
     * in a given collision between Fixture a and Fixture b.
     *
     * @param a    - The first Fixture involved in the contact.
     * @param b    - The second Fixture involved in the contact.
     * @param type - The class of what contactObject you want. Need it on this format: ClassName.class
     * @param <T>  - Generic type that extends from IGameObject.
     * @return Return the player involved in the contact.
     */
    private <T extends IGameObject> T getContactObject(Fixture a, Fixture b, Class<T> type) {
        List<T> objects = level.getGameObjects(type);
        short[] categoryBits = getCategoryBits(type);

        boolean bitFound = false;
        for (short bit : categoryBits) {
            if (a.getFilterData().categoryBits == bit) {
                bitFound = true;
                break;
            }
        }
        Fixture objectFixture = bitFound ? a : b;

        for (T object : objects) {
            if (object.getBody().equals(objectFixture.getBody())) {
                return object;
            }
        }
        throw new NullPointerException("No such " + type + " found.");
    }

    /**
     * This method checks if one of the fixtures has the given ContactType.
     *
     * @param a           - Fixture
     * @param b           - Fixture
     * @param contactType - The ContactType to we're checking for.
     * @return - Returns true if one of the fixtures have the given ContactType.
     */
    private boolean checkContactType(Fixture a, Fixture b, ContactType contactType) {
        return (a.getUserData() == contactType || b.getUserData() == contactType);
    }

    /**
     * This method checks if one of the fixtures has the given Sensor.
     *
     * @param a          - Fixture
     * @param b          - Fixture
     * @param sensorName - The Sensor to we're checking for.
     * @return - Returns true if one of the fixtures have the given Sensor.
     */
    private boolean checkContactSensor(Fixture a, Fixture b, String sensorName) {
        return (a.getUserData().equals(sensorName) || b.getUserData().equals(sensorName));
    }

    private void deathContact(Fixture a, Fixture b) {
        if (checkContactType(a, b, ContactType.DEATH) && checkContactType(a, b, ContactType.PLAYER)) {
            Player player = getContactObject(a, b, Player.class);
            player.setDead();
        }
    }

    private void coinContact(Fixture a, Fixture b) {
        if (checkContactType(a, b, ContactType.COIN) && checkContactType(a, b, ContactType.PLAYER)) {
            Coin coin = getContactObject(a, b, Coin.class);
            Player player = getContactObject(a, b, Player.class);
            coin.onHit();
            player.increaseHealth(10);
        }
    }

    private void goalContact(Fixture a, Fixture b) {
        if (checkContactType(a, b, ContactType.GOAL) && checkContactType(a, b, ContactType.PLAYER)) {
            Goal goal = getContactObject(a, b, Goal.class);
            goal.onHit();
        }
    }

    private void enemyContact(Fixture a, Fixture b) {
        if (checkContactType(a, b, ContactType.ENEMY) && checkContactType(a, b, ContactType.PLAYER)) {
            Enemy enemy = getContactObject(a, b, Enemy.class);
            Player player = getContactObject(a, b, Player.class);

            if (player.getCurrentState() == Player.State.FALLING) {
                enemy.onHit();
            } else {
                player.takeDamage(enemy.getAttack());
            }
        }
    }

    private void enemyRadar(Fixture a, Fixture b, boolean begin) {
        if (checkContactSensor(a, b, "enemyRadar") && checkContactType(a, b, ContactType.PLAYER)) {
            Enemy enemy = getContactObject(a, b, Enemy.class);
            Player player = getContactObject(a, b, Player.class);
            Vector2 playerPosition = player.getPosition();

            enemy.setPlayerPosition(playerPosition);
            enemy.setPlayerNearby(begin);
        }
    }

    private void groundContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactSensor(a, b, "foot") && (checkContactType(a, b, ContactType.GROUND) || checkContactType(a, b, ContactType.PLATFORM))) {
            Player player = getContactObject(a, b, Player.class);
            player.setGrounded(begin);
        }
    }

    private void sideContact(Fixture a, Fixture b, boolean right, boolean begin) {
        String direction = right ? "right" : "left";
        if (checkContactSensor(a, b, direction) && (checkContactType(a, b, ContactType.GROUND) || checkContactType(a, b, ContactType.PLATFORM))) {
            Player player = getContactObject(a, b, Player.class);
            if (right) {
                player.setRightCollision(begin);
            } else {
                player.setLeftCollision(begin);
            }
        }
    }

    private void cameraWallContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactType(a, b, ContactType.CAMERA_WALL) && checkContactType(a, b, ContactType.PLAYER)) {
            Player player = getContactObject(a, b, Player.class);
            MapEndPoints wall = getContactObject(a, b, MapEndPoints.class);

            if (player.getPosition().x > wall.getPosition().x) {
                player.setLeftCollision(begin);
            } else {
                player.setRightCollision(begin);
            }
        }
    }

    private void platformContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactType(a, b, ContactType.PLATFORM) && checkContactSensor(a, b, "foot")) {
            Player player = getContactObject(a, b, Player.class);
            player.setOnPlatform(begin);
        }
    }

}
