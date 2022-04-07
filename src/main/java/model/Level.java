package model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.helper.TiledMapHelper;
import model.objects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Level {

    private final String levelName;
    private final GameModel model;
    private final GameObjectFactory factory;
    private World world;
    private static final float gravity = -26f;
    private Hud hud;
    private TiledMapHelper tiledMapHelper;
    private boolean levelCompleted;
    private HashMap<String, ArrayList<GameObject>> objectMap;
    private Integer score = 0;
    private Vector2 topLeft;
    private Vector2 bottomRight;


    public Level(String levelName, GameModel model) {
        this.levelName = camelToSentence(levelName);
        this.model = model;
        factory = new GameObjectFactory(this);

        objectMap = new HashMap<>();

        objectMap.put("Player", new ArrayList<>());
        objectMap.put("Goomba", new ArrayList<>());
        objectMap.put("Coin", new ArrayList<>());
        objectMap.put("Goal", new ArrayList<>());
        objectMap.put("Floater", new ArrayList<>());
        objectMap.put("MapEndPoints", new ArrayList<>());

        createWorld(levelName);
        createObjects();
        createHUD();
        parseMapEndPoints();
    }

    private void createWorld(String level) {
        this.world = new World(new Vector2(0, gravity), false);
        this.world.setContactListener(new GameContactListener(this));
        this.tiledMapHelper = new TiledMapHelper(this, level);
    }

    private void createObjects() {
        for(String object : objectMap.keySet()){
            if(object.equalsIgnoreCase("Player")){
                List<Vector2> spawnPoints = tiledMapHelper.parseMapSpawnPoints(object);
                for (int i = 0; i < Math.min(model.getNumPlayers(), model.getNumControllers()); i++) { // TODO: Might produce IndexOutOfBoundsException
                    objectMap.get(object).add(factory.create(object, spawnPoints.get(i).x, spawnPoints.get(i).y));
                }
            }
            else {
                for (Vector2 v : tiledMapHelper.parseMapSpawnPoints(object)) {
                    objectMap.get(object).add(factory.create(object, v.x, v.y));
                }
            }
        }
        objectMap.put("Enemy", objectMap.get("Goomba"));

    }

    private void parseMapEndPoints() {
        List<Vector2> mapEndPoints = tiledMapHelper.parseMapSpawnPoints("MapEndPoints");

        if (mapEndPoints.get(0).x < mapEndPoints.get(1).x ) {
            topLeft = mapEndPoints.get(0);
            bottomRight = mapEndPoints.get(1);
        }
        else {
            topLeft = mapEndPoints.get(1);
            bottomRight = mapEndPoints.get(0);
        }
    }

    private void createHUD() {
        hud = new Hud(new SpriteBatch(), this);
    }

    public void updateHUD() {
        hud.update();
    }

    public Hud getHud() {
        return hud;
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> objectList = new ArrayList<>();
        for (String object : objectMap.keySet()) {
            if (object.equals("Enemy")) { // We do not want to add enemy objects twice.
                continue;
            }
            objectList.addAll(objectMap.get(object));
        }
        return objectList;
    }

    // Source: https://stackoverflow.com/a/19254882
    public <T extends IGameObject> List<T> getGameObjects(Class<T> type) {
        return (List<T>) objectMap.get(getClassName(type));
    }

    <T extends IGameObject> String getClassName(Class<T> type) {
        return type.toString().substring(type.toString().lastIndexOf('.') + 1);
    }

    public World getWorld() {
        return world;
    }

    public Integer getScore() {
        return score;
    }

    public Vector2 getTopLeft() {
        return topLeft;
    }

    public Vector2 getBottomRight() {
        return bottomRight;
    }

    public void setLevelCompleted(boolean value) {
        levelCompleted = value;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return tiledMapHelper.setupMap();
    }

    public boolean isCompleted() {
        return levelCompleted;
    }

    public void increaseScore(int value) {
        score += value;
    }

    //from https://dirask.com/posts/Java-convert-camelCase-to-Sentence-Case-jE6PZ1
    private String camelToSentence(String text) {
        if (!text.equals("")) {
            String result = text.replaceAll("([A-Z, 0-9])", " $1");
            return result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
        }
        return null;
    }

    @Override
    public String toString() {
        return levelName;
    }
}
