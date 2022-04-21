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
}
