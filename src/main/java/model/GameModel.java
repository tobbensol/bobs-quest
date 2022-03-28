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

public class GameModel implements ControllableModel{



    private boolean reload = false;

    private final List<Level> levels;
    private int level = 0;
    private List<Controller> controllers;
    private int numPlayers;
    private int numControllers;
    private GameState state;
    private GameController gameController;



    public GameModel() {
        state = GameState.STARTUP;
        this.numPlayers = 1;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add(new Level("level1", this));
//        levels.add("level2");
//        levels.add("platformTest");
//        levels.add("slopeTest");
//        levels.add("CameraTest");

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();
    }

    private boolean gameOver() {
        for (Player player : getLevel().getPlayers()) {
            if (!player.isDead()) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        gameController.inputListener();

        if (getLevel().isCompleted()) {
            state = GameState.NEXT_LEVEL;
            changeScreen();
            restart();
        }
        if (gameOver()) {
            state = GameState.GAME_OVER;
            changeScreen();
            restart();
        }

        getLevel().getWorld().step(1/60f, 6, 2);

        for (int i = 0; i < getLevel().getPlayers().size(); i++) {
            controllers.get(i).inputListener(getLevel().getPlayers().get(i));
        }
        for (Player player : getLevel().getPlayers()) {
            player.update();
        }

        for (Goomba goomba : getLevel().getGoombas()) {
            goomba.update();
        }

        getLevel().updateHUD();
    }

    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }

    public Level getLevel(){
        return levels.get(level);
    }

    public boolean getReload(){
        return reload;
    }

    public void setReload(Boolean value){
        reload = value;
    }

    @Override
    public void restart(){
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

    @Override
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        restart();
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
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

    @Override
    public void changeScreen() {

        switch (state) {
            case ACTIVE -> Boot.INSTANCE.setScreen(new GameScreen(Boot.INSTANCE.getCamera(), this));
            case STARTUP -> Boot.INSTANCE.setScreen(new StartScreen(this));
            case GAME_OVER -> Boot.INSTANCE.setScreen(new GameOverScreen(this));
            case NEXT_LEVEL -> Boot.INSTANCE.setScreen(new LevelCompletedScreen(this));
        }

    }

}
