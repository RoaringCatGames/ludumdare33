package com.roaringcatgames.ld33;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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

		Graphics.DisplayMode dm = Gdx.graphics.getDesktopDisplayMode();
		Gdx.app.log("DISPLAY", "W: " + dm.width + " H: " + dm.height + " X: " + dm.bitsPerPixel);
		Gdx.graphics.setDisplayMode(dm.width, dm.height, true);
		Gdx.graphics.setVSync(true);
		Gdx.input.setCursorCatched(false);

		batch = new SpriteBatch();

		Assets.loadSplash();
		am = Assets.load();
		setScreen(new SplashScreen(batch));

	}

	@Override
	public void render () {
		float r = 0/255f;
		float g = 24f/255f;
		float b = 72f/255f;
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!isLoaded && am.update()){
			isLoaded = true;
			setScreen(new FullMenuScreen(this));
		}

		super.render();
	}
}
