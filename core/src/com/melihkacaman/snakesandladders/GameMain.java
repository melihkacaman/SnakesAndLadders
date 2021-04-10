package com.melihkacaman.snakesandladders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import helpers.JSONMapGenerator;
import helpers.JSONMapObject;
import sceenes.MainMenu;

import java.util.ArrayList;

public class GameMain extends Game {
	private SpriteBatch batch;
	private ArrayList<JSONMapObject> defaultMap;
	private JSONMapGenerator jsonMapGenerator;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		jsonMapGenerator = new JSONMapGenerator();
		defaultMap = jsonMapGenerator.getDefaultMap();

		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}

	public ArrayList<JSONMapObject> getDefaultMap(){
		return defaultMap;
	}
}
