package launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Bob's Quest");
        cfg.setForegroundFPS(60);
        cfg.setWindowedMode(1600, 900);
        cfg.setWindowIcon("Multi_Platformer_Tileset_v2/Players/Bob.png");
        cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        new Lwjgl3Application(new Boot(), cfg);
    }
}