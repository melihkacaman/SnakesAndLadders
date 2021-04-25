package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.DefaultFontGenerator;
import helpers.GameInfo;
import helpers.ImageButtonGenerator;
import player.Player;

public class PlayerInfoHuds {
    private GameMain gameMain;
    private Stage stage;
    private Viewport viewport;
    private DefaultFontGenerator defaultFontGenerator;

    private ImageButton readyBtn;
    private TextField userNameTxt;
    private Label userNameInfo;

    public PlayerInfoHuds(GameMain gameMain) {
        this.gameMain = gameMain;
        defaultFontGenerator = new DefaultFontGenerator(40);

        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionComponents();
        createListeners();
    }

    private void createListeners() {
        readyBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String userName = userNameTxt.getText();
                if(!userName.isEmpty()){
                    // Connect server
                    // save this user

                }
            }
        });
    }

    void createAndPositionComponents() {
        userNameInfo = defaultFontGenerator.getNewLabel("Give your name", Color.valueOf("#fccd37"));
        readyBtn = new ImageButtonGenerator("Buttons/Ready.png");
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = defaultFontGenerator.getFont();
        style.fontColor = Color.BLUE;
        style.background = new Image(new Texture("Backgrounds/Text BG.png")).getDrawable();
        userNameTxt = new TextField("", style);

        userNameInfo.setPosition(GameInfo.WIDTH /2f - 200, GameInfo.HEIGHT/2f + 70);

        readyBtn.setPosition(GameInfo.WIDTH /2f, GameInfo.HEIGHT/2f - 70);

        userNameTxt.setPosition(20, GameInfo.HEIGHT/2f);
        userNameTxt.setSize(GameInfo.WIDTH - 40, 50);


        stage.addActor(userNameInfo);
        stage.addActor(readyBtn);
        stage.addActor(userNameTxt);
    }


    public Stage getStage(){
        return this.stage;
    }
}