package model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import model.GameContactListener;
import model.Level;
import model.helper.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private Player player;
    private World world;
    private Level level;

    @BeforeEach
    void setup() {
        world = new World(new Vector2(0, 0), false);
        level = mock(Level.class);
        when(level.getWorld()).thenReturn(world);
        player = new Player(level, 0, 0);

        List<Player> players = new ArrayList<>();
        players.add(player);
        when(level.getGameObjects(Player.class)).thenReturn(players);
        world.setContactListener(new GameContactListener(level));
    }

    @Test
    void testPlayerMovesHorizontally() {
        assertEquals(new Vector2(0, 0), player.getPosition());
        assertTrue(player.facingRight);
        assertFalse(player.grounded);

        player.setGrounded(true);
        player.moveHorizontally(false);
        player.update();
        doStep();
        player.update();

        assertTrue(player.getPosition().x < 0);
        assertEquals(0, player.getPosition().y);
        assertFalse(player.facingRight);
        assertEquals(Player.State.STANDING, player.previousState);
        assertEquals(Player.State.WALKING, player.getCurrentState());

        for (int i = 0; i < 3; i++) {
            player.moveHorizontally(true);
            player.update();
            doStep();
        }

        assertTrue(player.getPosition().x > 0);
        assertEquals(0, player.getPosition().y);
        assertTrue(player.facingRight);
    }

    @Test
    void testPlayerJumps() {
        assertEquals(new Vector2(0, 0), player.getPosition());

        player.setGrounded(true);
        player.jump();
        player.setGrounded(true);
        player.jump();
        player.update();
        doStep();
        // Player's state gets updated the frame after the body gets updated
        player.update();

        assertTrue(player.getPosition().y > 0);
        assertEquals(0, player.getPosition().x);
        assertEquals(Player.State.JUMPING, player.getCurrentState());
        assertEquals(Player.State.STANDING, player.previousState);
        assertFalse(player.grounded);
    }

    @Test
    void testPlayerDrops() {
        assertEquals(new Vector2(0, 0), player.getPosition());

        player.drop();
        player.update();
        doStep();
        player.update();

        assertTrue(player.getPosition().y < 0);
        assertEquals(0, player.getPosition().x);
        assertEquals(Player.State.FALLING, player.getCurrentState());
        assertEquals(Player.State.STANDING, player.previousState);
    }

    @Test
    void testPlayerCanGoThroughPlatform() {

    }

    @Test
    void testPlayerDies() {
        assertEquals(100, player.getHp());
        player.takeDamage(90);
        assertEquals(10, player.getHp());
        player.takeDamage(10);
        assertEquals(-1, player.getHp());
        assertEquals(Player.State.STANDING, player.previousState);
        assertTrue(player.isDead());

        Array<Fixture> fixtureArray = player.getBody().getFixtureList();
        for (int i = 0; i < fixtureArray.size; i++) {
            Filter filter = fixtureArray.get(i).getFilterData();
            assertEquals(Constants.DESTROYED_BIT, filter.categoryBits);
            assertEquals(Constants.DESTROYED_MASK_BITS, filter.maskBits);
        }
        assertEquals(5, player.getBody().getLinearVelocity().y);

        player.update();
        assertEquals(Player.State.DEAD, player.getCurrentState());
    }

    @Test
    void testPlayerTeleport() {
        assertEquals(player.getPosition(), new Vector2(0, 0));
        player.setPosition(10, 10);
        assertEquals(player.getPosition(), new Vector2(10 * Constants.PPM, 10 * Constants.PPM));
        assertEquals(player.getBody().getAngle(), 0);
    }

    private void doStep() {
        world.step(1/60f, 6, 2);
    }
}
