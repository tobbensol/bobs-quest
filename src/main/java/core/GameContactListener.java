package core;

import com.badlogic.gdx.physics.box2d.*;
import helper.Constants;
import helper.ContactType;

public class GameContactListener implements ContactListener {

    private GameScreen gameScreen;

    public GameContactListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
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

    private void groundContact(Fixture a, Fixture b, boolean begin) {
        if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
            if (a.getUserData().equals("foot") || b.getUserData().equals("foot")) {
                gameScreen.getPlayer().setGrounded(begin);
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

        if (a.getUserData().equals(direction) || b.getUserData().equals(direction)) {
            gameScreen.getPlayer().setSideCollision(begin);
            //System.out.println("Collision between player and ground!");
        }
    }

}
