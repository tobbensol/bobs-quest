package view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import model.GameModel;
import model.objects.Player;

public class GameCamera extends OrthographicCamera {

    GameModel gameModel;

    public GameCamera (GameModel gameModel){
        this.gameModel = gameModel;
    }


    public void update (){
        float averageX = 0;
        float averageY = 0;
        for (Player player : gameModel.getPlayers()) {
            averageX += player.getPosition().x;
            averageY += player.getPosition().y;
        }
        averageX = averageX/gameModel.getPlayers().size();
        averageY = averageY/gameModel.getPlayers().size();

        position.set(new Vector3(averageX, averageY,0));
        super.update();
    }


}
