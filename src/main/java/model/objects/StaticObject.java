package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import model.GameModel;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class StaticObject {
    final private String name;
    final private Texture texture;
    protected int width, height;
    protected Body body;
    protected float x, y, velY;
    protected GameModel gameModel;

    protected boolean facingRight;

    public StaticObject(String name, String texturePath, GameModel gameModel, float x, float y, int density, ContactType contactType) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.gameModel = gameModel;
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;

        this.body = BodyHelper.BodyHelper(x, y, width, height, density, gameModel.getWorld(), contactType);
        facingRight = true;
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public String getName() {
        return name;
    }

    public Vector2 getPosition() {
        return body.getPosition().scl(Constants.PPM);
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

}
