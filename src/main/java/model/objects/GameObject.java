package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public abstract class GameObject implements IGameObject {
    final private String name;
    protected Body body;
    protected float x, y, width, height;
    protected Level level;
    protected boolean facingRight, isDestroyed;
    protected short bit, maskBits;
    Texture texture;
    String texturePath;

    public GameObject(String name, Level level, float width, float height, float x, float y, float density, ContactType contactType, BodyDef.BodyType bodyType, short categoryBits, short maskBits, boolean isSensor, boolean rectangle) {
        this.name = name;
        this.level = level;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.body = BodyHelper.createObjectBody(x, y, width, height, density, level.getWorld(), contactType, bodyType, categoryBits, maskBits, isSensor, rectangle);
        facingRight = true;
        this.bit = categoryBits;
        this.maskBits = maskBits;
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public void setPosition(float x, float y) {
        body.setTransform(x / Constants.PPM, y / Constants.PPM, body.getAngle());
    }

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

    @Override
    public void changeMaskBit(boolean filterAway, short filterBit){
        maskBits = BodyHelper.changeMaskBit(filterAway, filterBit, maskBits);
        BodyHelper.changeFilterData(body, bit, maskBits);
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }
}
