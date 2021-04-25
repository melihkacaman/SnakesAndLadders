package sceenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import com.melihkacaman.snakesandladders.HelpersMethods;
import helpers.GameInfo;
import huds.PlayerInfoHuds;

import java.awt.*;

public class PlayerInfo implements Screen {
    private GameMain gameMain;
    private OrthographicCamera camera;
    private Viewport viewport;
    private PlayerInfoHuds playerInfoHuds;

    private Texture background;
    private Label userName;
    public PlayerInfo(GameMain gameMain) {
        this.gameMain = gameMain;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);

        background = new Texture("Backgrounds/Wait Screen.png");
        playerInfoHuds =  new PlayerInfoHuds(gameMain);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        HelpersMethods.clearScreen();

        gameMain.getBatch().begin();
        gameMain.getBatch().draw(background, 0, 0);
        gameMain.getBatch().end();

        gameMain.getBatch().setProjectionMatrix(playerInfoHuds.getStage().getCamera().combined);
        playerInfoHuds.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
