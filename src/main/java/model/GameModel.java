package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import controls.ArrowController;
import controls.Controller;
import controls.WASDController;
import model.helper.TiledMapHelper;
import model.objects.Player;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private World world;
    private GameContactListener gameContactListener;
    private TiledMapHelper tiledMapHelper;

    private final int numPlayers = 2; // TODO: Variable number of players
    private final int numControllers = 2;
    private List<Player> players;
    private List<Controller> controllers;


    public GameModel() {
        this.world = new World( new Vector2( 0 , -10f ), false );
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);
        this.tiledMapHelper = new TiledMapHelper(this);
        players = new ArrayList<>();
        for (int i = 0; i < Math.min(numPlayers, numControllers); i++) {
            //players.add(new Player("Player" + (i+1), "player_stick.png", this, i*100, 400, 1));
            players.add(new Player("Player" + (i+1), "marioSprite.png", this, i*100, 400, 1));
        }

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
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

    public OrthogonalTiledMapRenderer setupMap() {
        return tiledMapHelper.setupMap();
    }

    public void update() {
        world.step(1/60f, 6, 2);

        for (int i = 0; i < getPlayers().size(); i++) {
            controllers.get(i).inputListener(players.get(i));
        }
        for (Player player : getPlayers()) {
            player.update();
        }
    }
}
