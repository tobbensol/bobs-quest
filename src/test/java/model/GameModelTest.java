package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameModelTest {

    private GameModel model;
    private Level level;
    private World world;

    @BeforeEach
    void setup() {
        new HeadlessApplication(new Game() {public void create() {}});
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
        assertEquals(1, model.getNumPlayers());
        model.setNumPlayers(3);
        assertEquals(3, model.getNumPlayers());
        assertEquals(3, model.getNumControllers());
    }

    @Test
    void testUpdateModel() {
        when(level.isCompleted()).thenReturn(false);
        doNothing().when(model).changeScreen();

        model.update();
    }
}

