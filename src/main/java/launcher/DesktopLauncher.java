package launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("incredibly epic game");
        cfg.setForegroundFPS(60);
        cfg.setWindowedMode(1600, 900);

        new Lwjgl3Application(new Boot(), cfg);
    }
}