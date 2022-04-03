package model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import model.helper.ContactType;
import model.objects.*;

import java.util.List;

public class GameContactListener implements ContactListener {

    private final Level level;

    public GameContactListener(Level level) {
        this.level = level;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        groundContact(a, b, true);
        leftContact(a, b, true);
        rightContact(a, b, true);
        headContact(a, b, true);
        platformContact(a,b,true);
        cameraWallContact(a,b,true);

        coinContact(a, b);
        goombaContact(a, b);
        goombaRadar(a,b,true);
        goalContact(a, b);

        deathContact(a, b);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        groundContact(a, b, false);
        leftContact(a, b, false);
        rightContact(a, b, false);
        headContact(a, b, false);
        platformContact(a,b,false);

        goombaRadar(a,b,false);
        cameraWallContact(a,b,false);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    /**
     * This method finds and return the correct contactObject of a given type involved
     * in a given collision between Fixture a and Fixture b.
     *
     * @param a - The first Fixture involved in the contact.
     * @param b - The second Fixture involved in the contact.
     * @param type - The class of what contactObject you want. Need it on this format: ClassName.class
     * @param <T> - Generic type that extends from GameObject.
     * @return Return the player involved in the contact.
     */
    private <T extends GameObject> T getContactObject(Fixture a, Fixture b, Class<T> type) {
        List<T> objects = level.getGameObjects(type);

        ContactType contactType; // TODO: Find some way to get ContactType from the generic input "type".

        if (type.isAssignableFrom(Player.class)) {
            contactType = ContactType.PLAYER;
        } else if (type.isAssignableFrom(Goomba.class)) {
            contactType = ContactType.ENEMY;
        } else if (type.isAssignableFrom(MapEndPoints.class)) {
            contactType = ContactType.CAMERA_WALL;
        } else if (type.isAssignableFrom(Coin.class)) {
            contactType = ContactType.COIN;
        } else if (type.isAssignableFrom(Goal.class)) {
            contactType = ContactType.GOAL;
        }else {
            throw new NullPointerException("Class " + type + " not valid.");
        }

        Fixture objectFixture = a.getUserData() == contactType ? a : b;

        for (T object : objects) {
            if (object.getBody().equals(objectFixture.getBody())) {
                return object;
            }
        }

        throw new NullPointerException("No such " + type + " found.");
    }

    /**
     * This method checks if one of the fixtures has the given ContactType.
     * @param a - Fixture
     * @param b - Fixture
     * @param contactType - The ContactType to we're checking for.
     * @return - Returns true if one of the fixtures have the given ContactType.
     */
    private boolean checkContactType(Fixture a, Fixture b, ContactType contactType) {
        return (a.getUserData() == contactType || b.getUserData() == contactType);
    }

    /**
     * This method checks if one of the fixtures has the given Sensor.
     * @param a - Fixture
     * @param b - Fixture
     * @param sensorName - The Sensor to we're checking for.
     * @return - Returns true if one of the fixtures have the given Sensor.
     */
    private boolean checkContactSensor(Fixture a, Fixture b, String sensorName) {
        return (a.getUserData().equals(sensorName) || b.getUserData().equals(sensorName));
    }

    private void deathContact(Fixture a, Fixture b) {
        if (checkContactType(a,b,ContactType.DEATH) && checkContactType(a,b,ContactType.PLAYER)) {
            Player player = getContactObject(a, b,Player.class);
            player.setDead();
        }
    }

    private void coinContact(Fixture a, Fixture b) {
        if (checkContactType(a,b,ContactType.COIN) && checkContactType(a,b,ContactType.PLAYER)) {
            Coin coin = getContactObject(a,b,Coin.class);
            Player player = getContactObject(a,b,Player.class);
            coin.onHit();
            player.increaseHealth(10);
        }
    }

    private void goalContact(Fixture a, Fixture b) {
        if (checkContactType(a,b,ContactType.GOAL) && checkContactType(a,b,ContactType.PLAYER)) {
            Goal goal = getContactObject(a,b,Goal.class);
            goal.onHit();
        }
    }

    /**
     * Checks if a contact is between a Player and a Goomba object.
     * If yes, damages player.
     *
     * @param a - The first Fixture involved in the contact.
     * @param b - The second Fixture involved in the contact.
     */
    private void goombaContact(Fixture a, Fixture b) {
        if (checkContactType(a,b,ContactType.ENEMY) && checkContactType(a,b,ContactType.PLAYER)) {
            Goomba goomba = getContactObject(a,b,Goomba.class);
            Player player = getContactObject(a,b,Player.class);

            if (player.getState() == Player.State.FALLING) { //TODO: Make attack/drop state or something in player.
                goomba.setDead();
            } else {
                player.takeDamage(Goomba.getAttack());
            }
        }
    }

    private void goombaRadar(Fixture a, Fixture b, boolean begin) {
        if (checkContactSensor(a,b,"goombaRadar") && checkContactType(a,b,ContactType.PLAYER)) {
            Goomba goomba = getContactObject(a,b,Goomba.class);
            Player player = getContactObject(a,b,Player.class);
            Vector2 playerPosition = player.getPosition();
            goomba.setPlayerPostion(playerPosition);
            goomba.setPlayerNearby(begin);
        }
    }

    private void groundContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactSensor(a,b,"foot") && (checkContactType(a,b,ContactType.GROUND) || checkContactType(a,b,ContactType.PLATFORM))) {
            Player player = getContactObject(a,b,Player.class);
            player.setGrounded(begin);
        }
    }

    private void leftContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactSensor(a,b,"left") && (checkContactType(a,b,ContactType.GROUND) || checkContactType(a,b,ContactType.PLATFORM))) {
            Player player = getContactObject(a,b,Player.class);
            player.setLeftCollision(begin);
        }
    }

    private void rightContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactSensor(a,b,"right") && (checkContactType(a,b,ContactType.GROUND) || checkContactType(a,b,ContactType.PLATFORM))) {
            Player player = getContactObject(a,b,Player.class);
            player.setRightCollision(begin);
        }
    }

    private void cameraWallContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactType(a,b,ContactType.CAMERA_WALL) && checkContactType(a,b,ContactType.PLAYER)) {
            Player player = getContactObject(a,b,Player.class);
            MapEndPoints wall = getContactObject(a,b,MapEndPoints.class);

            if (player.getPosition().x > wall.getPosition().x) {
                player.setLeftCollision(begin);
            } else {
                player.setRightCollision(begin);
            }
        }
    }

    private void headContact(Fixture a, Fixture b, boolean begin) {
        if ( checkContactSensor(a,b,"head") && (checkContactType(a,b,ContactType.GROUND) || checkContactType(a,b,ContactType.PLATFORM) ) ) {
            Player player = getContactObject(a,b,Player.class);
            player.setHeadCollision(begin);
        }
    }

    private void platformContact(Fixture a, Fixture b, boolean begin) {
        if (checkContactType(a,b,ContactType.PLATFORM) && checkContactSensor(a,b,"foot")) {
            Player player = getContactObject(a,b,Player.class);
            player.setOnPlatform(begin);
        }
    }

}
