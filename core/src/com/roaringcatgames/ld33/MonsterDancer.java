package com.roaringcatgames.ld33;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MonsterDancer extends Game {
	public SpriteBatch batch;
	public boolean is2Player = false;

	boolean isLoaded = false;
	AssetManager am;
	@Override
	public void create () {
		batch = new SpriteBatch();

		am = Assets.load();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!isLoaded && am.update()){
			isLoaded = true;
			setScreen(new FullMenuScreen(this));
		}

		super.render();
	}
}
