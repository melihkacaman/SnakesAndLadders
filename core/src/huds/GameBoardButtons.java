package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;

import client.ClientManager;
import helpers.DefaultFontGenerator;
import helpers.GameInfo;
import helpers.GameManager;
import helpers.ImageButtonGenerator;
import model.Couple;
import movement.Movement;
import player.Player;
import player.PlayerCharacter;
import player.PlayerController;
import sceenes.MainMenu;
import sceenes.PlayBoard;

import java.util.List;
import java.util.Random;

public class GameBoardButtons {
    private GameMain game;
    private Stage stage;
    private Viewport viewport;
    private DefaultFontGenerator defaultFontGenerator;

    private Random random;
    private PlayerController playerController;
    private List<Player> players;
    private Couple couple;
    private ClientManager clientManager;

    private ImageButton pauseBtn, diceBtn;
    private Image diceValue;

    private Image endPanel;
    private ImageButton quitBtn;
    private Label endLabel;

    public Sound diceSound;

    private int turnCount = 0;

    private Label redName, redLocation, blueName, blueLocation;

    
    PlayBoard playBoard;
    public GameBoardButtons(GameMain game, List<Player> players, PlayBoard playBoard, Couple couple, ClientManager clientManager) {
        this.game = game;
        this.players = players;
        this.playBoard = playBoard;
        this.defaultFontGenerator = new DefaultFontGenerator(35);
        this.couple = couple;
        this.clientManager = clientManager;   // Todo: will be deleted

        random = new Random();
        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());
        playerController = new PlayerController();

        Gdx.input.setInputProcessor(stage);

        createAndPositionLabels();
        createAndPositionButtons();
        addListenersToButtons();

        diceSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Dice Sound.wav"));

        players.get(0).diceTurn = true;
    }

    public void createEndPanel(Player winnerUser){
        endPanel = new Image(new Texture("End Panel/End Panel.png"));
        endLabel = defaultFontGenerator.getNewLabel(winnerUser.getName() + " won!", Color.BLUE);
        quitBtn = new ImageButtonGenerator("End Panel/Quit 2.png");

        endPanel.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, Align.center);
        endLabel.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT /2f + 50, Align.center);
        quitBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f - 80, Align.center);

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                //// Todo: Sclient remove
            }
        });

         stage.addActor(endPanel);
         stage.addActor(endLabel);
         stage.addActor(quitBtn);

         diceBtn.remove();
         diceValue.remove();

         Image winner = new Image(winnerUser.getTexture());
         winner.setPosition(GameInfo.WIDTH / 2f - 20 , GameInfo.HEIGHT /2f - 25, Align.center);
         winner.setHeight(90);
         winner.setWidth(90);
         winnerUser.setPosition(GameInfo.WIDTH + 100 , GameInfo.HEIGHT + 100);

         stage.addActor(winner);
    }

    private void createAndPositionLabels() {
        for (Player p: players) {
            if (p.getCharacter() == PlayerCharacter.REDBIRD){
                redName = defaultFontGenerator.getNewLabel(p.getName(), Color.valueOf("#ec1c24"));
                redLocation = defaultFontGenerator.getNewLabel(""+p.getCurrentLocation(), Color.BLACK);
                Table redTable = new Table();
                redTable.top().left();
                redTable.setFillParent(true);

                redTable.add(redName).padLeft(90).padTop(15);
                redTable.row();
                redTable.add(redLocation).padLeft(90).padTop(25);

                stage.addActor(redTable);
            }else if(p.getCharacter() == PlayerCharacter.BLUEBIRD){
                blueName = defaultFontGenerator.getNewLabel(p.getName(), Color.valueOf("#5bc9e1"));
                blueLocation = defaultFontGenerator.getNewLabel(""+p.getCurrentLocation(), Color.BLACK);

                Table blueTable = new Table();
                blueTable.top().right();
                blueTable.setFillParent(true);

                blueTable.add(blueName).padRight(90).padTop(15);
                blueTable.row();
                blueTable.add(blueLocation).padRight(90).padTop(25);

                stage.addActor(blueTable);
            }
        }
    }

    public void updateTopLabels(){
        for (Player p : players) {
            if (p.getCharacter() == PlayerCharacter.REDBIRD){
                redLocation.setText(""+p.getCurrentLocation());
            }else if(p.getCharacter() == PlayerCharacter.BLUEBIRD){
                blueLocation.setText(""+p.getCurrentLocation());
            }
        }
    }

    private void addListenersToButtons() {
        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Todo: Pause the game
            }
        });

        diceBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (couple.getSelfId() == players.get(turnCount % 2).getId()) {
                    diceSound.play();
                    final int dice = throwDice();
                    getMove(dice);
                }
            }
        });
    }

    private void getMove(int dice){
        diceValue.setDrawable(new SpriteDrawable(new Sprite(new Texture("Dices/Dice " + dice + ".png"))));
        diceValue.setVisible(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                diceValue.setVisible(false);
            }
        }, 1);

        Player player = players.get(turnCount % 2);

        Vector2 target = player.getTargetForward(dice);
        player.turn = true;
        playBoard.setMovement(player, dice, target);

        turnCount++;
        diceBtn.setTouchable(Touchable.disabled);
    }

    public void setDiceButtonTouchable(boolean state){
        diceBtn.setTouchable(state ? Touchable.enabled : Touchable.disabled);
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

    public int getTurnCount() {
        return turnCount;
    }

    public void increaseTurnCount() {
        this.turnCount++;
    }
}
