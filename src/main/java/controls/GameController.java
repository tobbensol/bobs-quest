package controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import launcher.Boot;
import model.ControllableModel;
import model.GameState;

public class GameController {
    private final ControllableModel model;
    private boolean pauseHelper = true; // If initialized with false, cannot pause on first iteration of inputListener()

    public GameController(ControllableModel model) {
        this.model = model;
    }

    public void inputListener() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && model.getCurrentState() == GameState.ACTIVE) {
            if (model.isPaused()) {
                model.resumeGame();
            } else {
                model.pauseGame();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.M) && model.getCurrentState() == GameState.ACTIVE && model.isPaused()) {
            model.setCurrentState(GameState.MAIN_MENU);
            model.changeScreen();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            model.restart();
        }

        if (model.getCurrentState() == GameState.MAIN_MENU) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                model.setCurrentState(GameState.ACTIVE);
                model.changeScreen();
                model.resumeGame();
            }
        }

        if (model.getCurrentState() == GameState.GAME_OVER || model.getCurrentState() == GameState.NEXT_LEVEL) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                model.setCurrentState(GameState.ACTIVE);
                model.changeScreen();
                model.resumeGame();
            }
        }

    }

    /**
     * This method returns a drag-listener that updates the volume in the GameModel according to the value of the slider inside the Settings Screen.
     *
     * @param slider - The slider to update from.
     * @param music  - True if music, false if sound effect.
     * @return
     */
    public DragListener volumeListener(Slider slider, boolean music) {
        return new DragListener() {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                if (music) {
                    model.setMusicVolume(slider.getValue());
                } else {
                    model.setSoundEffectsVolume(slider.getValue());
                }
            }
        };
    }

    public ChangeListener goBackListener() {
        return goToScreenListener(model.getPreviousState());
    }

    public ChangeListener exitListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        };
    }

    public ChangeListener goToScreenListener(GameState state) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.goToScreen(state);
            }
        };
    }

    public ChangeListener newGameListener(int numberOfPlayers) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.startNewGame(numberOfPlayers);
            }
        };
    }

    public ChangeListener continueGameListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.continueGame();
            }
        };
    }

    public ChangeListener selectLevel(TextButton textButton) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String level = String.valueOf(textButton.getText());
                model.startSelectedLevel(level);
            }
        };
    }


    public ChangeListener fullScreenListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.graphics.isFullscreen()){
                    Gdx.graphics.setWindowedMode((int)(Boot.INSTANCE.getScreenWidth()*0.9f) , (int)(Boot.INSTANCE.getScreenHeight()*0.9f));
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        };
    }
}
