package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.GameModel;
import model.helper.ContactType;

public class newCoin extends StaticObject{

    public newCoin(String name, GameModel gameModel, float x, float y, float density, ContactType contactType) {
        super(name, gameModel, x, y, density, contactType);
        texture = new Texture("Multi_Platformer_Tileset_v2/WorldObjects/Coin.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x-32, y-32, width, height);
    }
}
