package model.helper;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioHelper {

    private final AssetManager assetManager;
    private final Map<String, Music> levelMusic;
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
        levelMusic.put("Grasslands", getMusic("Plains_Game_song"));
        levelMusic.put("Sandlands", getMusic("Desert_Game_song"));
        levelMusic.put("Snowlands", getMusic("Winter_Game_song"));
    }

    private void loadMusic() {
        assetManager.load("audio/music/Winter_Game_song.mp3", Music.class);
        assetManager.load("audio/music/Desert_Game_song.mp3", Music.class);
        assetManager.load("audio/music/Menu_Game_song.mp3", Music.class);
        assetManager.load("audio/music/Plains_Game_song.mp3", Music.class);

    }

    private void loadSoundEffects() {
        assetManager.load("audio/sounds/gameOver.wav", Sound.class);
        assetManager.load("audio/sounds/coin.wav", Sound.class);
        assetManager.load("audio/sounds/drop3.wav", Sound.class);
        assetManager.load("audio/sounds/hit2.wav", Sound.class);
        assetManager.load("audio/sounds/jump.wav", Sound.class);
        assetManager.load("audio/sounds/jump2.wav", Sound.class);
        assetManager.load("audio/sounds/orchestra.wav", Sound.class);
        assetManager.load("audio/sounds/deathScream.wav", Sound.class);
        assetManager.load("audio/sounds/goombaDeath.wav", Sound.class);
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
            Music song = assetManager.get("audio/music/" + name + ".mp3", Music.class);
            song.setLooping(true);
            return song;
        } else {
            throw new IllegalArgumentException("Music '" + name + "' not found.");
        }
    }

    public Music getLevelMusic(String level) {
        if (levelMusic.containsKey(level)) {
            return levelMusic.get(level);
        } else {
            return getMusic("Menu_Game_song"); // Default music if level does not have specified music.
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    public void setSoundEffectsVolume(float soundEffectsVolume) {
        this.soundEffectsVolume = soundEffectsVolume;
    }

    public void dispose() {
        assetManager.dispose();
    }
}
