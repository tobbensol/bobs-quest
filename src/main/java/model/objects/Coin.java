package model.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public class Coin extends StaticObject {

    private boolean isDestroyed = false;
    private AssetManager manager;

    public Coin(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Coin.class).size() + 1), level, Constants.TILE_SIZE, Constants.TILE_SIZE, x, y, 0, ContactType.COIN, Constants.COIN_BIT, Constants.INTERACTIVE_MASK_BITS, true, false);
        texture = new Texture("Multi_Platformer_Tileset_v2/WorldObjects/Epic_coin_64.png");
        manager = new AssetManager();
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.finishLoading();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - (float) Constants.TILE_SIZE / 2, y - (float) Constants.TILE_SIZE / 2, width, height);
    }

    public void onHit() {
        level.increaseScore(1);
        BodyHelper.changeFilterData(body, Constants.DESTROYED_BIT);
        isDestroyed = true;
        manager.get("audio/sounds/coin.wav", Sound.class).play();

    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
