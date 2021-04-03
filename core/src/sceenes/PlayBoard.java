package sceenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import com.melihkacaman.snakesandladders.HelpersMethods;
import helpers.GameInfo;
import huds.GameBoardButtons;

public class PlayBoard implements Screen {
    private GameMain game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture bg;
    private GameBoardButtons buttons;

    public PlayBoard(GameMain game) {
        this.game = game;

        camera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);

        bg = new Texture("Backgrounds/Play Board.png");
        buttons = new GameBoardButtons(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //HelpersMethods.clearScreen();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(bg, 0,0);
        game.getBatch().end();

        game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
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
    }
}
