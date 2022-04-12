package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import model.GameModel;
import model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GameControllerTest {

    private GameController controller;
    private GameModel model;

    @BeforeEach
    void setup() {
        model = mock(GameModel.class);
        controller = new GameController(model);
        Gdx.input = spy(new MockInput());
    }

    @Test
    void testStartup() {
        when(model.getState()).thenReturn(GameState.STARTUP);

        int[] keyCodes = new int[] {Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.SPACE};
        for (int key : keyCodes) {
            when(Gdx.input.isKeyPressed(key)).thenReturn(true);
        }

        verifyNoInteractions(model);
        verifyNoInteractions(Gdx.input);

        controller.inputListener();

        verify(Gdx.input, atLeast(4)).isKeyPressed(anyInt());
        verify(model, times(1)).setNumPlayers(1);
        verify(model, times(1)).setNumPlayers(2);
        verify(model, times(1)).setNumPlayers(3);
        verify(model, times(1)).setState(GameState.ACTIVE);
        verify(model, times(1)).changeScreen();
        verify(model, times(1)).resumeGame();
    }

    @Test
    void testExit() {
        when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)).thenReturn(true);
        Gdx.app = mock(HeadlessApplication.class);

        verifyNoInteractions(model);
        verifyNoInteractions(Gdx.app);

        controller.inputListener();

        verify(Gdx.app, times(1)).exit();
    }

    @Test
    void testActiveGame() {
        when(model.getState()).thenReturn(GameState.ACTIVE);

        verifyNoInteractions(model);
        verifyNoInteractions(Gdx.input);

        when(Gdx.input.isKeyPressed(Input.Keys.P)).thenReturn(true);
        when(Gdx.input.isKeyPressed(Input.Keys.R)).thenReturn(true);
        when(model.isPaused()).thenReturn(true);
        controller.inputListener();

        verify(Gdx.input, times(1)).isKeyPressed(Input.Keys.P);
        verify(model, times(2)).getState();
        verify(model, times(1)).isPaused();
        verify(model, times(1)).resumeGame();
        verify(Gdx.input, times(1)).isKeyPressed(Input.Keys.R);
        verify(model, times(1)).restart();

        when(Gdx.input.isKeyPressed(Input.Keys.P)).thenReturn(false);
        controller.inputListener();

        when(Gdx.input.isKeyPressed(Input.Keys.P)).thenReturn(true);
        when(model.isPaused()).thenReturn(false);
        controller.inputListener();

        verify(Gdx.input, times(3)).isKeyPressed(Input.Keys.P);
        verify(model, times(5)).getState();
        verify(model, times(2)).isPaused();
        verify(model, times(1)).resumeGame();
        verify(model, times(1)).pauseGame();
    }

    @Test
    void testToActiveNotStartup() {
        when(model.getState()).thenReturn(GameState.GAME_OVER);

        verifyNoInteractions(model);
        verifyNoInteractions(Gdx.input);

        when(Gdx.input.isKeyPressed(Input.Keys.SPACE)).thenReturn(true);
        controller.inputListener();

        verify(model, times(1)).setState(GameState.ACTIVE);
        verify(model, times(1)).changeScreen();
        verify(model, times(1)).resumeGame();

        when(model.getState()).thenReturn(GameState.GAME_OVER);
        controller.inputListener();

        verify(model, times(2)).setState(GameState.ACTIVE);
        verify(model, times(2)).changeScreen();
        verify(model, times(2)).resumeGame();
    }

}
