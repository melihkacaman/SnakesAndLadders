package sceenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.entity.CustomVector;
import com.melihkacaman.entity.PairMovement;
import com.melihkacaman.snakesandladders.GameMain;
import com.melihkacaman.snakesandladders.HelpersMethods;

import client.ClientManager;
import helpers.GameInfo;
import helpers.JSONMapObject;
import huds.GameBoardButtons;
import model.Couple;
import movement.Movement;
import player.Player;
import player.PlayerCharacter;
import stuff.FinishPoint;
import stuff.Ladder;
import stuff.Snake;
import stuff.Stuff;

import java.util.ArrayList;
import java.util.List;

public class PlayBoard implements Screen {
    private GameMain game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture bg;
    private GameBoardButtons buttons;
    private ArrayList<Stuff> stuffs;

    // players
    private Couple couple;
    private Player playerSelf;
    private Player playerPair;
    List<Player> players;
    private boolean backward = false;
    private ClientManager clientManager;

    private boolean stopMovement = false;

    public void setMovement(Player player, int dice, Vector2 target) {
        this.movement = new Movement(player, dice, target);
        if (couple.getSelfId() == player.getId()){   // if it's me, send the data of movement
            clientManager.sendMovement(new PairMovement(player.getId(), dice, new CustomVector(target.x, target.y)));
        }
    }

    private Movement movement;

    private World world;

    public PlayBoard(GameMain game, Couple couple, ClientManager clientManager) {
        this.game = game;
        this.players = new ArrayList<>();
        this.stuffs = new ArrayList<>();
        this.couple = couple;
        this.clientManager = clientManager;

        fillStuffs(game.getDefaultMap());

        camera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);

        world = new World(new Vector2(0,0), true);

        bg = new Texture("Backgrounds/Play Board.png"); // message

        playerSelf = new Player(couple.getSelfUserName(), world, game, GameInfo.XSTARTINGPOINT,GameInfo.YSTARTINGPOINT,
                couple.getSelfCharacter(),couple.getSelfId());
        playerPair = new Player(couple.getPairUserName(), world, game, GameInfo.XSTARTINGPOINT, GameInfo.YSTARTINGPOINT,
                couple.getPairCharacter(), couple.getPair().getId());

        fillPlayers();

        buttons = new GameBoardButtons(game, players, this,couple, clientManager);
    }

    private void fillPlayers(){
        if (playerSelf.getCharacter() == PlayerCharacter.REDBIRD){
            players.add(playerSelf);
            players.add(playerPair);
        }else {
            players.add(playerPair);
            players.add(playerSelf);
        }
    }

    private void fillStuffs(ArrayList<JSONMapObject> defaultMap) {
        for (JSONMapObject obj: defaultMap) {
            if (obj.getType().equals("Ladder")){
                this.stuffs.add(new Ladder(obj.getId(), obj.getFirstValue(), obj.getSecondValue()));
            }else if(obj.getType().equals("Snake")){
                this.stuffs.add(new Snake(obj.getId(), obj.getFirstValue(), obj.getSecondValue()));
            }else if(obj.getType().equals("FinishPoint")){
                this.stuffs.add(new FinishPoint(obj.getId(), obj.getFirstValue(), obj.getSecondValue()));
            }
        }
    }

    @Override
    public void show() {

    }

    void move(){
        if (backward){
            moveBackward();
        }else {
            for (Player player: this.players) {
                if (player.turn){
                    if (movement.getTarget().dst2(player.getX(), player.getY()) < 5){
                        player.setPosition(player.getX(), player.getY());
                        player.turn = false;
                        buttons.setDiceButtonTouchable(true);
                        movement = null;

                        //checkLocationForStuff(player.getCurrentLocation(), player);
                    }else if (player.getX() > GameInfo.WIDTH - 20){
                        player.setPosition(3, player.getY() + 55);
                    }else {
                        player.translateX(2);
                    }
                }
            }
        }
    }

    private void checkLocationForStuff(int location, Player player){
        for (Stuff stuff : stuffs){
            if (stuff.getBeginCell() == location){
                buttons.setDiceButtonDisabled();
                player.turn = true;
                if (stuff instanceof Ladder){
                    int abstractDice = stuff.getEndCell() - location;     //// Todo: use ladder class
                    Vector2 abstractTarget = player.getTargetForward(abstractDice);
                    movement = new Movement(player, abstractDice, abstractTarget);
                }else if (stuff instanceof Snake){
                    int abstractDice = stuff.getStepsNumber();
                    Vector2 abstractTarget = player.getTargetBackward(abstractDice);
                    movement = new Movement(player, abstractDice, abstractTarget);
                    backward = true;
                }else if (stuff instanceof FinishPoint){
                    buttons.createEndPanel(player);
                }
            }
        }
    }

    private void moveBackward(){
        if (movement.getTarget().dst2(movement.getPlayer().getX(), movement.getPlayer().getY()) < 5){
            movement.getPlayer().setPosition(movement.getPlayer().getX(), movement.getPlayer().getY());
            movement.getPlayer().turn = false;
            backward = false;
            buttons.setDiceButtonTouchable(true);
        }else if (movement.getPlayer().getX() < 20){
            movement.getPlayer().setPosition(GameInfo.WIDTH - 3f, movement.getPlayer().getY() - 55);
        }else {
            movement.getPlayer().translateX(-4);
        }
    }


    private void checkOutFinishing(){
        for (Player p : players){
            if (p.getY() > 655 && p.getX() > GameInfo.WIDTH - 20){
                stopMovement = true;
                buttons.createEndPanel(p);
            }
        }
    }

    @Override
    public void render(float delta) {
        HelpersMethods.clearScreen();


        game.getBatch().begin();

        game.getBatch().draw(bg, 0,0);



        game.getBatch().draw(playerSelf, playerSelf.getX(), playerSelf.getY());
        game.getBatch().draw(playerPair, playerPair.getX(), playerPair.getY());
        if (!stopMovement){
            move();
            buttons.updateTopLabels();
        }

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();

        world.step(Gdx.graphics.getDeltaTime(),6,2);

        if (clientManager.activeMovement != null){
            for (Player p : players) {
                if (p.getId() == clientManager.activeMovement.getId()){
                    movement = new Movement(p, clientManager.activeMovement.getDice(),
                            p.getTargetForward(clientManager.activeMovement.getDice()));

                    p.turn = true;
                    buttons.setDiceButtonTouchable(false);
                    buttons.increaseTurnCount();
                    clientManager.activeMovement = null;
                    break;
                }
            }
        }


        checkOutFinishing();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        buttons.getStage().dispose();
        world.dispose();
    }

    public List<Player> getPlayers() {
        return players;
    }
}
