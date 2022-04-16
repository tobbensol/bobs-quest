package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import controls.*;
import launcher.Boot;
import model.helper.AudioHelper;
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
    private Level level;
    private boolean reload = false;
    private int levelNR = 0;
    private int numPlayers;
    private GameState state;
    private boolean pause = false;
    private boolean initializeLevel = true;
    private GameCamera camera;

    private AudioHelper audioHelper;
    private Music music;

    public GameModel() {
        state = GameState.STARTUP;
        this.numPlayers = 1;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add("level-1"); // 0
        levels.add("level-2"); // 1
        levels.add("TestMaps/platform-test"); // 2
        levels.add("TestMaps/slope-test"); // 3
        levels.add("TestMaps/camera-test"); // 4
        levels.add("TestMaps/goomba-test"); // 5
        levels.add("TestMaps/coin-test"); // 6
        levels.add("TestMaps/valley-and-spike-test"); // 7
        levels.add("TestMaps/size-test"); // 8
        levels.add("TestMaps/goomba-collision-test"); // 9
        levels.add("TestMaps/floater-test"); // 10
        levels.add("TestMaps/moving-platform-test"); // 11

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();
    }

    private boolean gameOver() {
        for (Player player : getLevel().getGameObjects(Player.class)) {
            if (!player.isDestroyed()) {
                return false;
            }
        }
        audioHelper.getSoundEffect("gameover").play();
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
            audioHelper = new AudioHelper();
            level = createLevel();
            music = level.getLevelMusic();
            createCamera();
            initializeLevel = false;
            pauseGame();
        }

        gameController.inputListener();

        if (isPaused()) {
            getLevel().getHud().pause();
            getLevel().updateHUD();
            music.pause();
            return;
        } else {
            getLevel().getHud().resume();
            music.play();
        }

        if (getLevel().isCompleted()) {
            music.stop();
            music.dispose();
            audioHelper.getSoundEffect("orchestra").play();
            setState(GameState.NEXT_LEVEL); // TODO: Extract contents of if to own method (same for level completed and game over)
            changeScreen();
            restart();
        }
        if (gameOver()) {
            music.stop();
            music.dispose();
            setState(GameState.GAME_OVER);
            changeScreen();
            restart();
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
    public boolean restart() {
        boolean nextLevel = false;
        if (getLevel().isCompleted()) {
            levelNR++;
            nextLevel = true;
        }
        level = createLevel();
        music = level.getLevelMusic();
        pauseGame();
        return nextLevel;
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

    public AudioHelper getAudioHelper() {
        return audioHelper;
    }
}
