package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import helper.MockGL;
import model.helper.AudioHelper;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;
import model.objects.*;
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

        AudioHelper audioHelper = mock(AudioHelper.class);
        Sound sound = mock(Sound.class);
        level = mock(Level.class);
        listener = new GameContactListener(level);
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(listener);
        when(level.getWorld()).thenReturn(world);
        when(level.getAudioHelper()).thenReturn(audioHelper);
        when(audioHelper.getSoundEffect(anyString())).thenReturn(sound);

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
            BodyHelper.createEnvironmentBody(shape, world, contactType, Constants.PLATFORM_BIT, Constants.DEFAULT_MASK_BITS, false);
        } else {
            BodyHelper.createEnvironmentBody(shape, world, contactType, Constants.DEFAULT_BIT, Constants.DEFAULT_MASK_BITS, contactType == ContactType.DEATH);
        }
    }

    private void doStep() {
        world.step(1 / 60f, 6, 2);
    }

    @Test
    void testPlayerNotFallingEnemyContact() {
        Enemy enemy = stubEnemy();
        doNothing().when(player).takeDamage(anyInt());

        verifyNoInteractions(player);
        verifyNoInteractions(enemy);
        assertNotEquals(Player.State.FALLING, player.getCurrentState());

        doStep();
        verify(enemy, never()).onHit();
        verify(player, times(1)).takeDamage(enemy.getAttack());

        destroyFirstBody();
        verify(enemy, never()).onHit();
        verify(player, times(1)).takeDamage(enemy.getAttack());
    }

    @Test
    void testPlayerFallingEnemyContact() {
        Enemy enemy = stubEnemy();
        player.getBody().setLinearVelocity(0, -2);
        player.setState();

        verifyNoInteractions(enemy);
        //doesn't work since falling is less sensetive now and needs a speed of less than -1.5 to activate falling (used to be -0.5, but that would activave falling when standing on a platform)
        assertEquals(Player.State.FALLING, player.getCurrentState());

        doStep();
        verify(enemy, times(1)).onHit();
        verify(player, never()).takeDamage(enemy.getAttack());

        destroyFirstBody();
        verify(enemy, times(1)).onHit();
        verify(player, never()).takeDamage(enemy.getAttack());
    }

    @Test
    void testPlayerEnemyRadar() {
        Enemy enemy = stubEnemy();
        doNothing().when(player).takeDamage(anyInt());

        verifyNoInteractions(player);
        verifyNoInteractions(enemy);

        doStep();
        verify(enemy, times(1)).setPlayerPosition(player.getPosition());
        verify(enemy, times(1)).setPlayerNearby(true);

        destroyFirstBody();
        verify(enemy, times(2)).setPlayerPosition(player.getPosition());
        verify(enemy, times(1)).setPlayerNearby(false);
    }

    private Enemy stubEnemy() {
        Enemy enemy = spy(new Goomba("GOOMBA", level, 0, 0));
        List<Enemy> enemyList = new ArrayList<>();
        enemyList.add(enemy);
        when(level.getGameObjects(Enemy.class)).thenReturn(enemyList);
        when(level.getClassName(Enemy.class)).thenReturn("Enemy");
        return enemy;
    }

    @Test
    void testPlayerGoalContact() {
        Goal goal = stubGoal();

        verifyNoInteractions(player);
        verifyNoInteractions(goal);

        doStep();
        verify(goal, times(1)).onHit();

        destroyFirstBody();
        verify(goal, times(1)).onHit();
    }

    private Goal stubGoal() {
        Goal goal = spy(new Goal("GOAL", level, 0, 0));
        List<Goal> goalList = new ArrayList<>();
        goalList.add(goal);
        when(level.getGameObjects(Goal.class)).thenReturn(goalList);
        when(level.getClassName(Goal.class)).thenReturn("Goal");
        return goal;
    }

    @Test
    void testPlayerCoinContact() {
        Coin coin = stubCoin();
        doNothing().when(coin).onHit();

        verifyNoInteractions(player);
        verifyNoInteractions(coin);

        doStep();
        verify(coin, times(1)).onHit();
        verify(player, times(1)).increaseHealth(10);

        destroyFirstBody();
        verify(coin, times(1)).onHit();
        verify(player, times(1)).increaseHealth(10);

    }

    private Coin stubCoin() {
        Coin coin = spy(new Coin("COIN", level, 0, 0));
        List<Coin> coinList = new ArrayList<>();
        coinList.add(coin);
        when(level.getGameObjects(Coin.class)).thenReturn(coinList);
        when(level.getClassName(Coin.class)).thenReturn("Coin");
        return coin;
    }

    @Test
    void testPlayerCameraWallContactWhenPlayerToTheRight() {
        MapEndPoints wall = stubMapEndPoints();
        wall.setPosition(-0.1f, 0);

        verifyNoInteractions(player);
        assertEquals(-0.1f, wall.getPosition().x);

        doStep();

        verify(player, times(1)).setLeftCollision(true);

        destroyFirstBody();

        verify(player, times(1)).setLeftCollision(false);
    }

    @Test
    void testPlayerCameraWallContactWhenPlayerToTheLeft() {
        MapEndPoints wall = stubMapEndPoints();
        wall.setPosition(0.1f, 0);

        verifyNoInteractions(player);
        assertEquals(0.1f, wall.getPosition().x);

        doStep();

        verify(player, times(1)).setRightCollision(true);

        destroyFirstBody();

        verify(player, times(1)).setRightCollision(false);
    }

    private MapEndPoints stubMapEndPoints() {
        MapEndPoints wall = spy(new MapEndPoints("WALL", level, 0, 0));
        List<MapEndPoints> wallList = new ArrayList<>();
        wallList.add(wall);
        when(level.getGameObjects(MapEndPoints.class)).thenReturn(wallList);
        when(level.getClassName(MapEndPoints.class)).thenReturn("MapEndPoints");
        return wall;
    }

    @Test
    void testValidFixtures() {
        Contact contact = mock(Contact.class);
        when(contact.getFixtureA()).thenReturn(null);
        when(contact.getFixtureB()).thenReturn(null);

        listener.beginContact(contact);

        contact = mock(Contact.class);
        Fixture a = mock(Fixture.class);
        Fixture b = mock(Fixture.class);

        when(contact.getFixtureA()).thenReturn(a);
        when(contact.getFixtureB()).thenReturn(b);
        when(a.getUserData()).thenReturn("a");
        when(b.getUserData()).thenReturn(null);

        listener.beginContact(contact);

        verify(a, times(1)).getUserData();
        verify(b, times(1)).getUserData();
        verify(a, never()).getFilterData();
        verify(b, never()).getFilterData();

        contact = mock(Contact.class);
        a = mock(Fixture.class);
        b = mock(Fixture.class);

        when(contact.getFixtureA()).thenReturn(a);
        when(contact.getFixtureB()).thenReturn(b);
        when(a.getUserData()).thenReturn("a");
        when(b.getUserData()).thenReturn("b");
        Filter aFilter = mock(Filter.class);
        Filter bFilter = mock(Filter.class);
        aFilter.categoryBits = Constants.DEFAULT_BIT;
        bFilter.categoryBits = Constants.DESTROYED_BIT;
        when(a.getFilterData()).thenReturn(aFilter);
        when(b.getFilterData()).thenReturn(bFilter);

        listener.beginContact(contact);

        verify(a, times(1)).getFilterData();
        verify(b, times(1)).getFilterData();
    }

    @Test
    void testEmptyContactObjectsThrowsNullPointerException() {
        createTestEnvironment(ContactType.GROUND);

        verifyNoInteractions(player);
        when(level.getGameObjects(Player.class)).thenReturn(new ArrayList<>());

        try {
            doStep();
            fail();
        } catch (NullPointerException ignored) {}
    }

}
