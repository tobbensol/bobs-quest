package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import controls.ArrowController;
import controls.Controller;
import controls.CustomController;
import controls.WASDController;
import model.helper.TiledMapHelper;
import model.objects.*;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private World world;
    private GameContactListener gameContactListener;
    private TiledMapHelper tiledMapHelper;

    private Hud hud;
    private Integer score;

    private final int numPlayers = 3; // TODO: Variable number of players
    private final int numControllers = 3;
    private GameObjectFactory factory = new GameObjectFactory(this);
    private List<Player> players;
    private List<Controller> controllers;
    private List<Goomba> goombas;
    private List<Coin> coins;


    public GameModel() {
        this.world = new World( new Vector2( 0 , -10f ), false );
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);
        this.tiledMapHelper = new TiledMapHelper(this);

        hud = new Hud(new SpriteBatch(), this);
        score = 0;

        players = new ArrayList<>();
        List<Vector2> spawnPoints = tiledMapHelper.parseMapSpawnPoints("Player");
        for (int i = 0; i < Math.min(numPlayers, numControllers); i++) { // TODO: Might produce IndexOutOfBoundsException
            players.add(new Player("Player",  this, spawnPoints.get(i).x, spawnPoints.get(i).y));
        }
        System.out.println(players);

        // Add goomba
        goombas = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Goomba")){
//            goombas.add(new Goomba("Goomba 1", this, center.x, center.y, 1, ContactType.ENEMY));
            goombas.add((Goomba) factory.create("Goomba", v.x, v.y));
        }
        System.out.println(goombas);

        coins = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.parseMapSpawnPoints("Coin")){
//            coins.add(new newCoin("Coin", this, center.x, center.y, 1, ContactType.COIN));
            coins.add((Coin) factory.create("Coin", v.x, v.y));
        }
        System.out.println(coins);


        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
    }

    public OrthogonalTiledMapRenderer setupMap() {
        return tiledMapHelper.setupMap();
    }

    private boolean checkRestart() {
        for (Player player : players) {
            if (player.isDead()) {
                return true;
            }
        }
        return false;
    }

    private void restart(){
        System.out.println(":D");
    }

    public void update() {
        if (checkRestart()){
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

        hud.updateScore();
    }

    public void increaseScore(Integer value) {
        score += value;
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
        return players;
    }

    public Hud getHud() {
        return hud;
    }

    public List<Goomba> getGoombas() {
        return goombas;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}
