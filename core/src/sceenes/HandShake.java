package sceenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import com.melihkacaman.snakesandladders.HelpersMethods;

import helpers.GameInfo;
import huds.PlayerInfoHuds;

public class HandShake implements Screen {
    private GameMain gameMain;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture background;

    public HandShake(GameMain gameMain) {
        this.gameMain = gameMain;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        HelpersMethods.clearScreen();

        gameMain.getBatch().begin();
        gameMain.getBatch().end();
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
