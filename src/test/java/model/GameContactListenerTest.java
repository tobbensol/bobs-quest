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
        player.getBody().setLinearVelocity(0, -1);
        player.setState();

        verifyNoInteractions(enemy);
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
    void testPlayerCameraWallContact() {
        fail();
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
