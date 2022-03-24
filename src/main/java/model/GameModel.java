package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import controls.*;
import launcher.Boot;
import model.helper.TiledMapHelper;
import model.objects.*;
import view.GameOverScreen;
import view.GameScreen;
import view.LevelCompletedScreen;
import view.StartScreen;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private World world;
    private TiledMapHelper tiledMapHelper;

    private Hud hud;
    private Integer score;
    public boolean levelCompleted;
    private boolean reload = false;

    private static final int numPlayers = 3; // TODO: Variable number of players
    private static final int numControllers = 3;
    private final GameObjectFactory factory = new GameObjectFactory(this);
    private final List<String> levels;
    private int level = 0;
    private List<Controller> controllers;
    private List<Player> players;
    private List<Goomba> goombas;
    private List<Coin> coins;

    private List<Goal> goals;

    private GameState state;
    private GameController gameController;



    public GameModel() {
        state = GameState.STARTUP;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add("level1");
        levels.add("level2");
        levels.add("platformTest");
        levels.add("slopeTest");
        levels.add("CameraTest");

        createWorld(levels.get(level));

        createObjects();

        createHUD();

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
    }

    private void createHUD() {
        hud = new Hud(new SpriteBatch(), this);
        score = 0;
    }

    private void createWorld(String level) {
        this.world = new World( new Vector2( 0 , -10f ), false );
        this.world.setContactListener(new GameContactListener(this));
        this.tiledMapHelper = new TiledMapHelper(this, level);
    }

    private void createObjects() {
        players = new ArrayList<>();
        List<Vector2> spawnPoints = tiledMapHelper.parseMapSpawnPoints("Player");
        for (int i = 0; i < Math.min(numPlayers, numControllers); i++) { // TODO: Might produce IndexOutOfBoundsException
            players.add(new Player("Player",  this, spawnPoints.get(i).x, spawnPoints.get(i).y));
        }
        System.out.println(players);

        goombas = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Goomba")){
            goombas.add((Goomba) factory.create("Goomba", v.x, v.y));
        }
        System.out.println(goombas);

        coins = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Coin")){
            coins.add((Coin) factory.create("Coin", v.x, v.y));
        }
        System.out.println(coins);

        goals = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Goal")){
            goals.add((Goal) factory.create("Goal", v.x, v.y));
        }
        System.out.println(goals);

    }

    private boolean gameOver() {
        for (Player player : players) {
            if (!player.isDead()) {
                return false;
            }
        }
        return true;
    }

    private void restart(){
        if (levelCompleted){
            level++;
            setLevelCompleted(false);
        }
        world.dispose();
        createWorld(levels.get(level));
        createObjects();
        score = 0;
        reload = true;
    }

    public void update() {
        gameController.inputListener();

        if (levelCompleted) {
            state = GameState.NEXT_LEVEL;
            changeScreen();
            restart();
        }
        if (gameOver()) {
            state = GameState.GAME_OVER;
            changeScreen();
            restart();
        }

        world.step(1/60f, 6, 2);

        for (int i = 0; i < getPlayers().size(); i++) {
            controllers.get(i).inputListener(players.get(i));
        }
        for (Player player : getPlayers()) {
            player.update();
        }

        for (Goomba goomba : getGoombas()) {
            goomba.update();
        }

        hud.update();
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return tiledMapHelper.setupMap();
    }

    public void increaseScore(Integer value) {
        score += value;
    }

    public void setLevelCompleted(boolean value){
        levelCompleted = value;
    }

    public Integer getScore() {
        return score;
    }

    public World getWorld() {
        return world;
    }

    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
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

    public String getLevel(){
        return levels.get(level);
    }

    public boolean getReload(){
        return reload;
    }

    public void setReload(Boolean value){
        reload = value;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        if (state == null) {
            throw new IllegalArgumentException("Cannot set state to null.");
        }
        if (this.state == GameState.STARTUP && (state == GameState.GAME_OVER || state == GameState.NEXT_LEVEL)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.state == GameState.GAME_OVER && (state == GameState.STARTUP || state == GameState.NEXT_LEVEL)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.state == GameState.NEXT_LEVEL && (state == GameState.GAME_OVER || state == GameState.STARTUP)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        this.state = state;
    }

    public void changeScreen() {

        switch (state) {
            case ACTIVE -> Boot.INSTANCE.setScreen(new GameScreen(Boot.INSTANCE.getCamera(), this));
            case STARTUP -> Boot.INSTANCE.setScreen(new StartScreen(this));
            case GAME_OVER -> Boot.INSTANCE.setScreen(new GameOverScreen(this));
            case NEXT_LEVEL -> Boot.INSTANCE.setScreen(new LevelCompletedScreen(this));
        }

    }

}
