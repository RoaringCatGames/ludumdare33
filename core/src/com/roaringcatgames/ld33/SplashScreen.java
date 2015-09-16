package com.roaringcatgames.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by barry on 8/24/15 @ 8:55 PM.
 */
public class SplashScreen extends ScreenAdapter {

    OrthographicCamera cam;
    SpriteBatch batch;

    public SplashScreen(SpriteBatch batch){
        this.batch = batch;
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        cam.update();
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();
        batch.draw(Assets.splashScreen, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();


    }
}
