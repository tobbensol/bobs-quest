package view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import model.GameModel;
import model.objects.Player;

import java.util.ArrayList;
import java.util.Collections;

public class GameCamera extends OrthographicCamera {

    GameModel gameModel;

    public GameCamera(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void update() {

        manageZoom();
        position.set(getAveragePlayerPosition());
        super.update();
    }

    private boolean checkXOutOfBounds() {
        float mapLeftX = gameModel.getLevel().getTopLeft().x;
        float mapRightX = gameModel.getLevel().getBottomRight().x;
        Vector3 nextPosition = getAveragePlayerPosition();
        float screenLeftX = nextPosition.x - viewportWidth * zoom / 2;
        float screenRightX = nextPosition.x + viewportWidth * zoom / 2;

        return screenLeftX < mapLeftX || screenRightX  > mapRightX;
    }

private boolean checkYOutOfBounds() {
        float mapTopY = gameModel.getLevel().getTopLeft().y;
        float mapBottomY = gameModel.getLevel().getBottomRight().y;
        Vector3 nextPosition = getAveragePlayerPosition();
        float screenTopY = nextPosition.y + viewportHeight * zoom / 2;
        float screenBottomY = nextPosition.y - viewportHeight * zoom / 2;

        return screenTopY > mapTopY || screenBottomY < mapBottomY;
}

    private void manageZoom() {
        ArrayList<Float> playerXs = new ArrayList<>();

        for (Player player : gameModel.getLevel().getPlayers()) {
            if (!player.isDead()) {
                playerXs.add(player.getPosition().x);
            }
        }

        if (playerXs.isEmpty()) {
            return;
        }

        double paddedXWidth = viewportWidth * zoom * 0.68;

        float minX = Collections.min(playerXs);
        float maxX = Collections.max(playerXs);
        float playersXDifference = maxX - minX;

        float minZoom = 1f;
        float maxZoom = 1.45f;

        if (playersXDifference > paddedXWidth && zoom <= maxZoom) {
            zoom += 0.0016f;
        }
        if (playersXDifference < paddedXWidth && zoom >= minZoom) {
            zoom -= 0.0016f;
        }
    }

    private Vector3 getAveragePlayerPosition() {
        float averageX = 0;
        float averageY = 0;
        int playerCount = 0;

        for (Player player : gameModel.getLevel().getPlayers()) {
            if (!player.isDead()) {
                averageX += player.getPosition().x;
                averageY += player.getPosition().y;
                playerCount += 1;
            }
        }

        averageX = averageX / playerCount;
        averageY = averageY / playerCount;

        return new Vector3(averageX, averageY, 0);
    }
}
