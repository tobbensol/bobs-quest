package view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import model.GameModel;
import model.helper.Constants;
import model.objects.MapEndPoints;
import model.objects.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameCamera extends OrthographicCamera {

    private static final float minZoom = 1f;
    private static final float maxZoom = 1.45f;
    private static final float zoomTriggerPercent = 0.68f;
    private static final float zoomIncreaseAmount = 0.0016f;
    private static final float zoomDecreaseAmount = 0.0016f;
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

    /**
     * Sets the camera walls' positions to the left side and the right side of the camera (x) and to the middle of the screen (y).
     */
    private void moveCameraWalls() {
        MapEndPoints wall1 = gameModel.getLevel().getGameObjects(MapEndPoints.class).get(0);
        MapEndPoints wall2 = gameModel.getLevel().getGameObjects(MapEndPoints.class).get(1);

        wall1.setPosition(position.x - viewportWidth * zoom / 2 - wall1.getWidth() / 2, position.y);
        wall2.setPosition(position.x + viewportWidth * zoom / 2 + wall2.getWidth() / 2, position.y);
    }

    /**
     * Limits the inputted camera position between the rightmost and leftmost possible camera positions of the map.
     *
     * @param position Inputted camera position
     * @return The next possible camera position
     */
    private Vector3 getNextCameraPosition(Vector3 position) {
        Vector2 mapTopLeft = gameModel.getLevel().getTopLeft();
        Vector2 mapBottomRight = gameModel.getLevel().getBottomRight();

        position.x = Math.min(Math.max(position.x, mapTopLeft.x + viewportWidth * zoom / 2), mapBottomRight.x - viewportWidth * zoom / 2);
        position.y = Math.min(Math.max(position.y, mapBottomRight.y + viewportHeight * zoom / 2), mapTopLeft.y - viewportHeight * zoom / 2);

        return position;
    }


    /**
     * If the distance between the two outermost players is greater or less than a certain percent of the screen scaled with the current zoom, increase or decrease the zoom level.
     * The zoom must be between the minZoom and the maxZoom.
     * The further the players are from each other, the faster the zoom increases.
     * The closer the players are from each other, the faster the zoom decreases.
     */
    private void manageZoom() {
        ArrayList<Float> playerXs = new ArrayList<>();

        for (Player player : gameModel.getLevel().getGameObjects(Player.class)) {
            if (!player.isDead()) {
                playerXs.add(player.getPosition().x);
            }
        }

        if (playerXs.isEmpty() || playerXs.size() == 1) {
            return;
        }

        double zoomTriggerWidth = viewportWidth * zoom * zoomTriggerPercent;

        float minX = Collections.min(playerXs);
        float maxX = Collections.max(playerXs);
        float playersXDifferenceWidth = maxX - minX;

        if (playersXDifferenceWidth > zoomTriggerWidth && zoom <= maxZoom) {
            zoom += zoomIncreaseAmount * playersXDifferenceWidth / zoomTriggerWidth;
        }
        if (playersXDifferenceWidth < zoomTriggerWidth && zoom >= minZoom) {
            zoom -= zoomDecreaseAmount * zoomTriggerWidth / playersXDifferenceWidth;
        }
    }

    /**
     * Retrieves the min position and the max position of the alive players and takes the average between them.
     * If no players are alive, then return the last position.
     *
     * @return The average player position
     */
    private Vector3 getAveragePlayerPosition() {
        List<Player> players = gameModel.getLevel().getGameObjects(Player.class);
        int alivePlayerCount = 0;

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
                alivePlayerCount++;
            }
        }

        if (alivePlayerCount == 0) {
            return position;
        }

        return new Vector3((minX + maxX) / 2, (minY + maxY) / 2, 0);
    }
}
