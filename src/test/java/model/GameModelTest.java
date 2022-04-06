package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class GameModelTest {

    private static GameModel model;

    @BeforeAll
    static void setup() {
        model = mock(GameModel.class);
    }

    @Test
    void modelTest() {
        assertEquals(model.getNumPlayers(), 1);
        model.setNumPlayers(3);
        assertEquals(model.getNumPlayers(), 3);
        assertEquals(model.getNumControllers(), 3);
    }
}

