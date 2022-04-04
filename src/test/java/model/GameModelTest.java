package model;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GameModelTest {

    static GameModel model;

    @BeforeAll
    static void setup() {
        model = new GameModel(true);
    }

    @Test
    void modelTest() {
//        assertEquals(new Vector2(0, 0), model.getWorld().getGravity());
        fail();
    }
}
