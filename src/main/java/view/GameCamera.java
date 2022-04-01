package view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import model.GameModel;
import model.objects.CameraWall;
import model.objects.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameCamera extends OrthographicCamera {

    private final GameModel gameModel;

    public GameCamera(GameModel gameModel) {
        this.gameModel = gameModel;
        position.set(getAveragePlayerPosition());
    }

    public void update() {
        manageZoom();
        position.set(getNextCameraPosition(getAveragePlayerPosition()));
        super.update();
        moveCameraWalls();
    }

    private void moveCameraWalls() {
        CameraWall wall1 = gameModel.getLevel().getCameraWalls().get(0);
        CameraWall wall2 = gameModel.getLevel().getCameraWalls().get(1);

        wall1.setPosition((position.x - viewportWidth * zoom / 2 - wall1.getWidth() / 2) / 100, position.y / 100);
        wall2.setPosition((position.x + viewportWidth * zoom / 2 + wall2.getWidth() / 2) / 100, position.y / 100);
    }

    private Vector3 getNextCameraPosition(Vector3 position) {
        Vector2 mapTopLeft = gameModel.getLevel().getTopLeft();
        Vector2 mapBottomRight = gameModel.getLevel().getBottomRight();

        //limits the camera position between the rightmost and leftmost point of the map
        position.x = Math.min(Math.max(position.x, mapTopLeft.x + viewportWidth * zoom / 2), mapBottomRight.x - viewportWidth * zoom / 2);
        position.y = Math.min(Math.max(position.y, mapBottomRight.y + viewportHeight * zoom / 2), mapTopLeft.y - viewportHeight * zoom / 2);

        return position;
    }

    private void manageZoom() {
        ArrayList<Float> playerXs = new ArrayList<>();

        for (Player player : gameModel.getLevel().getPlayers()) {
            if (!player.isDead()) {
                playerXs.add(player.getPosition().x);
            }
        }

        if (playerXs.isEmpty() || playerXs.size() == 1) {
            return;
        }

        double paddedXWidth = viewportWidth * zoom * 0.68;

        float minX = Collections.min(playerXs);
        float maxX = Collections.max(playerXs);
        float playersXDifference = maxX - minX;

        float minZoom = 1f;
        float maxZoom = 1.45f;

        if (playersXDifference > paddedXWidth && zoom <= maxZoom) {
            zoom += 0.0016f * playersXDifference / paddedXWidth;
        }
        if (playersXDifference < paddedXWidth && zoom >= minZoom) {
            zoom -= 0.0016f * paddedXWidth / playersXDifference;
        }
    }

    private Vector3 getAveragePlayerPosition() {
        List<Player> players = gameModel.getLevel().getPlayers();

        if (players.size() == 0) {
            return position;
        }

        float maxX = 0;
        float minX = 1000000;
        float maxY = 0;
        float minY = 1000000;

        for (Player player : players) {
            if (!player.isDead()) {
                maxX = Math.max(maxX, player.getPosition().x);
                minX = Math.min(minX, player.getPosition().x);
                maxY = Math.max(maxY, player.getPosition().y);
                minY = Math.min(minY, player.getPosition().y);
            }
        }

        return new Vector3((minX+maxX)/2, (minY+maxY)/2, 0);
    }
}
