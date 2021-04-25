package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DefaultFontGenerator {
    private FreeTypeFontGenerator freeTypeFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameters;
    private BitmapFont font;
    public DefaultFontGenerator(int fontSize) {
        this.freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/blow.ttf"));
        this.parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = fontSize;
        font = freeTypeFontGenerator.generateFont(parameters);
    }

    public Label getNewLabel(String text, Color color){
        return new Label(text, new Label.LabelStyle(font,color));
    }

    public BitmapFont getFont(){
        return this.font;
    }
}
