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
        int playerCount = 0;

        for (Player player : gameModel.getPlayers()) {
            if (!player.isDead()) {
                averageX += player.getPosition().x;
                averageY += player.getPosition().y;
                playerCount += 1;
            }
        }
        System.out.println("x: " + averageX + " " + "y: " + averageY);

        averageX = averageX/playerCount;
        averageY = averageY/playerCount;

        position.set(new Vector3(averageX, averageY,0));
        super.update();
    }


}
