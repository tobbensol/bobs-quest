package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.mock.audio.MockMusic;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import controls.GameController;
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

        AudioHelper audioHelper = mock(AudioHelper.class);
        Sound sound = mock(Sound.class);
        world = new World(new Vector2(0, 0), false);
        music = mock(Music.class);
        level = mock(Level.class);
        when(level.getWorld()).thenReturn(world);
        when(level.getAudioHelper()).thenReturn(audioHelper);
        when(audioHelper.getSoundEffect(anyString())).thenReturn(sound);
        when(audioHelper.getLevelMusic(anyString())).thenReturn(music);
        when(audioHelper.getMusic(anyString())).thenReturn(music);

        model = spy(new GameModel());
        doReturn(level).when(model).createLevel();

        when(model.getLevel()).thenReturn(level);
        doNothing().when(model).createCamera();
        doNothing().when(model).resetZoom();
        doNothing().when(model).setMusic(music);
        when(model.getMusic()).thenReturn(music);
        when(model.getAudioHelper()).thenReturn(audioHelper);
    }

    @Test
    void testWhenGameOver() throws InterruptedException {
        stubUpdateModel();

        model.update();
        model.resumeGame();

        assertFalse(model.isPaused());
        assertFalse(player.isDestroyed());
        assertEquals(GameState.MAIN_MENU, model.getCurrentState());

        model.setCurrentState(GameState.ACTIVE);
        model.update();

        verify(model, never()).changeScreen();
        assertFalse(model.isPaused());
        assertEquals(GameState.ACTIVE, model.getCurrentState());

        player.setDead();
        Thread.sleep(2000);
        assertTrue(player.isDestroyed());

        model.update();

        verify(model, times(1)).changeScreen();
        assertEquals(GameState.GAME_OVER, model.getCurrentState());
    }

    @Test
    void testWhenLevelCompleted() {
        stubUpdateModel();

        model.update();
        model.resumeGame();

        assertFalse(model.isPaused());
        assertEquals(GameState.MAIN_MENU, model.getCurrentState());

        model.setCurrentState(GameState.ACTIVE);
        model.update();

        verify(model, never()).changeScreen();
        assertFalse(model.isPaused());
        assertEquals(GameState.ACTIVE, model.getCurrentState());

        when(level.isCompleted()).thenReturn(true);
        model.update();

        verify(model, times(1)).changeScreen();
        assertEquals(GameState.NEXT_LEVEL, model.getCurrentState());
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

        doNothing().when(music).setLooping(true);

        Boot.INSTANCE = mock(Boot.class);
        GameController controller = mock(GameController.class);
        when(Boot.INSTANCE.getGameController()).thenReturn(controller);
        doNothing().when(controller).inputListener();
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
        model.restart();
        assertEquals(3, model.getNumPlayers());
        assertEquals(3, model.getNumControllers());
    }

    @Test
    void testRestart() {
        Music music = new MockMusic();
        when(level.getLevelMusic()).thenReturn(music);
        stubUpdateModel();
        model.pauseGame();
        model.update();
        model.resumeGame();

        // No access to model.levels or model.levelNR
        assertFalse(model.getLevel().isCompleted());
        assertFalse(model.isPaused());

        assertFalse(model.restart());

        verify(model, times(2)).createLevel();
        assertTrue(model.isPaused());

        model.resumeGame();
        assertFalse(model.isPaused());
        when(level.isCompleted()).thenReturn(true);

        assertTrue(model.restart());

        verify(model, times(3)).createLevel();
        assertTrue(model.isPaused());
    }

    @Test
    void testSetStateThrows() {
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(null));

        assertEquals(GameState.MAIN_MENU, model.getCurrentState());
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEXT_LEVEL));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.GAME_OVER));

        model.setCurrentState(GameState.NEW_GAME);
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEXT_LEVEL));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.GAME_OVER));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.SETTINGS));

        model.setCurrentState(GameState.SELECT_LEVEL);
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEXT_LEVEL));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.GAME_OVER));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.SETTINGS));

        model.setCurrentState(GameState.MAIN_MENU);
        model.setCurrentState(GameState.SETTINGS);
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEXT_LEVEL));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.GAME_OVER));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.SELECT_LEVEL));

        model.setCurrentState(GameState.ACTIVE);
        model.setCurrentState(GameState.NEXT_LEVEL);
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.GAME_OVER));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEW_GAME));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.SELECT_LEVEL));

        model.setCurrentState(GameState.ACTIVE);
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEW_GAME));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.SETTINGS));

        model.setCurrentState(GameState.GAME_OVER);
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.NEXT_LEVEL));
        assertThrows(IllegalArgumentException.class, () -> model.setCurrentState(GameState.SETTINGS));
    }

    @Test
    void testChangeScreen() {
        Boot.INSTANCE = mock(Boot.class);
        doNothing().when(Boot.INSTANCE).setScreen(any(Screen.class));

        assertEquals(GameState.MAIN_MENU, model.getCurrentState());
        mockScreenChange(MainMenuScreen.class);

        model.setCurrentState(GameState.NEW_GAME);
        mockScreenChange(NewGameScreen.class);

        model.setCurrentState(GameState.SELECT_LEVEL);
        mockScreenChange(SelectLevelScreen.class);

        model.setCurrentState(GameState.MAIN_MENU);
        model.setCurrentState(GameState.SETTINGS);
        mockScreenChange(SettingsScreen.class);

        model.setCurrentState(GameState.ACTIVE);
        mockScreenChange(GameScreen.class);

        model.setCurrentState(GameState.NEXT_LEVEL);
        mockScreenChange(LevelCompletedScreen.class);

        model.setCurrentState(GameState.ACTIVE);
        model.setCurrentState(GameState.GAME_OVER);
        mockScreenChange(GameOverScreen.class);
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
            e.printStackTrace();
            fail();
        }
        model.setMusicVolume(0);
        assertEquals(0, model.getMusicVolume());
        model.setSoundEffectsVolume(0);
        assertEquals(0, model.getSoundEffectsVolume());
    }

    @Test
    void testContinueGame() {
        stubUpdateModel();
        model.setCurrentState(GameState.MAIN_MENU);
        assertEquals(GameState.MAIN_MENU, model.getCurrentState());
        model.continueGame();
        assertEquals(GameState.ACTIVE, model.getCurrentState());
    }

    @Test
    void testGoToScreen() {
        stubUpdateModel();
        model.goToScreen(GameState.MAIN_MENU);
        assertEquals(GameState.MAIN_MENU, model.getCurrentState());
        assertTrue(model.isPaused());
        model.goToScreen(GameState.ACTIVE);
        assertEquals(GameState.ACTIVE, model.getCurrentState());
        assertFalse(model.isPaused());
    }

    @Test
    void testStartNewGame() {
        stubUpdateModel();
        assertThrows(IllegalArgumentException.class, () -> model.startNewGame(0));
        assertThrows(IllegalArgumentException.class, () -> model.startNewGame(4));
        model.startNewGame(3);
        assertEquals(3, model.getNumPlayers());
        assertEquals(1, model.getAvailableLevels().size());
    }
}

