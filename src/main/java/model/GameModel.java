package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import controls.*;
import launcher.Boot;
import model.objects.GameObject;
import model.objects.IGameObject;
import model.objects.Player;
import view.*;

import java.util.ArrayList;
import java.util.List;

public class GameModel implements ControllableModel {


    private final List<String> levels;
    private final List<Controller> controllers;
    private final int numControllers;
    private final GameController gameController;
    Level level;
    private boolean reload = false;
    private int levelNR = 0;
    private int numPlayers;
    private GameState state;
    private boolean pause = false;
    private boolean initializeLevel = true;
    private GameCamera camera;

    public GameModel() {
        state = GameState.STARTUP;
        this.numPlayers = 1;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add("level1"); // 0
        levels.add("level2"); // 1
        levels.add("platformTest"); // 2
        levels.add("slopeTest"); // 3
        levels.add("cameraTest"); // 4
        levels.add("goombaTest"); // 5
        levels.add("coinTest"); // 6
        levels.add("valleyAndSpikeTest"); // 7
        levels.add("sizeTest"); // 8
        levels.add("goombaCollisionTest"); // 9
        levels.add("floaterTest"); // 10

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();

    }

    private boolean gameOver() {
        for (Player player : getLevel().getGameObjects(Player.class)) {
            if (!player.getFrozen()) {
                return false;
            }
        }
        return true;
    }

    protected Level createLevel() {
        return new Level(levels.get(levelNR), this);
    }

    protected void createCamera() {
        this.camera = new GameCamera(this);
        this.camera.setToOrtho(false, Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight());
    }

    public void update() {
        if (initializeLevel) {
            level = createLevel();
            createCamera();
            initializeLevel = false;
            pauseGame();
        }

        gameController.inputListener();

        if (isPaused()) {
            getLevel().getHud().pause();
            getLevel().updateHUD();
            return;
        } else {
            getLevel().getHud().resume();
        }

        if (getLevel().isCompleted()) {
            state = GameState.NEXT_LEVEL;
            changeScreen();
            restart();
            pauseGame();
        }
        if (gameOver()) {
            //TODO: Add some delay after all players are dead, an animation for 3 sec or something
            state = GameState.GAME_OVER;
            changeScreen();
            restart();
            pauseGame();
        }

        getLevel().getWorld().step(Gdx.graphics.getDeltaTime(), 12, 4);

        List<Player> players = getLevel().getGameObjects(Player.class);
        for (int i = 0; i < players.size(); i++) {
            controllers.get(i).inputListener(players.get(i));
        }
        for (IGameObject object : getLevel().getGameObjects()) {
            object.update();
        }

        getLevel().updateHUD();
    }

    public GameCamera getCamera() {
        return camera;
    }

    public boolean getReload() {
        return reload;
    }

    public void setReload(Boolean value) {
        reload = value;
    }

    @Override
    public void restart() {
        if (getLevel().isCompleted()) {
            levelNR++;
        }
        level = createLevel();
        changeScreen();
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
            case ACTIVE -> Boot.INSTANCE.setScreen(new GameScreen(this));
            case STARTUP -> Boot.INSTANCE.setScreen(new StartScreen(this));
            case GAME_OVER -> Boot.INSTANCE.setScreen(new GameOverScreen(this));
            case NEXT_LEVEL -> Boot.INSTANCE.setScreen(new LevelCompletedScreen(this));
        }

    }

    public Level getLevel() {
        return level;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
        restart();
        pauseGame();
    }

    @Override
    public void pauseGame() {
        pause = true;
    }

    @Override
    public void resumeGame() {
        pause = false;
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    public int getNumControllers() {
        return numControllers;
    }
}
