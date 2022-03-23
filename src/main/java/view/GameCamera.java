package view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import model.GameModel;
import model.objects.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GameCamera extends OrthographicCamera {

    GameModel gameModel;

    public GameCamera (GameModel gameModel){
        this.gameModel = gameModel;
    }

    public void update() {

        manageZoom();
        position.set( getAveragePlayerPosition() );
        super.update();
    }

    private void manageZoom (){
        ArrayList<Float> playerXs = new ArrayList<>();

        for (Player player : gameModel.getPlayers()) {
            if ( !player.isDead() ) {
                playerXs.add( player.getPosition().x );
            }
        }

        double paddedXWidth = viewportWidth * zoom * 0.68;

        float minX = Collections.min( playerXs );
        float maxX = Collections.max( playerXs );
        float playersXDifference = maxX - minX;

//        System.out.println("ScreenX: " + paddedXWidth + ", Player Difference: " + playersXDifference );

        float minZoom = 1f;
        float maxZoom = 1.45f;

        if ( playersXDifference > paddedXWidth && zoom <= maxZoom ) {
            zoom += 0.0016f;
        }
        if ( playersXDifference < paddedXWidth && zoom >= minZoom ) {
            zoom -= 0.0016f;
        }
    }

    private Vector3 getAveragePlayerPosition() {
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

        averageX = averageX/playerCount;
        averageY = averageY/playerCount;

        return new Vector3(averageX, averageY,0);
    }

    public float getZoom() {
        return zoom;
    }

    public double getScreenWidth() {
        return viewportWidth;
    }

    public double getScreenHeight() {
        return viewportHeight;
    }

}
