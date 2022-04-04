package model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import model.GameContactListener;
import model.GameModel;
import model.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private static Player player;
    private static World world;

    @BeforeAll
    static void setup() {
        world = new World(new Vector2(0, 0), false);
        Level level = mock(Level.class);
        when(level.getWorld()).thenReturn(world);
        player = new Player(level, 0, 0);

        // TODO: Check if player is grounded
        List<Player> players = new ArrayList<>();
        players.add(player);
        when(level.getGameObjects(Player.class)).thenReturn(players);
        world.setContactListener(new GameContactListener(level));
    }

    @Test
    void testPlayerMovesLeft() {
        assertEquals(new Vector2(0, 0), player.getPosition());
        assertTrue(player.facingRight);
        player.moveHorizontally(1.0f, false);
        doStep();
        assertTrue(player.getPosition().x < 0);
        assertEquals(0, player.getPosition().y);
        assertFalse(player.facingRight);
    }

    @Test
    void testPlayerJumps() {
        assertEquals(new Vector2(0, 0), player.getPosition());

        player.setGrounded(true);
        player.jump(1.0f);
        doStep();
        player.update();

        assertTrue(player.getPosition().y > 0);
        assertEquals(0, player.getPosition().x);
        assertEquals(Player.State.JUMPING, player.currentState);
        assertEquals(Player.State.JUMPING, player.getState());
        assertEquals(Player.State.STANDING, player.previousState);
        assertFalse(player.grounded);
    }

    private void doStep() {
        world.step(1/60f, 6, 2);
    }
}
