package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.GameModel;
import model.helper.Constants;
import model.helper.ContactType;

public class Goal extends StaticObject{
    public Goal(String name, GameModel gameModel, float x, float y) {
        super(name + " " + (gameModel.getGoals().size()+1), gameModel, x, y, 0, ContactType.GOAL, Constants.COIN_BIT, Constants.INTERACTIVE_MASK_BITS, true, true);
        //TODO give goal a proper texture
        texture = new Texture("Multi_Platformer_Tileset_v2/WorldObjects/Goal.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x - (float) Constants.TILE_SIZE/2, y - (float) Constants.TILE_SIZE/2, width, height);
    }

    public void onHit() {
        gameModel.setFinished(true);
    }
}
