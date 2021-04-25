package huds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.GameManager;
import helpers.ImageButtonGenerator;
import helpers.GameInfo;
import sceenes.PlayBoard;
import sceenes.PlayerInfo;

public class MainMenuButtons {
    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private ImageButton playBtn, optionsBtn, quitBtn, musicBtn;

    public MainMenuButtons(GameMain game) {
        this.game = game;

        viewport =  new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);
        checkTheMusic();

        createAndPositionButtons();
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayerInfo(game));
            }
        });

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // options screen
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // quit
            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (GameManager.getInstance().isMusicOn){
                    GameManager.getInstance().isMusicOn = false;
                    GameManager.getInstance().stopMusic();
                }else {
                    GameManager.getInstance().isMusicOn = true;
                    GameManager.getInstance().playMusic();
                }
            }
        });
    }

    void checkTheMusic(){
        if (GameManager.getInstance().isMusicOn){
            GameManager.getInstance().playMusic();
        }
    }

    private void createAndPositionButtons() {
        playBtn = new ImageButtonGenerator("Buttons/Menu/Start Game.png");
        optionsBtn = new ImageButtonGenerator("Buttons/Menu/Options.png");
        quitBtn = new ImageButtonGenerator("Buttons/Menu/Quit.png");
        musicBtn = new ImageButtonGenerator("Buttons/Menu/Music On.png");

        playBtn.setPosition(GameInfo.WIDTH / 2f - 100 , GameInfo.HEIGHT / 2f - 70, Align.center);
        optionsBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f - 156, Align.center);
        quitBtn.setPosition(GameInfo.WIDTH / 2f + 100, GameInfo.HEIGHT / 2f - 244 , Align.center);
        musicBtn.setPosition(GameInfo.WIDTH - 13, 13, Align.bottomRight);

        stage.addActor(playBtn);
        stage.addActor(optionsBtn);
        stage.addActor(quitBtn);
        stage.addActor(musicBtn);
    }

    public Stage getStage(){
        return stage;
    }


} // MainMenuButtons
