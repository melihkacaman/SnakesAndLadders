package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameManager {
    private static GameManager ourInstance = new GameManager();
    private Music bgMusic;
    public boolean isMusicOn = true;

    private GameManager(){
    }

    public void playMusic(){
        if (bgMusic == null){
            bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/BG Music.mp3"));
        }
        if (!bgMusic.isPlaying()){
            bgMusic.play();
        }
    }

    public void stopMusic(){
        if (bgMusic == null){
            bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/BG Music.mp3"));
        }
        if (bgMusic.isPlaying()){
            bgMusic.stop();
            bgMusic.dispose();
        }
    }

    public static GameManager getInstance(){
        return ourInstance;
    }
}
