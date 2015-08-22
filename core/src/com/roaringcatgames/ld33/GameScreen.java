package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.roaringcatgames.ld33.components.*;
import com.roaringcatgames.ld33.systems.*;

/**
 * Created by barry on 8/22/15 @ 12:36 AM.
 */
public class GameScreen extends ScreenAdapter {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_SONG_END = 3;
    static final int GAME_OVER = 4;



    MonsterDancer game;
    PooledEngine engine;
    ComponentFactory componentFactory;

    Entity player1;
    Entity player2;
    Music currentSong;

    private int state;

    public GameScreen(MonsterDancer game){
        super();
        this.game = game;

        engine = new PooledEngine();

        engine.addSystem(new PulsingSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new DebugRenderer(game.batch));

        componentFactory = new ComponentFactory(engine);

        createPlayers();
        OrthographicCamera cam = engine.getSystem(RenderingSystem.class).getCamera();
        Entity e = engine.createEntity();
        float width = cam.viewportWidth;
        float height = cam.viewportHeight;
        TransformComponent tc = componentFactory.createTransformComponent(width/2f, height/2f, 1f, 1f, 0f);

        BoundsComponent bc = componentFactory.createBoundsComponent(0f, 0f, cam.viewportWidth, cam.viewportHeight);
        e.add(tc);
        e.add(bc);
        engine.addEntity(e);

        state = GAME_READY;
    }

    private void createPlayers() {
        float p1x = PlayerComponent.WIDTH_M/2f, p1y = PlayerComponent.HEIGHT_M/2f, p1sx = 1f, p1sy = 1f, p1r = 0f, p1frameTime = 0.08f;
        float p2x = p1x + (1.5f*PlayerComponent.WIDTH_M), p2y = PlayerComponent.HEIGHT_M/2f, p2sx = 1f, p2sy = 1f, p2r = 0f, p2frameTime = 0.16f;

        player1 = buildPlayerEntity(p1x, p1y, p1sx, p1sy, p1r, p1frameTime);
        engine.addEntity(player1);

        player2 = buildPlayerEntity(p2x, p2y, p2sx, p2sy, p2r, p2frameTime);
        engine.addEntity(player2);



    }

    private Entity buildPlayerEntity(float x, float y, float scaleX, float scaleY, float rotation, float frameTime) {
        Entity e = engine.createEntity();
        TextureComponent textureComponent = componentFactory.createTextureComponent();
        TransformComponent transform = componentFactory.createTransformComponent(x, y, scaleX, scaleY, rotation);

        AnimationComponent aniComp = componentFactory.createAnimationComponent();
        Animation ani = new Animation(frameTime, Assets.getPlayerFrames(States.DEFAULT));
        aniComp.animations.put(States.DEFAULT, ani);
        StateComponent p1State = componentFactory.createStateComponent(States.DEFAULT, true);

        MovementComponent move = componentFactory.createMovementComponent(0f, 0f, 0f, 0f);
        BoundsComponent bounds = componentFactory.createBoundsComponent(x, y, PlayerComponent.WIDTH_M, PlayerComponent.HEIGHT_M);

        e.add(bounds);
        e.add(move);
        e.add(textureComponent);
        e.add(transform);
        e.add(aniComp);
        e.add(p1State);

        return e;
    }

    public void update(float deltaTime){
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        engine.update(deltaTime);

        switch(state){
            case GAME_READY:
                updateReady(deltaTime);
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused(deltaTime);
                break;
            case GAME_SONG_END:
                updateSongOver(deltaTime);
                break;
            case GAME_OVER:
                updateGameOver(deltaTime);
                break;
            default:
                break;
        }
    }

    private void updateReady(float deltaTime) {
        if(Gdx.input.justTouched() && currentSong == null){
            currentSong = Assets.getSong1();
            currentSong.setLooping(false);
            currentSong.play();
            state = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime){

    }

    private void updatePaused(float deltaTime){

    }

    private void updateSongOver(float deltaTime){

    }

    private void updateGameOver(float deltaTime){

    }

    @Override
    public void render(float delta) {
        update(delta);
    }


}
