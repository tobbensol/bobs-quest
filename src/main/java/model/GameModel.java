package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
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
    private List<String> completedLevels;
    private final List<Controller> controllers;
    private final int numControllers;
    private final GameController gameController;
    Level level;
    private boolean reload = false;
    private int levelNR = 0;
    private int numPlayers;

    private GameState currentState;
    private GameState previousState;

    private boolean pause = false;
    private boolean initializeLevel = true;
    private GameCamera camera;

    private AudioHelper audioHelper;
    private Music music;
    private float musicVolume;
    private float soundEffectsvolume;

    public GameModel() {
        currentState = GameState.STARTUP;
        previousState = GameState.STARTUP;
        this.numPlayers = 1;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add("level1"); // 0
        levels.add("platformTest"); // 1
        levels.add("slopeTest"); // 2
        levels.add("cameraTest"); // 3
        levels.add("goombaTest"); // 4
        levels.add("coinTest"); // 5
        levels.add("valleyAndSpikeTest"); // 6
        levels.add("sizeTest"); // 7
        levels.add("goombaCollisionTest"); // 8
        levels.add("floaterTest"); // 9

        completedLevels = new ArrayList<>();

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();

        audioHelper = new AudioHelper();
        musicVolume = 0.5f;
        soundEffectsvolume = 0.5f;
    }

    private boolean gameOver() {
        for (Player player : getLevel().getGameObjects(Player.class)) {
            if (!player.getFrozen()) {
                return false;
            }
        }
        audioHelper.getSoundEffect("gameover").play(soundEffectsvolume);
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
            music.setVolume(musicVolume);
        }

        if (getLevel().isCompleted()) {
            if (!completedLevels.contains(level.getLevelName())) {
                completedLevels.add(level.getLevelName());
            }
            music.stop();
            music.dispose();
            audioHelper.getSoundEffect("orchestra").play(soundEffectsvolume);
            currentState = GameState.NEXT_LEVEL;
            changeScreen();
            restart();
            pauseGame();
        }
        if (gameOver()) {
            music.stop();
            music.dispose();
            currentState = GameState.GAME_OVER;
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
        music = level.getLevelMusic();
        pauseGame();
    }

    @Override
    public GameState getCurrentState() {
        return currentState;
    }
    public GameState getPreviousState() {
        return previousState;
    }

    @Override
    public void setCurrentState(GameState currentState) {
        if (currentState == null) {
            throw new IllegalArgumentException("Cannot set state to null.");
        }
        //TODO: Check if GameState is valid or not. (old checks disabled for now)
//        if (this.currentState == GameState.STARTUP && (currentState == GameState.GAME_OVER || currentState == GameState.NEXT_LEVEL)) {
//            throw new IllegalArgumentException("Illegal state.");
//        }
//        if (this.currentState == GameState.GAME_OVER && (currentState == GameState.STARTUP || currentState == GameState.NEXT_LEVEL)) {
//            throw new IllegalArgumentException("Illegal state.");
//        }
//        if (this.currentState == GameState.NEXT_LEVEL && (currentState == GameState.GAME_OVER /*|| currentState == GameState.STARTUP*/)) {
//            throw new IllegalArgumentException("Illegal state.");
//        }
        previousState = this.currentState;
        this.currentState = currentState;
    }

    @Override
    public void changeScreen() {

        switch (currentState) {
            case ACTIVE -> Boot.INSTANCE.setScreen(new GameScreen(this));
            case STARTUP -> Boot.INSTANCE.setScreen(new StartScreen(this));
            case GAME_OVER -> Boot.INSTANCE.setScreen(new GameOverScreen(this));
            case NEXT_LEVEL -> Boot.INSTANCE.setScreen(new LevelCompletedScreen(this));
            case SETTINGS -> Boot.INSTANCE.setScreen(new SettingsScreen(this));
            case NEWGAME -> Boot.INSTANCE.setScreen(new NewGameScreen(this));
            case SELECTLEVEL -> Boot.INSTANCE.setScreen(new SelectLevelScreen(this));
        }

    }

    public Level getLevel() {
        return level;
    }

    public List<String> getLevels() {
        return levels;
    }

    public List<String> getCompletedLevels() {
        return completedLevels;
    }

    public void setLevelNR(int levelNR) {
        this.levelNR = levelNR;
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

    public AudioHelper getAudioHelper() {
        return audioHelper;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void setSoundEffectsvolume(float soundEffectsvolume) {
        this.soundEffectsvolume = soundEffectsvolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getSoundEffectsvolume() {
        return soundEffectsvolume;
    }

}
