package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.GameScreen;
import helper.BodyHelper;
import helper.Constants;
import helper.ContactType;

public abstract class Object {
    final private String name;
    final private Texture texture;
    private int width, height;
    protected Body body;
    protected float x, y, speed, velY;
    private GameScreen gameScreen;


//    private boolean facingRight;
//    private boolean grounded;

//    enum State {
//        IDLE,
//        WALKING,
//        JUMPING
//    }

    public Object(String name, String texturePath, GameScreen gameScreen, float x, float y, int density, ContactType contactType) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.gameScreen = gameScreen;

        this.body = BodyHelper.BodyHelper(x, y, width, height, density, gameScreen.getWorld(), contactType);
//        facingRight = true;
//        grounded = true;
    }

    public void update() {
        x = body.getPosition().x * Constants.PPM - (width / 2);
        y = body.getPosition().y * Constants.PPM - (height / 2);

        velY = body.getLinearVelocity().len();
        //velY = 0;
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture,x,y,width,height);
    }

    public String getName() {
        return name;
    }


    public Vector2 getPosition() {
        return body.getPosition().scl(Constants.PPM);
    }


    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setAnimation(int animation) {
        // TODO: Implement
    }

    public void move(Vector2 vector2){
        body.applyForceToCenter(vector2, true);
    }
}
