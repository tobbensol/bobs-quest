package model.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.helper.ContactType;

public class Goomba extends MoveableObject {

    private TextureRegion tr = new TextureRegion(getTexture(), 6*64, 0, 64, 64); // TODO: Proper texture
    private int numMoves;
    private static final int attack = 40;

    public Goomba(String name, String texturePath, GameModel gameModel, float x, float y, int density, ContactType contactType) {
        super(name, texturePath, gameModel, x, y, density, contactType);
        numMoves = 0;
    }

    @Override
    public void update() {
        super.update();
        if (numMoves == 0) {
            moveHorizontally(1.0f, false);
        }
        if (numMoves >= 120) {
            moveHorizontally(1.0f, true);
            numMoves = -120;
        }
        numMoves++;
    }

    @Override
    public void moveHorizontally(float delta, boolean isRight) {
        if (isRight) {
            body.applyLinearImpulse(new Vector2(delta*0.3f, 0), body.getWorldCenter(), true);
        }
        else {
            body.applyLinearImpulse(new Vector2(delta*-0.3f, 0), body.getWorldCenter(), true);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, x, y, width, height);
    }

    public static int getAttack() {
        return attack;
    }
}
