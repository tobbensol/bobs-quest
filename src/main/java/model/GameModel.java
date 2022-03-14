package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import controls.ArrowController;
import controls.Controller;
import controls.CustomController;
import controls.WASDController;
import model.helper.ContactType;
import model.helper.TiledMapHelper;
import model.objects.Goomba;
import model.objects.Player;

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
    private List<Player> players;
    private List<Controller> controllers;
    private List<Goomba> goombas;


    public GameModel() {
        this.world = new World( new Vector2( 0 , -10f ), false );
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);
        this.tiledMapHelper = new TiledMapHelper(this);

        hud = new Hud(new SpriteBatch(), this);
        score = 0;

        players = new ArrayList<>();
        for (int i = 0; i < Math.min(numPlayers, numControllers); i++) { // TODO: Might produce IndexOutOfBoundsException
            Vector2 spawnPoint = tiledMapHelper.getSpawnPoint("Player").get(i);
            players.add(new Player("Player" + (i+1),  this, spawnPoint.x, spawnPoint.y-10, 0.8f));
        }

        // Add goomba TODO: Add "all" goombas
        goombas = new ArrayList<>();
        for (Vector2 v : tiledMapHelper.getSpawnPoint("Goomba")){
            goombas.add(new Goomba("Goomba 1", this, v.x, v.y, 1, ContactType.ENEMY));
        }


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
            if (!player.isDead()) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        world.step(1/60f, 6, 2);

        if (checkRestart()){
//           -
//            for (int i = 0; i < Math.min(numPlayers, numControllers); i++) {
//                Vector2 spawnPoint = tiledMapHelper.getSpawnPoints().get(i);
//                players.set(i, new Player("Player" + (i+1), "marioSprite.png", this, spawnPoint.x, spawnPoint.y-10, 1));
//            }
        }

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
}
