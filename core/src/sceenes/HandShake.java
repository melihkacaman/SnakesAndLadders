package sceenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import com.melihkacaman.snakesandladders.HelpersMethods;

import client.ClientManager;
import helpers.DefaultFontGenerator;
import helpers.GameInfo;
import helpers.ImageButtonGenerator;
import model.Couple;
import player.PlayerCharacter;

public class HandShake implements Screen {
    private GameMain gameMain;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Couple couple;
    private ClientManager clientManager;

    private Texture background;
    private BitmapFont font;
    private Stage stage;
    private ImageButton letsBegin;

    DefaultFontGenerator defaultFontGenerator;

    public HandShake(GameMain gameMain, Couple couple, ClientManager clientManager) {
        this.gameMain = gameMain;
        this.couple = couple;
        this.clientManager = clientManager;

        defaultFontGenerator = new DefaultFontGenerator(45);
        font = defaultFontGenerator.getFont();
        font.setColor(DefaultFontGenerator.getDefaultColor());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        camera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);
        stage = new Stage(viewport,gameMain.getBatch());
        Gdx.input.setInputProcessor(stage);

        background = new Texture("Backgrounds/Hand Shake.png");

        createButton();
    }

    private void createButton() {
        letsBegin = new ImageButtonGenerator("Buttons/Lets Begin.png");
        letsBegin.setSize(letsBegin.getWidth() + 150, letsBegin.getHeight());
        letsBegin.setPosition(GameInfo.WIDTH /2f - letsBegin.getWidth() / 2f, GameInfo.HEIGHT / 2f - 200);

        stage.addActor(letsBegin);

        letsBegin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean res = clientManager.sendStartSignal();
                if (res){
                    gameMain.setScreen(new PlayBoard(gameMain, couple, clientManager));
                }else {
                    // ToDo : back to main menu
                }
            }
        });


    }

    @Override
    public void show() {

    }

    private void drawNames(){
        if (couple.getSelfCharacter() == PlayerCharacter.REDBIRD){
            font.draw(gameMain.getBatch(), couple.getSelfUserName(), GameInfo.WIDTH / 2f - 200, GameInfo.HEIGHT / 2f+15);
            font.draw(gameMain.getBatch(), couple.getPairUserName(), GameInfo.WIDTH / 2f + 50, GameInfo.HEIGHT / 2f+15);
        }else {
            font.draw(gameMain.getBatch(), couple.getSelfUserName(), GameInfo.WIDTH / 2f + 50, GameInfo.HEIGHT / 2f + 15);
            font.draw(gameMain.getBatch(), couple.getPairUserName(), GameInfo.WIDTH / 2f - 200, GameInfo.HEIGHT / 2f + 15);
        }
    }

    @Override
    public void render(float delta) {
        HelpersMethods.clearScreen();

        gameMain.getBatch().begin();
        gameMain.getBatch().draw(background, 0, 0);
        drawNames();
        letsBegin.draw(gameMain.getBatch(), 100);
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
