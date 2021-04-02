package sceenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.GameInfo;
import huds.MainMenuButtons;

public class MainMenu implements Screen {
    private GameMain gameMain;
    private OrthographicCamera mainCamera;
    private Viewport viewport;

    private Texture background;
    private MainMenuButtons buttons;


    public MainMenu(GameMain gameMain) {
        this.gameMain = gameMain;

        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);   // /2f unutma
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);

        background = new Texture("Backgrounds/Menu BG.png");

        buttons = new MainMenuButtons(gameMain);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameMain.getBatch().begin();
        gameMain.getBatch().draw(background, 0 , 0);
        gameMain.getBatch().end();

        gameMain.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
        buttons.getStage().draw();
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
