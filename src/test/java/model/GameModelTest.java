package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import helper.MockGL;
import launcher.Boot;
import model.helper.AudioHelper;
import model.objects.Goomba;
import model.objects.IGameObject;
import model.objects.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockingDetails;
import org.mockito.internal.util.DefaultMockingDetails;
import view.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameModelTest {

    private GameModel model;
    private Level level;
    private World world;
    private Player player;
    private Music music;

    @BeforeEach
    void setup() {
        new HeadlessApplication(new Game() {
            public void create() {
            }
        });
        Gdx.gl = new MockGL();

        world = new World(new Vector2(0, 0), false);
        music = mock(Music.class);
        level = mock(Level.class);
        when(level.getWorld()).thenReturn(world);

        model = spy(new GameModel());
        doReturn(level).when(model).createLevel();
        when(model.getLevel()).thenReturn(level);
        doNothing().when(model).createCamera();
    }

    @Test
    void testWhenGameOver() throws InterruptedException {
        stubUpdateModel();

        model.update();
        model.resumeGame();

        assertFalse(model.isPaused());
        assertFalse(player.isDestroyed());
        assertEquals(GameState.STARTUP, model.getState());

        model.setState(GameState.ACTIVE);
        model.update();

        verify(model, never()).changeScreen();
        verify(model, never()).restart();
        assertFalse(model.isPaused());
        assertEquals(GameState.ACTIVE, model.getState());

        player.setDead();
        Thread.sleep(2000);
        assertTrue(player.isDestroyed());

        model.update();

        verify(model, times(1)).changeScreen();
        verify(model, times(1)).restart();
        assertTrue(model.isPaused());
        assertEquals(GameState.GAME_OVER, model.getState());
    }

    @Test
    void testWhenLevelCompleted() {
        stubUpdateModel();

        model.update();
        model.resumeGame();

        assertFalse(model.isPaused());
        assertEquals(GameState.STARTUP, model.getState());

        model.setState(GameState.ACTIVE);
        model.update();

        verify(model, never()).changeScreen();
        verify(model, never()).restart();
        assertFalse(model.isPaused());
        assertEquals(GameState.ACTIVE, model.getState());

        when(level.isCompleted()).thenReturn(true);
        model.update();

        verify(model, times(1)).changeScreen();
        verify(model, times(1)).restart();
        assertTrue(model.isPaused());
        assertEquals(GameState.NEXT_LEVEL, model.getState());
    }

    @Test
    void testUpdateObjects() {
        stubUpdateModel();

        List<IGameObject> objects = new ArrayList<>();
        Goomba goomba = mock(Goomba.class);
        objects.add(goomba);
        when(level.getGameObjects()).thenReturn(objects);
//        doNothing().when(model).setState(any(GameState.class));

        verifyNoInteractions(goomba);

        model.update();
        model.resumeGame();
        model.update();

        verify(goomba, times(1)).update();
    }

    private void stubUpdateModel() {
        player = new Player("TEST", level, 0, 0);
        List<Player> players = new ArrayList<>();
        players.add(player);
        when(level.getGameObjects(Player.class)).thenReturn(players);

        stubGraphics();

        when(level.getLevelMusic()).thenReturn(music);
        doNothing().when(music).pause();
    }

    private void stubGraphics() {
        Hud hud = mock(Hud.class);
        when(level.getHud()).thenReturn(hud);
        when(level.isCompleted()).thenReturn(false);
        doNothing().when(model).changeScreen();
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
    void testChangeScreen() {
        Boot.INSTANCE = mock(Boot.class);
        doNothing().when(Boot.INSTANCE).setScreen(any(Screen.class));
        assertEquals(GameState.STARTUP, model.getState());

        mockScreenChange(StartScreen.class);

        model.setState(GameState.ACTIVE);
        mockScreenChange(GameScreen.class);

        model.setState(GameState.GAME_OVER);
        mockScreenChange(GameOverScreen.class);

        model.setState(GameState.ACTIVE);
        model.setState(GameState.NEXT_LEVEL);
        mockScreenChange(LevelCompletedScreen.class);

    }

    private void mockScreenChange(Class<? extends Screen> type) {
        try (MockedConstruction<? extends Screen> mockedConstruction = mockConstruction(type)) {
            model.changeScreen();
            verify(Boot.INSTANCE, times(1)).setScreen(any(type));
        }
    }

    @Test
    void testCreateLevel() {
        model = spy(new GameModel());
        try (MockedConstruction<Level> mockedConstruction = mockConstruction(Level.class)) {
            model.createLevel();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testCreateCamera() {
        model = spy(new GameModel());
        Boot.INSTANCE = mock(Boot.class);
        when(Boot.INSTANCE.getScreenHeight()).thenReturn(0);
        when(Boot.INSTANCE.getScreenWidth()).thenReturn(0);
        try (MockedConstruction<GameCamera> mockedConstruction = mockConstruction(GameCamera.class)) {
            model.createCamera();
            MockingDetails mockingDetails = new DefaultMockingDetails(model.getCamera());
            assertTrue(mockingDetails.isMock());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testCreateAudioHelper() {
        stubUpdateModel();
        try (MockedConstruction<AudioHelper> mockedConstruction = mockConstruction(AudioHelper.class)) {
            model.update();
            MockingDetails mockingDetails = new DefaultMockingDetails(model.getAudioHelper());
            assertTrue(mockingDetails.isMock());
        } catch (Exception e) {
            fail();
        }
    }

}

