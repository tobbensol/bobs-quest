package objects;

import com.badlogic.gdx.Input;
import controls.ArrowController;
import controls.Controller;
import core.GameScreen;


public class Player extends Object{
    Controller controller;

    public Player(String name, String texturePath, GameScreen gameScreen, float x, float y, int density, Controller controller) {
        super(name, texturePath, gameScreen, x, y, density);
        this.controller= controller;
    }

    @Override
    public void update() {
        super.update();
        move(controller.inputListener());
    }
}
