package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.GameInfo;
import helpers.ImageButtonGenerator;
import movement.Movement;
import player.Player;
import player.PlayerController;
import sceenes.PlayBoard;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GameBoardButtons {
    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private Random random;
    private PlayerController playerController;
    private List<Player> players;

    private ImageButton pauseBtn;
    private ImageButton diceBtn;
    private Image diceValue;

    private int turnCount = 0;

    PlayBoard playBoard;
    public GameBoardButtons(GameMain game, List<Player> players, PlayBoard playBoard) {
        this.game = game;
        this.players = players;
        this.playBoard = playBoard;

        random = new Random();
        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());
        playerController = new PlayerController();

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

        diceBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final int dice = throwDice();
                System.out.println("Dices/Dice " + dice + ".png");

                diceValue.setDrawable(new SpriteDrawable(new Sprite(new Texture("Dices/Dice " + dice + ".png"))));
                diceValue.setVisible(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        diceValue.setVisible(false);
                    }
                }, 1);

                Vector2 target = players.get(turnCount % 2).getTarget(dice);
                players.get(turnCount % 2).turn = true;
                playBoard.setMovement(players.get(turnCount % 2), dice, target);

                turnCount++;
                diceBtn.setTouchable(Touchable.disabled);
            }
        });
    }

    public void setDiceButtonTouchable(){
        diceBtn.setTouchable(Touchable.enabled);
    }
    public void setDiceButtonDisabled(){diceBtn.setTouchable(Touchable.disabled);}
    private void createAndPositionButtons() {
        pauseBtn = new ImageButtonGenerator("Buttons/Game/Pause.png");
        diceBtn = new ImageButtonGenerator("Dices/Dice.png");
        diceValue = new Image(new Texture("Dices/Dice 1.png"));

        pauseBtn.setPosition(13,20,Align.bottomLeft);
        diceBtn.setPosition(GameInfo.WIDTH / 2f, diceBtn.getWidth() / 2f + 35 , Align.center);
        diceValue.setPosition(GameInfo.WIDTH / 2f, diceBtn.getWidth() / 2f + 35 , Align.center);

        diceValue.setVisible(false);

        stage.addActor(pauseBtn);
        stage.addActor(diceBtn);
        stage.addActor(diceValue);
    }

    public Stage getStage(){
        return stage;
    }

    private int throwDice(){
        return this.random.nextInt(6) + 1;
    }
}
