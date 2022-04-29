package model;

/**
 * A controllable model object should give the user the ability to control the game model,
 * for example to set the number of players, restart the game, change screens etc.
 */
public interface ControllableModel {

    /**
     * This method should restart the game. If the level is completed,
     * it should start the next level and reset the score.
     *
     * @return true if restarting at next level
     */
    boolean restart();

    /**
     * @return returns the current GameState from the model.
     */
    GameState getCurrentState();

    /**
     * This method should set the current GameState to the given state.
     * The different GameStates are:
     * ACTIVE, GAME_OVER, STARTUP, NEXT_LEVEL
     *
     * @param currentState - the GameState to set.
     */
    void setCurrentState(GameState currentState);

    /**
     * @return returns the previous GameState from the model.
     */
    GameState getPreviousState();

    /**
     * This method should change the screen. By calling this method,
     * the screen wil change according to the current GameState to the model.
     */
    void changeScreen();

    /**
     * This method sets the number of players in the model.
     * The default number of players in model is 1.
     *
     * @param numPlayers - the number of players to set.
     */
    void setNumPlayers(int numPlayers);

    void pauseGame();

    void resumeGame();

    boolean isPaused();

    void goToScreen(GameState newGame);

    /**
     * This method let you start a new game from the beginning. The progress of the game will be lost.
     *
     * @param numberOfPlayers - The number of players you want to start the game with.
     */
    void startNewGame(int numberOfPlayers);


    void continueGame();

    /**
     * This method let you start a game at a specific level. The progress in the game will not be lost.
     *
     * @param level - The level you want to start at.
     */
    void startSelectedLevel(String level);

    /**
     * This method sets the music volume in the GameModel.
     *
     * @param volume - float value between 0 and 1.
     */
    void setMusicVolume(float volume);

    /**
     * This method sets the sound effect volume in the GameModel.
     *
     * @param volume - float value between 0 and 1.
     */
    void setSoundEffectsVolume(float volume);
}
