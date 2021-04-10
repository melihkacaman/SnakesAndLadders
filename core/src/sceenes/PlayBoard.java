package sceenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import com.melihkacaman.snakesandladders.HelpersMethods;
import helpers.GameInfo;
import helpers.JSONMapObject;
import huds.GameBoardButtons;
import movement.Movement;
import player.Player;
import player.PlayerCharacter;
import stuff.Ladder;
import stuff.Snake;
import stuff.Stuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PlayBoard implements Screen {
    private GameMain game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture bg;
    private GameBoardButtons buttons;
    private ArrayList<Stuff> stuffs;

    // players
    private Player player1;
    private Player player2;
    List<Player> players;

    public void setMovement(Player player, int dice, Vector2 target) {
        this.movement = new Movement(player, dice, target);
    }

    private Movement movement;

    private World world;

    public PlayBoard(GameMain game) {
        this.game = game;
        this.players = new ArrayList<>();
        this.stuffs = new ArrayList<>();

        fillStuffs(game.getDefaultMap());

        camera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);

        world = new World(new Vector2(0,0), true);

        bg = new Texture("Backgrounds/Play Board.png"); // message

        player1 = new Player("Melih", world, game, 25,188, PlayerCharacter.REDBIRD);
        player2 = new Player("Yusuf", world, game, 25, 188, PlayerCharacter.BLUEBIRD);
        players.add(player1);
        players.add(player2);

        buttons = new GameBoardButtons(game, players, this);
    }

    private void fillStuffs(ArrayList<JSONMapObject> defaultMap) {
        for (JSONMapObject obj: defaultMap) {
            if (obj.getType().equals("Ladder")){
                this.stuffs.add(new Ladder(obj.getId(), obj.getFirstValue(), obj.getSecondValue()));
            }else if(obj.getType().equals("Snake")){
                this.stuffs.add(new Snake(obj.getId(), obj.getFirstValue(), obj.getSecondValue()));
            }
        }
    }

    @Override
    public void show() {

    }

    void move(){
        for (Player player: this.players) {
            if (player.turn){
                if (movement.getTarget().dst2(player.getX(), player.getY()) < 5){
                    player.setPosition(player.getX(), player.getY());
                    player.turn = false;
                    buttons.setDiceButtonTouchable();
                    movement = null;

                    checkLocationForStuff(player.getCurrentLocation(), player);
                }else if (player.getX() > GameInfo.WIDTH - 20){
                    player.setPosition(3, player.getY() + 55);
                }else {
                    player.translateX(1);
                }
            }
        }
    }

    private void checkLocationForStuff(int location, Player player){
        for (Stuff stuff : stuffs){
            if (stuff.getBeginCell() == location){
                if (stuff instanceof Ladder){
                    int abstractDice = stuff.getEndCell() - location;
                    player.turn = true;
                    buttons.setDiceButtonDisabled();
                    Vector2 abstractTarget = player.getTarget(abstractDice);
                    movement = new Movement(player, abstractDice, abstractTarget);
                }else if (stuff instanceof Snake){
                    // going backward
                }
            }
        }
    }


    @Override
    public void render(float delta) {
        HelpersMethods.clearScreen();


        game.getBatch().begin();

        game.getBatch().draw(bg, 0,0);



        game.getBatch().draw(player1, player1.getX(), player1.getY());
        game.getBatch().draw(player2, player2.getX(), player2.getY());
        move();


        game.getBatch().end();

        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();

        world.step(Gdx.graphics.getDeltaTime(),6,2);
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
