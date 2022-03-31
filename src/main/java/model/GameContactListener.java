package model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import model.helper.ContactType;
import model.objects.Coin;
import model.objects.Goal;
import model.objects.Goomba;
import model.objects.Player;

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
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    /**
     * This function should find and return the correct player
     * involved in a given collision between to Fixtures: a and b.
     *
     * @param a - The first Fixture involved in the contact.
     * @param b - The second Fixture involved in the contact.
     * @return Return the player involved in the contact.
     */
    private Player getContactPlayer(Fixture a, Fixture b) {
        List<Player> players = level.getPlayers();

        Fixture p = a.getUserData() == ContactType.PLAYER ? a : b;

        for (Player player : players) {
            if (player.getBody().equals(p.getBody())) {
                return player;
            }
        }
        return null; // TODO: Handle null player
    }

    private Goomba getContactGoomba(Fixture a, Fixture b) {
        List<Goomba> goombas = level.getGoombas();

        Fixture g = a.getUserData() == ContactType.ENEMY ? a : b;

        for (Goomba goomba : goombas) {
            if (goomba.getBody().equals(g.getBody())) {
                return goomba;
            }
        }
        return null;

    }

    private void deathContact(Fixture a, Fixture b) {
        if (a.getUserData() == ContactType.DEATH || b.getUserData() == ContactType.DEATH) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {
                Player player = getContactPlayer(a, b);
                player.setDead();
            }
        }
    }

    private void coinContact(Fixture a, Fixture b) {
        if (a.getUserData() == ContactType.COIN || b.getUserData() == ContactType.COIN) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {

                // Finding out which of the fixtures is a Player and Coin.
                Fixture p = a.getUserData() == ContactType.PLAYER ? a : b; // Use the sane for players! ^^^
                Fixture c = p == a ? b : a;

                for (Coin coin : level.getCoins()) {
                    if (coin.getBody().equals(c.getBody())) {
                        coin.onHit();
                    }
                }
                Player player = getContactPlayer(a, b);
                player.increaseHealth(10);
            }
        }
    }

    private void goalContact(Fixture a, Fixture b) {
        if (a.getUserData() == ContactType.GOAL || b.getUserData() == ContactType.GOAL) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {

                // Finding out which of the fixtures is a Player and Coin.
                // TODO: Extract method
                Fixture p = a.getUserData() == ContactType.PLAYER ? a : b; // Use the sane for players! ^^^
                Fixture c = p == a ? b : a;

                for (Goal goal : level.getGoals()) {
                    if (goal.getBody().equals(c.getBody())) {
                        goal.onHit();
                    }
                }
            }
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
        if (a.getUserData() == ContactType.ENEMY || b.getUserData() == ContactType.ENEMY) {
            Goomba goomba = getContactGoomba(a,b);

            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {
                Player player = getContactPlayer(a, b);

                if (player.getState() == Player.State.FALLING) { //TODO: Make attack/drop state or something in player.
                    goomba.setDead();
                } else {
                    player.takeDamage(Goomba.getAttack());
                }

            }
        }
    }


    private void goombaRadar(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData().equals("goombaRadar") || b.getUserData().equals("goombaRadar")) {
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {
                Goomba goomba = getContactGoomba(a,b);
                Player player = getContactPlayer(a,b);

                Vector2 playerPosition = player.getPosition();
                goomba.setPlayerPostion(playerPosition); // TODO: Maybe make list/pq and remove when end contact? what is best?
                goomba.setPlayerNearby(begin);
            }
        }
    }


    private void groundContact(Fixture a, Fixture b, boolean begin) {

        if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
                getContactPlayer(a, b).setGrounded(begin);
            }
            if (a.getUserData() == ContactType.PLATFORM || b.getUserData() == ContactType.PLATFORM) {
                getContactPlayer(a, b).setGrounded(begin);
            }
        }
    }

    private void leftContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData().equals("left") || b.getUserData().equals("left")) {
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
                getContactPlayer(a, b).setLeftCollision(begin);
            }
            if (a.getUserData() == ContactType.PLATFORM || b.getUserData() == ContactType.PLATFORM) {
                getContactPlayer(a, b).setLeftCollision(begin);
            }
        }
    }

    private void rightContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData().equals("right") || b.getUserData().equals("right")) {
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
                getContactPlayer(a, b).setRightCollision(begin);
            }
            if (a.getUserData() == ContactType.PLATFORM || b.getUserData() == ContactType.PLATFORM) {
                getContactPlayer(a, b).setRightCollision(begin);
            }
        }
    }

    private void headContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData().equals("head") || b.getUserData().equals("head")) {
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
                getContactPlayer(a, b).setHeadCollision(begin);
            }
            if (a.getUserData() == ContactType.PLATFORM || b.getUserData() == ContactType.PLATFORM) {
                getContactPlayer(a, b).setHeadCollision(begin);
            }
        }
    }

    private void platformContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData() == ContactType.PLATFORM || b.getUserData() == ContactType.PLATFORM) {
            Player player = getContactPlayer(a, b);
            if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
                player.setOnPlatform(begin);
            }
        }
    }

}
