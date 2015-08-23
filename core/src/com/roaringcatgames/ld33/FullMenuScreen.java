package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector3;
import com.roaringcatgames.ld33.components.*;
import com.roaringcatgames.ld33.systems.*;

/**
 * Created by barry on 8/22/15 @ 12:36 AM.
 */
public class FullMenuScreen extends ScreenAdapter {

    OrthographicCamera guiCam;
    Vector3 touchPoint = new Vector3();

    MonsterDancer game;
    PooledEngine engine;
    ComponentFactory componentFactory;

    Entity player1;
    Entity player2;
    Entity knob;

    public FullMenuScreen(MonsterDancer game){
        super();
        this.game = game;

        engine = new PooledEngine();

        engine.addSystem(new RotateToSystem());
        engine.addSystem(new PulsingSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new DebugRenderer(game.batch, engine, engine.getSystem(RenderingSystem.class).getCamera()));

        componentFactory = new ComponentFactory(engine);

        createButtons();
        createPlayers();
        createTVFrame();
    }

    private void createButtons(){
        //Knob
        knob = engine.createEntity();
        TextureComponent tc = componentFactory.createTextureComponent(Assets.getSelectKnob());
        knob.add(tc);
        //1060f, 90f, 106f, 107f
        TransformComponent tfc = componentFactory.createTransformComponent(34.8f, 4.8f, 1f, 1f, 0f);
        tfc.position.set(tfc.position.x, tfc.position.y, -1.1f);
        knob.add(tfc);
        StateComponent sc = componentFactory.createStateComponent(States.ONE_PLAYER);
        knob.add(sc);
        BoundsComponent bc = componentFactory.createBoundsComponent(tfc.position.x, tfc.position.y, 3.31f, 3.31f);
        knob.add(bc);
        RotateToComponent rc = componentFactory.createRotateToComponent(0f, 0f);
        knob.add(rc);
        engine.addEntity(knob);

        //SpaceBar
    }

    private void createPlayers() {

        float   p1x = (World.SCREEN.width/4f) + World.SCREEN.x,
                p1y = World.SCREEN.y + (PlayerComponent.HEIGHT_M/2f),
                p1sx = 1f,
                p1sy = 1f,
                p1r = 0f;

        player1 = buildPlayerEntity("P1", p1x, p1y, p1sx, p1sy, p1r);
        engine.addEntity(player1);


        float p2x = ((World.SCREEN.width / 4f) * 3f) + World.SCREEN.x,
                p2y = World.SCREEN.y + (PlayerComponent.HEIGHT_M / 2f),
                p2sx = -1f,
                p2sy = 1f,
                p2r = 0f;

        player2 = buildPlayerEntity("P2", p2x, p2y, p2sx, p2sy, p2r);
        engine.addEntity(player2);


        //MAYBE???
        Entity sweat = engine.createEntity();
        TextureComponent tc = componentFactory.createTextureComponent();
        sweat.add(tc);
        TransformComponent tfc = componentFactory.createTransformComponent(p1x - (PlayerComponent.WIDTH_M/2f), PlayerComponent.HEIGHT_M, 1f, 1f, 0f);
        sweat.add(tfc);
        StateComponent sc = componentFactory.createStateComponent(States.ON, true);
        sweat.add(sc);
        AnimationComponent ac = componentFactory.createAnimationComponent();
        ac.animations.put(States.ON, new Animation(0.08f, Assets.getSweatFrames()));
        sweat.add(ac);
        engine.addEntity(sweat);
    }

