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
    protected int width, height;
    protected Body body;
    protected float x, y;
    protected GameModel gameModel;
    String texturePath;

    protected boolean facingRight;

    public GameObject(String name, GameModel gameModel, float x, float y, float density, ContactType contactType, BodyDef.BodyType bodyType, short categoryBits, short maskBits, boolean isSensor) {
        this.name = name;
        this.gameModel = gameModel;
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;

        this.body = BodyHelper.BodyHelper(x, y, width, height, density, gameModel.getWorld(), contactType, bodyType, categoryBits, maskBits, isSensor);
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

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }
}
