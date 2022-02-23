package objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import core.Controller;
import core.GameScreen;
import helper.BodyHelper;
import helper.Constants;

public class Player extends Object{
    Controller controller;

    public Player(String name, String texturePath, GameScreen gameScreen, float x, float y, int density) {
        super(name, texturePath, gameScreen, x, y, density);
        this.controller= new Controller(this, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN);
    }

    @Override
    public void update() {
        super.update();
        controller.inputListener();
    }
}
