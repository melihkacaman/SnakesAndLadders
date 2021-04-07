package sceenes;

import com.badlogic.gdx.Gdx;
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
import huds.GameBoardButtons;
import player.Player;
import player.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PlayBoard implements Screen {
    private GameMain game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture bg;
    private GameBoardButtons buttons;

    // players
    private Player player;
    List<Player> players;

    private World world;

    public PlayBoard(GameMain game) {
        this.game = game;
        this.players = new ArrayList<Player>();

        camera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);

        world = new World(new Vector2(0,0), true);

        bg = new Texture("Backgrounds/Play Board.png");

        player = new Player("Melih", world, game, 25,188, PlayerCharacter.REDBIRD);
        players.add(player);

        buttons = new GameBoardButtons(game, players);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        HelpersMethods.clearScreen();


        game.getBatch().begin();
        game.getBatch().draw(bg, 0,0);
        player.drawPlayer(game.getBatch());
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
