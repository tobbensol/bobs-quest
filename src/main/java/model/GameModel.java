package model;

import com.badlogic.gdx.Input;
import controls.*;
import launcher.Boot;
import model.objects.GameObject;
import model.objects.Player;
import view.GameOverScreen;
import view.GameScreen;
import view.LevelCompletedScreen;
import view.StartScreen;

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

    public GameModel() {
        state = GameState.STARTUP;
        this.numPlayers = 1;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add("level1");
        levels.add("level2");
        levels.add("platformTest");
        levels.add("slopeTest");
        levels.add("cameraTest");
        levels.add("goombaTest");
        levels.add("coinTest");
        levels.add("valleyAndSpikeTest");
        levels.add("sizeTest");
        levels.add("goombaCollisionTest");

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();

        level = new Level(levels.get(levelNR), this);
    }

    private boolean gameOver() {
        for (Player player : getLevel().getGameObjects(Player.class)) {
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
            //TODO: Add some delay after all players are dead, an animation for 3 sec or something
            state = GameState.GAME_OVER;
            changeScreen();
            restart();
        }

        getLevel().getWorld().step(Gdx.graphics.getDeltaTime(), 12, 4);

        List<Player> players = getLevel().getGameObjects(Player.class);
        for (int i = 0; i < players.size(); i++) {
            controllers.get(i).inputListener(players.get(i));
        }
        for (GameObject object : getLevel().getGameObjects()) {
            object.update();
        }

        getLevel().updateHUD();
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
        level = new Level(levels.get(levelNR), this);
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
    }

    public int getNumControllers() {
        return numControllers;
    }
}
