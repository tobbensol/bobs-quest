package objects;

import com.badlogic.gdx.Gdx;
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
import helper.ContactType;

public class Player extends Object {
    Controller controller;
    //private boolean onGround = false;


    public Player(String name, String texturePath, GameScreen gameScreen, float x, float y, int density) {
        super(name, texturePath, gameScreen, x, y, density, ContactType.PLAYER);
        this.controller= new Controller(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN);
    }

    @Override
    public void update() {
        /*
        Vector2 direction = controller.inputListener();
        move(direction);

         */
        handleInput();
        super.update();
    }
    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && grounded) {
            this.body.applyLinearImpulse(new Vector2(0,1.1f), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.body.getLinearVelocity().x <= 2) {
            this.body.applyLinearImpulse(new Vector2(0.35f,0), this.body.getWorldCenter(), true);
            facingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.body.getLinearVelocity().x >= -2) {
            this.body.applyLinearImpulse(new Vector2(-0.35f,0), this.body.getWorldCenter(), true);
            facingRight = false;
        }

    }

    public boolean setGrounded(boolean value) {
        grounded = value;
        return grounded;
    }
}
