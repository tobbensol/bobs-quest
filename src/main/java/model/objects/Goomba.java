package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.GameModel;
import model.helper.Constants;
import model.helper.ContactType;

public class Goomba extends MoveableObject {

    private TextureRegion textureRegion;
    private int numMoves;
    private static final int attack = 40;

    public Goomba(String name, GameModel gameModel, float x, float y, int density) {
        super(name, gameModel, x, y, density, ContactType.ENEMY, Constants.ENEMY_BIT, Constants.ENEMY_MASK_BITS);
        texturePath = "Multi_Platformer_Tileset_v2/Enemies/Goomba.png";
        texture = new Texture(texturePath);
        textureRegion = new TextureRegion(getTexture(), 64, 0, 64, 64); // TODO: Proper texture

        numMoves = 0;
    }

    @Override
    public void update() {
        super.update();
        if (numMoves == 0) {
            moveHorizontally(1.0f, false);
            textureRegion.flip(true, false);
        }
        if (numMoves >= 120) {
            moveHorizontally(1.0f, true);
            numMoves = -120;
            textureRegion.flip(true, false);
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
        batch.draw(textureRegion, x, y, width, height);
    }

    public static int getAttack() {
        return attack;
    }
}
