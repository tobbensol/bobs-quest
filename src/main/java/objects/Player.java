package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import core.GameScreen;
import helper.BodyHelper;
import helper.Constants;

public class Player extends Object{


    public Player(String name, String texturePath, GameScreen gameScreen, float x, float y, int density) {
        super(name, texturePath, gameScreen, x, y, density);
    }

    @Override
    public void update() {
        super.update();
        // TODO: implement controls?????
    }
}
