package view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import model.GameModel;

public class GameCamera extends OrthographicCamera {

    GameModel gameModel;

    public GameCamera (GameModel gameModel){
        this.gameModel = gameModel;
    }


    public void update (){
        position.set(new Vector3(gameModel.getPlayers().get(0).getPosition().x,gameModel.getPlayers().get(0).getPosition().y,0));
        super.update();
    }


}
