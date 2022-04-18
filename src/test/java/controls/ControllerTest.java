package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import model.objects.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ControllerTest {

    private Controller controller;
    private Player player;

    @BeforeEach
    void setup() {
        controller = new ArrowController();
        player = mock(Player.class);
        Gdx.input = spy(new MockInput());
    }

    @Test
    void testInputListener() {
        int[] keyCodes = new int[] {Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN};
        for (int key : keyCodes) {
            when(Gdx.input.isKeyPressed(key)).thenReturn(true);
        }

        verifyNoInteractions(player);
        verifyNoInteractions(Gdx.input);
        controller.inputListener(player);

        verify(player, times(1)).moveHorizontally(true);
        verify(player, times(1)).moveHorizontally(false);
        verify(player, times(1)).jump();
        verify(player, times(1)).drop();

        when(player.isDead()).thenReturn(true);
        controller.inputListener(player);

        verify(player, times(1)).moveHorizontally(true);

    }

}
