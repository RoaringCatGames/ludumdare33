package com.roaringcatgames.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by barry on 8/22/15 @ 1:53 PM.
 */
public class UNUSEDMenuScreen extends ScreenAdapter{
    MonsterDancer game;

    OrthographicCamera guiCam;
    Vector3 touchPoint = new Vector3();

    Rectangle playerSelectArea = new Rectangle(1060f, 90f, 106f, 107f);
    Rectangle playButtonArea = new Rectangle(400f, 90f, 400f, 200f);

    public UNUSEDMenuScreen(MonsterDancer game){
        this.game = game;
        guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiCam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        Assets.getIntroMusic().play();
    }

    public void update(){
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(playerSelectArea.contains(touchPoint.x, touchPoint.y)){
                game.is2Player = !game.is2Player;
            }

            if(playButtonArea.contains(touchPoint.x, touchPoint.y)) {
                if (Assets.getIntroMusic().isPlaying()) {
                    Assets.getIntroMusic().stop();
                }
                game.setScreen(new GameScreen(game));
            }
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Assets.getTVFrame(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float scaleY = game.is2Player ? -1f : 1f;
        game.batch.draw(Assets.getSelectKnob(), 1060f, 90f, 53f, 53f, 106f, 107f, 1f, scaleY, 0f);
        game.batch.end();

//        game.batch.enableBlending();
//        game.batch.begin();
//        game.batch.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
//        game.batch.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
//        game.batch.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
//        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
