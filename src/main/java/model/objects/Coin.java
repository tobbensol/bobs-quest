package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.GameModel;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public class Coin extends StaticObject {

    private boolean isDestroyed = false;

    public Coin(String name, GameModel gameModel, float x, float y, float density) {
        super(name, gameModel, x, y, density, ContactType.COIN, Constants.COIN_BIT, Constants.COIN_MASK_BITS, true);
        texture = new Texture("Multi_Platformer_Tileset_v2/WorldObjects/Coin.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x-32, y-32, width, height);
    }

    public void onHit() { // TODO: Still registers contact
        BodyHelper.setCategoryFilter(body, Constants.DESTROYED_BIT);
        isDestroyed = true;
//        removeCell();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
