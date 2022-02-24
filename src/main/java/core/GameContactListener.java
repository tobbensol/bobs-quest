package core;

import com.badlogic.gdx.physics.box2d.*;
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

        if (a == null || b == null) {
            return;
        }
        if (a.getUserData() == null || b.getUserData() == null) {
            return;
        }

        if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {

            // Contact between PLAYER and GROUND
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {

                // TODO: Need to somehow check if the contact between player and ground only happens at the from the up-side of ground
                //  (side-wise and from the under-side shouldn't count)
                //  Possible solution: Maybe attaching a sensor under the the foot of the player and hence only checking contact with that.
                /*
                if (a.getBody().getPosition().y == b.getBody().getPosition().y) {
                    System.out.println(a.getBody().getPosition().y);
                }

                 */
                // TODO: Need to get access to the player and set isOnGround = true.
                System.out.println("Contact between a player and ground!");
                boolean value = gameScreen.getPlayer().setOnGround(true);
                System.out.println(value);

            }

        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) {
            return;
        }
        if (a.getUserData() == null || b.getUserData() == null) {
            return;
        }

        if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER) {
            // Contact between PLAYER and GROUND
            if (a.getUserData() == ContactType.GROUND || b.getUserData() == ContactType.GROUND) {
                // TODO: Need to get access to the player and set isOnGround = false.
                System.out.println("No contact");
                boolean value = gameScreen.getPlayer().setOnGround(false);
                System.out.println(value);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
