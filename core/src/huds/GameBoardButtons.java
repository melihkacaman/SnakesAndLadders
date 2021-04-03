package huds;

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
import helpers.GameInfo;
import helpers.ImageButtonGenerator;

public class GameBoardButtons {
    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private ImageButton pauseBtn;

    public GameBoardButtons(GameMain game) {
        this.game = game;

        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
        addListenersToButtons();

    }

    private void addListenersToButtons() {
        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Pause the game
            }
        });
    }

    private void createAndPositionButtons() {
        pauseBtn = new ImageButtonGenerator("Buttons/Game/Pause.png");

        pauseBtn.setPosition(13,20,Align.bottomLeft);

        stage.addActor(pauseBtn);
    }

    public Stage getStage(){
        return stage;
    }
}
