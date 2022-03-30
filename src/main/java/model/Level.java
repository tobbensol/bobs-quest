package model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import model.helper.TiledMapHelper;
import model.objects.*;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final String levelName;
    private final GameModel model;
    private final GameObjectFactory factory;
    private World world;
    private Hud hud;
    private TiledMapHelper tiledMapHelper;
    private boolean levelCompleted;
    private List<Player> players;
    private List<Goomba> goombas;
    private List<Coin> coins;
    private List<Goal> goals;
    private List<CameraWall> cameraWalls;
    private Integer score = 0;
    private Vector2 topLeft;
    private Vector2 bottomRight;


    public Level(String levelName, GameModel model) {
        this.levelName = camelToSentence(levelName);
        this.model = model;
        factory = new GameObjectFactory(this);

        createWorld(levelName);
        createObjects();
        createHUD();
        parseMapEndPoints();
        createCameraWalls();
    }

    private void createWorld(String level) {
        this.world = new World(new Vector2(0, -10f), false);
        this.world.setContactListener(new GameContactListener(this));
        this.tiledMapHelper = new TiledMapHelper(this, level);
    }

    private void createObjects() {
        players = new ArrayList<>();
        List<Vector2> spawnPoints = tiledMapHelper.parseMapSpawnPoints("Player");
        for (int i = 0; i < Math.min(model.getNumPlayers(), model.getNumControllers()); i++) { // TODO: Might produce IndexOutOfBoundsException
            players.add(new Player("Player", this, spawnPoints.get(i).x, spawnPoints.get(i).y));
        }
        System.out.println(players);

        goombas = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Goomba")) {
            goombas.add((Goomba) factory.create("Goomba", v.x, v.y));
        }
        System.out.println(goombas);

        coins = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Coin")) {
            coins.add((Coin) factory.create("Coin", v.x, v.y));
        }
        System.out.println(coins);

        goals = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Goal")) {
            goals.add((Goal) factory.create("Goal", v.x, v.y));
        }
        System.out.println(goals);
    }

    private void createCameraWalls() {
        cameraWalls = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            cameraWalls.add((CameraWall) factory.create("CameraWall", 0, 0));
        }
        System.out.println(cameraWalls);
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

    public List<Goomba> getGoombas() {
        return new ArrayList<>(goombas);
    }

    public List<Coin> getCoins() {
        return new ArrayList<>(coins);
    }

    public List<Goal> getGoals() {
        return new ArrayList<>(goals);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
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
