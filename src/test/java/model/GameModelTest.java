package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import launcher.Boot;
import model.objects.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameModelTest {

    private GameModel model;
    private Level level;
    private World world;

    @BeforeEach
    void setup() {
        new HeadlessApplication(new Game() {
            public void create() {
            }
        });
        world = new World(new Vector2(0, 0), false);

        level = mock(Level.class);
        when(level.getWorld()).thenReturn(world);

        model = spy(new GameModel());
        doReturn(level).when(model).createLevel();
        when(model.getLevel()).thenReturn(level);
        doNothing().when(model).createCamera();
    }

    @Test
    void testSetNumPlayers() {
        doReturn(false).when(model).restart();

        assertEquals(1, model.getNumPlayers());
        model.setNumPlayers(3);
        assertEquals(3, model.getNumPlayers());
        assertEquals(3, model.getNumControllers());
    }

    @Test
    void testUpdateModel() {
        Hud hud = mock(Hud.class);
        when(level.getHud()).thenReturn(hud);
        when(level.isCompleted()).thenReturn(false);
        doNothing().when(model).changeScreen();

        model.update();
    }

    @Test
    void testReload() {
        assertFalse(model.getReload());
        model.setReload(true);
        assertTrue(model.getReload());
    }

    @Test
    void testRestart() {
        // No access to model.levels or model.levelNR
        assertFalse(model.getLevel().isCompleted());
        assertFalse(model.isPaused());

        assertFalse(model.restart());

        verify(model, times(1)).createLevel();
        assertTrue(model.isPaused());

        model.resumeGame();
        assertFalse(model.isPaused());
        when(level.isCompleted()).thenReturn(true);

        assertTrue(model.restart());

        verify(model, times(2)).createLevel();
        assertTrue(model.isPaused());
    }

    @Test
    void testSetStateThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.setState(null));

        assertEquals(GameState.STARTUP, model.getState());
        assertThrows(IllegalArgumentException.class, () -> model.setState(GameState.GAME_OVER));
        assertThrows(IllegalArgumentException.class, () -> model.setState(GameState.NEXT_LEVEL));

        model.setState(GameState.ACTIVE);
        model.setState(GameState.GAME_OVER);
        assertThrows(IllegalArgumentException.class, () -> model.setState(GameState.STARTUP));
        assertThrows(IllegalArgumentException.class, () -> model.setState(GameState.NEXT_LEVEL));

        model.setState(GameState.ACTIVE);
        model.setState(GameState.NEXT_LEVEL);
        assertThrows(IllegalArgumentException.class, () -> model.setState(GameState.GAME_OVER));
        assertThrows(IllegalArgumentException.class, () -> model.setState(GameState.STARTUP));
    }

    @Test
    void testChangeScreen() { // Test: a.method() -> b.setField(new c). Cannot initialize c
        Boot.INSTANCE = mock(Boot.class);
        doNothing().when(Boot.INSTANCE).setScreen(any(Screen.class));


        assertEquals(GameState.STARTUP, model.getState());
//        model.changeScreen();
        fail();
    }

    @Test
    void testWhenGameOver() throws InterruptedException {
        Player player = new Player(level, 0, 0);
        List<Player> players = new ArrayList<>();
        players.add(player);
        when(level.getGameObjects(Player.class)).thenReturn(players);

        Hud hud = mock(Hud.class);
        when(level.getHud()).thenReturn(hud);
        when(level.isCompleted()).thenReturn(false);
        doNothing().when(model).changeScreen();

        model.update();
        model.resumeGame();

        assertFalse(model.isPaused());
        assertFalse(player.getFrozen());
        assertEquals(GameState.STARTUP, model.getState());

        model.setState(GameState.ACTIVE);
        model.update();

        verify(model, never()).changeScreen();
        verify(model, never()).restart();
        assertFalse(model.isPaused());
        assertEquals(GameState.ACTIVE, model.getState());

        player.setDead();
        Thread.sleep(2000);
        assertTrue(player.getFrozen());

        model.update();

        verify(model, times(1)).changeScreen();
        verify(model, times(1)).restart();
        assertTrue(model.isPaused());
        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    void testWhenLevelCompleted() {
        fail();
        // TODO
    }

}

