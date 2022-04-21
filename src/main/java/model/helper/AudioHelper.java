package model.helper;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class AudioHelper {

    private final AssetManager assetManager;
    private Map<String, Music> levelMusic;
    private float musicVolume;
    private float soundEffectsVolume;


    public AudioHelper() {
        assetManager = new AssetManager();
        loadMusic();
        loadSoundEffects();
        assetManager.finishLoading();

        musicVolume = 0.5f;
        soundEffectsVolume = 0.5f;

        levelMusic = new HashMap<>(); // ADD level specific music here:
        levelMusic.put("level-1", getMusic("Grasslands Theme"));
        levelMusic.put("level-2", getMusic("Iceland Theme"));

    }

    private void loadMusic() {
        assetManager.load("audio/music/Grasslands Theme.mp3", Music.class);
        assetManager.load("audio/music/Dungeon Theme.mp3", Music.class);
        assetManager.load("audio/music/Boss Theme.mp3", Music.class);
        assetManager.load("audio/music/Iceland Theme.mp3", Music.class);
        assetManager.load("audio/music/Intro Theme.mp3", Music.class);
        assetManager.load("audio/music/Mushroom Theme.mp3", Music.class);
        assetManager.load("audio/music/Worldmap Theme.mp3", Music.class);
        assetManager.load("audio/music/Desert Theme.mp3", Music.class);
    }

    private void loadSoundEffects() {
        assetManager.load("audio/sounds/gameover.wav", Sound.class);
        assetManager.load("audio/sounds/coin.wav", Sound.class);
        assetManager.load("audio/sounds/drop.wav", Sound.class);
        assetManager.load("audio/sounds/hit.wav", Sound.class);
        assetManager.load("audio/sounds/jump.wav", Sound.class);
        assetManager.load("audio/sounds/chipquest.wav", Sound.class);
        assetManager.load("audio/sounds/orchestra.wav", Sound.class);

    }

    public Sound getSoundEffect(String name) {
        String filename = "audio/sounds/" + name + ".wav";
        if (assetManager.contains(filename, Sound.class)) {
            return assetManager.get(filename, Sound.class);
        } else {
            throw new IllegalArgumentException("Sound effect '" + name + "' not found.");
        }
    }

    public Music getMusic(String name) {
        String filename = "audio/music/" + name + ".mp3";
        if (assetManager.contains(filename)) {
            return assetManager.get("audio/music/" + name + ".mp3", Music.class);
        } else {
            throw new IllegalArgumentException("Music '" + name + "' not found.");
        }
    }

    public Music getLevelMusic(String level) {
        if (levelMusic.containsKey(level)) {
            return levelMusic.get(level);
        } else {
            return getMusic("Intro Theme"); // Default music if level does not have specified music.
            //throw new IllegalArgumentException("This level does not have any music"); //TODO: empty music or standard music?
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void setSoundEffectsVolume(float soundEffectsVolume) {
        this.soundEffectsVolume = soundEffectsVolume;
    }

    public void dispose() {
        assetManager.dispose();
    }
}
