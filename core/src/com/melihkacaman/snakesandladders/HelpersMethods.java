package com.melihkacaman.snakesandladders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class HelpersMethods {

    public static void clearScreen(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
