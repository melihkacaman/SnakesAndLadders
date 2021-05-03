package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameManager {
    private static GameManager ourInstance = new GameManager();
    private Music bgMusic;
    public boolean isMusicOn = true;
    private Sound buttonSound;
    private Sound snakeSound;
    private Sound ladderSound;
    private Sound OKSound;
    private Sound WinSound;

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

    public void playBtnSound(){
        if (buttonSound == null){
            buttonSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Button Sound.wav"));
        }

        buttonSound.play();
    }

    public void playSnakeSound(){
        if (snakeSound == null){
            snakeSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Snake Sound.wav"));
        }

        snakeSound.play();
    }

    public void playOKSound(){
        if (OKSound == null){
            OKSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/menu-ok.wav"));
        }

        OKSound.play();
    }

    public void playWinSound(){
        if (WinSound == null){
            WinSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Win Sound.wav"));
        }

        WinSound.play();
    }

    public void playLadderSound(){
        if (ladderSound == null){
            ladderSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Ladder Sound.ogg"));
        }

        ladderSound.play();
    }

    public static GameManager getInstance(){
        return ourInstance;
    }
}
