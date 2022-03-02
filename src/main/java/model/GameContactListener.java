package model;

import com.badlogic.gdx.physics.box2d.*;
import view.GameScreen;
import model.helper.ContactType;
import model.objects.Player;
import java.util.List;

public class GameContactListener implements ContactListener {

    private GameModel gameModel;

    public GameContactListener(GameModel gameModel) {
        this.gameModel = gameModel;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        groundContact(a,b,true);
        headContact(a,b,true);
        horizontalContact(a,b,true,true); // Right contact
        horizontalContact(a,b,true,false); // Left contact

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;
        if (a.getUserData() == null || b.getUserData() == null)
            return;

        groundContact(a,b,false);
        headContact(a,b,false);
        horizontalContact(a,b,false,true); // Right contact
        horizontalContact(a,b,false,false); // Left contact
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
     * @param a - The first Fixture involved in the contact.
     * @param b - The second Fixture involved in the contact.
     * @return Return the player involved in the contact.
     */
    private Player getContactPlayer(Fixture a, Fixture b) {
        List<Player> players = gameModel.getPlayers();
        Fixture p;

        if (a.getBody().getType().equals(BodyDef.BodyType.DynamicBody)) {
            p = a;
        }
        else if (b.getBody().getType().equals(BodyDef.BodyType.DynamicBody)) {
            p = b;
        }
        else {
            return null;
        }

        for (Player player : players) {
            // TODO: find out how to find the correct player.
            if (player.getBody().equals(p.getBody())) {
                return player;
            }
        }
        return null;
        //return gameScreen.getPlayer();
    }


    private void groundContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
                getContactPlayer(a,b).setGrounded(begin);
            }
        }
    }

    private void headContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            if (a.getUserData().equals("head") || b.getUserData().equals("head")) {
                //System.out.println("Collision between head and ground!");
                // TODO: implement logic
            }
        }
    }

    private void horizontalContact(Fixture a, Fixture b, boolean begin, boolean right) {
        String direction;
        if (right)
            direction = "right";
        else
            direction = "left";

        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            if (a.getUserData().equals(direction) || b.getUserData().equals(direction)) {
                getContactPlayer(a,b).setSideCollision(begin);
                System.out.println("Collision between player and ground!");
            }
        }

    }

}
