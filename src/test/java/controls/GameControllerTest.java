package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import model.GameModel;
import model.GameState;
import model.Hud;
import model.Level;
import model.helper.AudioHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameControllerTest {

    private GameController controller;
    private GameModel model;

    @BeforeEach
    void setup() {
        model = spy(new GameModel());
        Level level = mock(Level.class);
        Hud hud = mock(Hud.class);
        AudioHelper audioHelper = mock(AudioHelper.class);
        Music music = mock(Music.class);
        doReturn(level).when(model).getLevel();
        doNothing().when(model).changeScreen();
        doReturn(false).when(model).restart();
        doReturn(hud).when(level).getHud();
        doReturn(music).when(model).getMusic();
        doReturn(audioHelper).when(model).getAudioHelper();


        controller = new GameController(model);
        Gdx.input = spy(new MockInput());
    }

    @Test
    void testStartup() {
        when(Gdx.input.isKeyPressed(Input.Keys.SPACE)).thenReturn(true);

        verifyNoInteractions(model);
        verifyNoInteractions(Gdx.input);

        assertEquals(1, model.getNumPlayers());
        assertEquals(GameState.MAIN_MENU, model.getCurrentState());
        assertFalse(model.isPaused());

        controller.inputListener();

        verify(model, times(1)).setCurrentState(GameState.ACTIVE);
        verify(model, times(1)).changeScreen();
        verify(model, times(1)).resumeGame();

        assertEquals(1, model.getNumPlayers());
        assertEquals(GameState.ACTIVE, model.getCurrentState());
        assertFalse(model.isPaused());
    }

    @Test
    void testActiveGame() {
        verifyNoInteractions(Gdx.input);

        when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.R)).thenReturn(true);
        model.setCurrentState(GameState.ACTIVE);
        model.pauseGame();
        controller.inputListener();

        verify(Gdx.input, times(1)).isKeyPressed(Input.Keys.ESCAPE);
        verify(model, times(1)).resumeGame();
        verify(Gdx.input, times(1)).isKeyPressed(Input.Keys.R);
        verify(model, times(1)).restart();
        assertFalse(model.isPaused());

        when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)).thenReturn(false);
        controller.inputListener();

        when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)).thenReturn(true);
        model.resumeGame();
        controller.inputListener();

        verify(Gdx.input, times(3)).isKeyPressed(Input.Keys.ESCAPE);
        verify(model, times(2)).resumeGame();
        verify(model, times(2)).pauseGame();
        assertTrue(model.isPaused());
    }

    @Test
    void testToActiveNotStartup() {
        model.setCurrentState(GameState.ACTIVE);
        model.setCurrentState(GameState.GAME_OVER);

        verifyNoInteractions(Gdx.input);

        when(Gdx.input.isKeyPressed(Input.Keys.SPACE)).thenReturn(true);

        controller.inputListener();

        verify(model, times(2)).setCurrentState(GameState.ACTIVE);
        verify(model, times(1)).changeScreen();
        verify(model, times(1)).resumeGame();
        assertEquals(GameState.ACTIVE, model.getCurrentState());

        model.setCurrentState(GameState.GAME_OVER);
        controller.inputListener();

        verify(model, times(3)).setCurrentState(GameState.ACTIVE);
        verify(model, times(2)).changeScreen();
        verify(model, times(2)).resumeGame();
        assertEquals(GameState.ACTIVE, model.getCurrentState());
    }

    @Test
    void testGoingToMenu(){
        model.setCurrentState(GameState.ACTIVE);
        model.pauseGame();

        verifyNoInteractions(Gdx.input);

        when(Gdx.input.isKeyPressed(Input.Keys.M)).thenReturn(true);
        controller.inputListener();

        assertEquals(GameState.MAIN_MENU, model.getCurrentState());
    }
}
