package model;

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
    private List<String> availableLevels;
    private final List<Controller> controllers;
    private final int numControllers;
    private final GameController gameController;
    private Level level;
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
        currentState = GameState.MAIN_MENU;
        previousState = GameState.MAIN_MENU;
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

        availableLevels = new ArrayList<>();

        gameController = new GameController(this);

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();


        musicVolume = 0.5f;
        soundEffectsvolume = 0.5f;
    }

    private boolean gameOver() {
        for (Player player : getLevel().getGameObjects(Player.class)) {
            if (!player.isDestroyed()) {
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
            audioHelper = new AudioHelper();
            level = createLevel();
            availableLevels.add(level.getLevelName());
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
        } else { //TODO i dont think this should happen every update
            getLevel().getHud().resume();
            music.play();
            music.setVolume(musicVolume);
        }
        if(currentState == GameState.GAME_OVER || currentState == GameState.NEXT_LEVEL){
            restart();
        }
        if (getLevel().isCompleted()) {
            music.stop();
            music.dispose();
            audioHelper.getSoundEffect("orchestra").play(soundEffectsvolume);
            currentState = GameState.NEXT_LEVEL;
            changeScreen();
        }
        if (gameOver()) {
            music.stop();
            music.dispose();
            currentState = GameState.GAME_OVER;
            changeScreen();
        }

        getLevel().getWorld().step(1/60f, 12, 4);

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

    @Override
    public boolean restart() {
        boolean nextLevel = getLevel().isCompleted();
        if (nextLevel) {
            levelNR++;
        }
        //TODO only here to make tests work, not a problem outside of tests
        if (camera != null){
            camera.resetZoom();
        }
        level = createLevel();
        if (!availableLevels.contains(level.getLevelName())) {
            availableLevels.add(level.getLevelName());
        }
        music = level.getLevelMusic();
        pauseGame();
        return nextLevel;
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
        if (this.currentState == GameState.MAIN_MENU && (currentState == GameState.NEXT_LEVEL || currentState == GameState.GAME_OVER)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.currentState == GameState.NEW_GAME && (currentState == GameState.NEXT_LEVEL || currentState == GameState.GAME_OVER || currentState == GameState.SETTINGS)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.currentState == GameState.SELECT_LEVEL && (currentState == GameState.NEXT_LEVEL || currentState == GameState.GAME_OVER || currentState == GameState.SETTINGS)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.currentState == GameState.SETTINGS && (currentState == GameState.NEXT_LEVEL || currentState == GameState.GAME_OVER || currentState == GameState.SELECT_LEVEL)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.currentState == GameState.NEXT_LEVEL && (currentState == GameState.GAME_OVER || currentState == GameState.NEW_GAME || currentState == GameState.SELECT_LEVEL)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.currentState == GameState.ACTIVE && (currentState == GameState.NEW_GAME || currentState == GameState.SETTINGS)) {
            throw new IllegalArgumentException("Illegal state.");
        }
        if (this.currentState == GameState.GAME_OVER && (currentState == GameState.NEXT_LEVEL || currentState == GameState.SETTINGS)) {
            throw new IllegalArgumentException("Illegal state.");
        }

        previousState = this.currentState;
        this.currentState = currentState;
    }

    @Override
    public void changeScreen() {

        switch (currentState) {
            case ACTIVE -> Boot.INSTANCE.setScreen(new GameScreen(this));
            case MAIN_MENU -> Boot.INSTANCE.setScreen(new MainMenuScreen(this));
            case GAME_OVER -> Boot.INSTANCE.setScreen(new GameOverScreen(this));
            case NEXT_LEVEL -> Boot.INSTANCE.setScreen(new LevelCompletedScreen(this));
            case SETTINGS -> Boot.INSTANCE.setScreen(new SettingsScreen(this));
            case NEW_GAME -> Boot.INSTANCE.setScreen(new NewGameScreen(this));
            case SELECT_LEVEL -> Boot.INSTANCE.setScreen(new SelectLevelScreen(this));
        }

    }

    public Level getLevel() {
        return level;
    }

    public List<String> getLevels() {
        return levels;
    }

    public List<String> getAvailableLevels() {
        return availableLevels;
    }

    public void resetAvailableLevels() {
        availableLevels = new ArrayList<>();
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
