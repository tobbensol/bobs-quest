package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import helper.MockGL;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;
import model.objects.Enemy;
import model.objects.Goal;
import model.objects.Goomba;
import model.objects.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameContactListenerTest {

    private GameContactListener listener;
    private World world;
    private Player player;
    private Level level;

    @BeforeEach
    void setup() {
        new HeadlessApplication(new Game() {
            public void create() {
            }
        });
        Gdx.gl = new MockGL();

        level = mock(Level.class);
        listener = new GameContactListener(level);
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(listener);
        when(level.getWorld()).thenReturn(world);

        player = spy(new Player("TEST", level, 0, 0));
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        when(level.getGameObjects(Player.class)).thenReturn(playerList);
        when(level.getClassName(Player.class)).thenReturn("Player");
    }

    @Test
    void testPlayerGroundContact() {
        createTestEnvironment(ContactType.GROUND);

        verifyNoInteractions(player);

        doStep();
        verify(player, times(1)).setGrounded(true);
        verify(player, times(1)).setRightCollision(true);
        verify(player, times(1)).setLeftCollision(true);

        destroyFirstBody();

        verify(player, times(1)).setGrounded(false);
        verify(player, times(1)).setRightCollision(false);
        verify(player, times(1)).setLeftCollision(false);
    }

    @Test
    void testPlayerPlatformContact() {
        createTestEnvironment(ContactType.PLATFORM);

        verifyNoInteractions(player);

        doStep();
        verify(player, times(1)).setOnPlatform(true);

        destroyFirstBody();

        verify(player, times(1)).setOnPlatform(false);
    }

    @Test
    void testPlayerDeathContact() throws InterruptedException {
        createTestEnvironment(ContactType.DEATH);

        verifyNoInteractions(player);
        assertFalse(player.isDead());
        assertFalse(player.isDestroyed());

        doStep();
        verify(player, times(1)).setDead();
        assertTrue(player.isDead());
        assertFalse(player.isDestroyed());

        destroyFirstBody();
        verify(player, times(1)).setDead();
        assertTrue(player.isDead());
        assertFalse(player.isDestroyed());

        Thread.sleep(1800);

        assertTrue(player.isDestroyed());
    }

    private void destroyFirstBody() {
        Array<Body> bodyArray = new Array<>();
        world.getBodies(bodyArray);
        world.destroyBody(bodyArray.get(0));
    }

    private void createTestEnvironment(ContactType contactType) {
        Polygon polygon = new Polygon();
        polygon.setVertices(new float[] {-100,-100, 100,-100, 100,100, -100,100});
        polygon.setPosition(0,0);
        polygon.setOrigin(0,0);
        PolygonMapObject polygonMapObject = new PolygonMapObject(polygon);
        Shape shape = BodyHelper.createShape(polygonMapObject);
        if (contactType == ContactType.PLATFORM) {
            BodyHelper.createEnvironmentBody(shape, world, contactType, Constants.PLATFORM_BIT, Constants.PLATFORM_MASK_BITS, false);
        } else {
            BodyHelper.createEnvironmentBody(shape, world, contactType, Constants.DEFAULT_BIT, Constants.DEFAULT_MASK_BITS, contactType == ContactType.DEATH);
        }
    }

    private void doStep() {
        world.step(1 / 60f, 6, 2);
    }

    @Test
    void testValidFixtures() {
        fail();
    }

    @Test
    void testEmptyContactObjects() {
        fail();
    }

}
