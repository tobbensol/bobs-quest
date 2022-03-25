package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import model.GameModel;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class GameObject {
    final private String name;
    Texture texture;
    protected Body body;
    protected float x, y, width, height;
    protected GameModel gameModel;
    String texturePath;

    protected boolean facingRight;

    public GameObject(String name, GameModel gameModel, float width, float height, float x, float y, float density, ContactType contactType, BodyDef.BodyType bodyType, short categoryBits, short maskBits, boolean isSensor, boolean rectangle) {
        this.name = name;
        this.gameModel = gameModel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.body = BodyHelper.createObjectBody(x, y, width, height, density, gameModel.getWorld(), contactType, bodyType, categoryBits, maskBits, isSensor, rectangle);
        facingRight = true;
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    @Override
    public String toString() {
        return name;
    }

    public Vector2 getPosition() {
        return body.getPosition().scl(Constants.PPM);
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }
}