    private Entity buildPlayerEntity(String name, float x, float y, float scaleX, float scaleY, float rotation) {

        float bounceTime = 0.12f;
        float frameTime = 0.08f;
        Entity e = engine.createEntity();
        TextureComponent textureComponent = componentFactory.createTextureComponent();
        TransformComponent transform = componentFactory.createTransformComponent(x, y, scaleX, scaleY, rotation);

        boolean isP2 = name == "P2";
        AnimationComponent aniComp = componentFactory.createAnimationComponent();
        Animation ani = new Animation(bounceTime, Assets.getPlayerFrames(States.DEFAULT, isP2));
        Animation aniKick = new Animation(frameTime, Assets.getPlayerFrames(States.KICK, isP2), Animation.PlayMode.NORMAL);
        Animation aniPunch = new Animation(frameTime, Assets.getPlayerFrames(States.PUNCH, isP2), Animation.PlayMode.NORMAL);
        Animation aniTail = new Animation(frameTime, Assets.getPlayerFrames(States.TAIL, isP2), Animation.PlayMode.NORMAL);
        Animation aniFire = new Animation(frameTime, Assets.getPlayerFrames(States.FIRE, isP2), Animation.PlayMode.NORMAL);
        Animation aniWin = new Animation(frameTime, Assets.getPlayerFrames(States.WIN, isP2), Animation.PlayMode.LOOP);
        aniComp.animations.put(States.DEFAULT, ani);
        aniComp.animations.put(States.KICK, aniKick);
        aniComp.animations.put(States.PUNCH, aniPunch);
        aniComp.animations.put(States.TAIL, aniTail);
        aniComp.animations.put(States.FIRE, aniFire);
        aniComp.animations.put(States.WIN, aniWin);

        StateComponent p1State = componentFactory.createStateComponent(States.DEFAULT, true);

        MovementComponent move = componentFactory.createMovementComponent(0f, 0f, 0f, 0f);
        BoundsComponent bounds = componentFactory.createBoundsComponent(x, y, PlayerComponent.WIDTH_M, PlayerComponent.HEIGHT_M);
        PlayerComponent player = componentFactory.createPlayerComponent(name);

        e.add(player);
        e.add(bounds);
        e.add(move);
        e.add(textureComponent);
        e.add(transform);
        e.add(aniComp);
        e.add(p1State);

        return e;
    }

    public void createTVFrame(){
        Entity e = engine.createEntity();
        TextureComponent tc = componentFactory.createTextureComponent(Assets.getTVFrame());
        e.add(tc);
        TransformComponent tfc = componentFactory.createTransformComponent(World.CENTERX_M, World.CENTERY_M, 1f, 1f, 0f);
        tfc.position.set(tfc.position.x, tfc.position.y, -1f);
        e.add(tfc);
        engine.addEntity(e);
    }

    public void update(float deltaTime){
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        engine.update(deltaTime);

        if (Gdx.input.justTouched()) {
            engine.getSystem(RenderingSystem.class).getCamera().unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            BoundsComponent b = knob.getComponent(BoundsComponent.class);

            if(b.bounds.contains(touchPoint.x, touchPoint.y)){
                game.is2Player = !game.is2Player;
                RotateToComponent rc = knob.getComponent(RotateToComponent.class);
                rc.targetRotation = game.is2Player ? -180f : 0f;
                rc.rotationSpeed = game.is2Player ? -360f : 360f;
            }
//
//            if(playButtonArea.contains(touchPoint.x, touchPoint.y)) {
//                if (Assets.getIntroMusic().isPlaying()) {
//                    Assets.getIntroMusic().stop();
//                }
//                game.setScreen(new GameScreen(game));
//            }
        }

        updateRunning(deltaTime);
    }

    private void updateRunning(float deltaTime){

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            player1.getComponent(StateComponent.class).set(States.KICK);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            player1.getComponent(StateComponent.class).set(States.FIRE);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            player1.getComponent(StateComponent.class).set(States.TAIL);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            player1.getComponent(StateComponent.class).set(States.PUNCH);
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player2.getComponent(StateComponent.class).set(States.KICK);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player2.getComponent(StateComponent.class).set(States.FIRE);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player2.getComponent(StateComponent.class).set(States.TAIL);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player2.getComponent(StateComponent.class).set(States.PUNCH);
        }

    }

    @Override
    public void render(float delta) {
        update(delta);
    }


}
