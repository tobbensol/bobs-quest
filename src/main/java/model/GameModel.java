package model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import controls.ArrowController;
import controls.Controller;
import controls.CustomController;
import controls.WASDController;
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
    private List<String> availableLevels;
    private Level level;
    private int levelNR = 0;
    private int numPlayers;

    private GameState currentState;
    private GameState previousState;

    private boolean pause = false;
    private boolean initializeLevel = true;
    private GameCamera camera;

    private final AudioHelper audioHelper;
    private Music music;
    private final Music menuMusic;
    private float backgroundX;


    private Screen currentScreen;
    private Screen previousScreen;

    public GameModel() {
        currentState = GameState.MAIN_MENU;
        previousState = GameState.MAIN_MENU;
        currentScreen = null;
        previousScreen = null;

        numPlayers = 1;

        levels = new ArrayList<>(); // Remember Linux is case-sensitive. File names needs to be exact!
        levels.add("level-1");
        levels.add("level-2");
        levels.add("level-3");

        availableLevels = new ArrayList<>();

        controllers = new ArrayList<>();
        controllers.add(new ArrowController());
        controllers.add(new WASDController());
        controllers.add(new CustomController(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K));
        numControllers = controllers.size();

        audioHelper = new AudioHelper();
        menuMusic = audioHelper.getMusic("Menu_Game_song");
        menuMusic.setLooping(true);
    }

    private boolean gameOver() {
        for (Player player : getLevel().getGameObjects(Player.class)) {
            if (!player.isDestroyed()) {
                return false;
            }
        }
        getAudioHelper().getSoundEffect("gameOver").play(getAudioHelper().getSoundEffectsVolume());
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

        if (currentState != GameState.ACTIVE) {
            menuMusic.setVolume(audioHelper.getMusicVolume());
            menuMusic.play();
        } else {
            menuMusic.pause();
        }

        if (initializeLevel) {
            level = createLevel();
            availableLevels.add(level.getLevelName());
            music = level.getLevelMusic();
            createCamera();
            initializeLevel = false;
            pauseGame();
            backgroundX = 0;
        }

        Boot.INSTANCE.getGameController().inputListener();

        if (isPaused()) {
            return;
        }
        if (currentState == GameState.GAME_OVER || currentState == GameState.NEXT_LEVEL || currentState == GameState.GAME_COMPLETED) {
            restart();
        }
        if (getLevel().isCompleted()) {
            music.stop();
            music.dispose();
            getAudioHelper().getSoundEffect("orchestra").play(getAudioHelper().getSoundEffectsVolume());

            if (levelNR == levels.size() - 1) {
                currentState = GameState.GAME_COMPLETED;
            } else {
                currentState = GameState.NEXT_LEVEL;
            }
            changeScreen();
        }
        if (gameOver()) {
            music.stop();
            music.dispose();
            currentState = GameState.GAME_OVER;
            changeScreen();
        }

        getLevel().getWorld().step(1 / 60f, 12, 4);

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

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music m) {
        music = m;
    }

    @Override
    public boolean restart() {
        boolean nextLevel = getLevel().isCompleted();
        if (nextLevel) {
            levelNR++;
            if (levelNR > levels.size() - 1) {
                levelNR = 0;
            }
        }
        resetZoom();
        level = createLevel();
        if (!availableLevels.contains(level.getLevelName())) {
            availableLevels.add(level.getLevelName());
        }
        getMusic().stop();
        getMusic().dispose();
        setMusic(level.getLevelMusic());
        getMusic().pause();
        pauseGame();
        return nextLevel;
    }

    @Override
    public GameState getCurrentState() {
        return currentState;
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
    public GameState getPreviousState() {
        return previousState;
    }

    @Override
    public void changeScreen() {
        Screen screen = null;
        switch (currentState) {
            case ACTIVE -> screen = new GameScreen(this);
            case MAIN_MENU -> screen = new MainMenuScreen(this);
            case GAME_OVER -> screen = new GameOverScreen(this);
            case GAME_COMPLETED -> screen = new GameCompletedScreen(this);
            case NEXT_LEVEL -> screen = new LevelCompletedScreen(this);
            case SETTINGS -> screen = new SettingsScreen(this);
            case NEW_GAME -> screen = new NewGameScreen(this);
            case SELECT_LEVEL -> screen = new SelectLevelScreen(this);
        }
        previousScreen = currentScreen;
        currentScreen = screen;

        Boot.INSTANCE.setScreen(screen);

        if (previousScreen != null) {
            previousScreen.dispose();
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
        getLevel().getHud().pause();
        getLevel().updateHUD();
        getMusic().pause();
    }

    @Override
    public void resumeGame() {
        pause = false;
        getLevel().getHud().resume();
        getMusic().play();
        getMusic().setVolume(getAudioHelper().getMusicVolume());
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

    public float getBackgroundX() {
        return backgroundX;
    }

    public void setBackgroundX(float stateTime) {
        this.backgroundX = stateTime;
    }

    public float getMusicVolume() {
        return getAudioHelper().getMusicVolume();
    }

    @Override
    public void setMusicVolume(float musicVolume) {
        audioHelper.setMusicVolume(musicVolume);
    }

    public float getSoundEffectsVolume() {
        return audioHelper.getSoundEffectsVolume();
    }

    @Override
    public void setSoundEffectsVolume(float soundEffectsVolume) {
        getAudioHelper().setSoundEffectsVolume(soundEffectsVolume);
    }

    @Override
    public void startNewGame(int numberOfPlayers) {
        if (numberOfPlayers < 1 || numberOfPlayers > 3) {
            throw new IllegalArgumentException("Number of players must be between 1 and 3");
        }
        resetAvailableLevels();
        startGame(0, numberOfPlayers);
    }

    @Override
    public void startSelectedLevel(String levelName) {
        int levelNR = getLevels().indexOf(levelName);
        if (levelNR == -1) {
            throw new IllegalArgumentException("No level named " + levelName);
        }
        int numPlayers = getNumPlayers();
        startGame(levelNR, numPlayers);
    }

    private void startGame(int levelNR, int numberOfPlayers) {
        if (getCurrentState() != GameState.ACTIVE) {
            setLevelNR(levelNR);
            setNumPlayers(numberOfPlayers);
            restart();
            setCurrentState(GameState.ACTIVE);
            changeScreen();
            resumeGame();
        }
    }

    @Override
    public void continueGame() {
        if (getCurrentState() != GameState.ACTIVE) {
            setCurrentState(GameState.ACTIVE);
            changeScreen();
            if (!isPaused()) {
                resumeGame();
            }
        }
    }

    @Override
    public void goToScreen(GameState state) {
        setCurrentState(state);
        changeScreen();
        if (getCurrentState() == GameState.ACTIVE) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    public void resetZoom() {
        camera.resetZoom();
    }

}
