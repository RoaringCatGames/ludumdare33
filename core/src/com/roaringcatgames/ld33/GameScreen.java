package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.roaringcatgames.ld33.components.*;
import com.roaringcatgames.ld33.systems.*;

import javax.xml.soap.Text;

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
    int songIndex = 0;
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
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new DebugRenderer(game.batch, engine, engine.getSystem(RenderingSystem.class).getCamera()));

        componentFactory = new ComponentFactory(engine);

        createPlayers();

        state = GAME_READY;
    }

    private void createPlayers() {
        float   p1x = PlayerComponent.WIDTH_M/2f,
                p1y = PlayerComponent.HEIGHT_M/2f,
                p1sx = 1f,
                p1sy = 1f,
                p1r = 0f,
                p1frameTime = 0.08f;

        player1 = buildPlayerEntity("P1", p1x, p1y, p1sx, p1sy, p1r, p1frameTime);
        engine.addEntity(player1);

        float   p2x = p1x + (1.5f*PlayerComponent.WIDTH_M),
                p2y = PlayerComponent.HEIGHT_M/2f,
                p2sx = -1f,
                p2sy = 1f,
                p2r = 0f,
                p2frameTime = 0.16f;

        player2 = buildPlayerEntity("P2", p2x, p2y, p2sx, p2sy, p2r, p2frameTime);
        engine.addEntity(player2);
    }

    private Entity buildPlayerEntity(String name, float x, float y, float scaleX, float scaleY, float rotation, float frameTime) {
        Entity e = engine.createEntity();
        TextureComponent textureComponent = componentFactory.createTextureComponent();
        TransformComponent transform = componentFactory.createTransformComponent(x, y, scaleX, scaleY, rotation);

        AnimationComponent aniComp = componentFactory.createAnimationComponent();
        Animation ani = new Animation(frameTime, Assets.getPlayerFrames(States.DEFAULT));
        Animation aniKick = new Animation(frameTime, Assets.getPlayerFrames(States.KICK), Animation.PlayMode.NORMAL);
        Animation aniPunch = new Animation(frameTime, Assets.getPlayerFrames(States.PUNCH), Animation.PlayMode.NORMAL);
        Animation aniTail = new Animation(frameTime, Assets.getPlayerFrames(States.TAIL), Animation.PlayMode.NORMAL);
        Animation aniFire = new Animation(frameTime, Assets.getPlayerFrames(States.FIRE), Animation.PlayMode.NORMAL);
        aniComp.animations.put(States.DEFAULT, ani);
        aniComp.animations.put(States.KICK, aniKick);
        aniComp.animations.put(States.PUNCH, aniPunch);
        aniComp.animations.put(States.TAIL, aniTail);
        aniComp.animations.put(States.FIRE, aniFire);

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

            Song s = getSong(songIndex);
            generateDanceMoves(s);

            currentSong = Assets.getSong1();
            currentSong.setLooping(false);
            currentSong.play();
            state = GAME_RUNNING;
        }
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            player2.getComponent(StateComponent.class).set(States.KICK);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player2.getComponent(StateComponent.class).set(States.FIRE);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            player2.getComponent(StateComponent.class).set(States.TAIL);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            player2.getComponent(StateComponent.class).set(States.PUNCH);
        }



    }

    private void updatePaused(float deltaTime){

    }

    private void updateSongOver(float deltaTime){

    }

    private void updateGameOver(float deltaTime){

    }

    private Song getSong(int songIndex){
        DanceMoveType[] moves = new DanceMoveType[]{
                DanceMoveType.KICK,
                DanceMoveType.FIRE,
                DanceMoveType.TAIL,
                DanceMoveType.PUNCH
        };
        Song s = new Song("s1", 1000f, 68);
        float targetMillis = 0f;
        int index = 0;
        for(int i = 0; i<68;i++){
            targetMillis += 1000f;
            DanceMoveType dmt = moves[index++%3];
            s.addMove(targetMillis, dmt);
        }

        return s;
    }

    private void generateDanceMoves(Song song){

        float distanceBetweenQuarter = 1f;
        float initialY = 0f;

        //How many meters per millisecond
        float metersPerSecond = distanceBetweenQuarter / (song.getMillisPerBeat()/1000f);


        for(DanceMove m:song.getMoves()){
            Entity e = engine.createEntity();
            //Add Texture for Move
            TextureComponent txc = componentFactory.createTextureComponent();
            e.add(txc);
            float x = m.moveType == DanceMoveType.KICK ? 4f :
                      m.moveType == DanceMoveType.FIRE ? 10f :
                      m.moveType == DanceMoveType.TAIL ? 16f :
                                                         22f; //PUNCH

            float y = initialY - (metersPerSecond*(m.targetMillis/1000f));

            //Add Transform for Move
            TransformComponent tc = componentFactory.createTransformComponent(x, y, 1f, 1f, 0f);
            e.add(tc);
            //Add Bounds for Move
            BoundsComponent bc = componentFactory.createBoundsComponent(tc.position.x, tc.position.y, 6f, 6f);
            e.add(bc);
            //Add Animations for Move
            AnimationComponent ac = componentFactory.createAnimationComponent();
            //ac.animations.put(States.DEFAULT, Assets.)

            //Add Movement for Move
            MovementComponent mc = componentFactory.createMovementComponent(0f, metersPerSecond, 0f, 0f);
            e.add(mc);

            engine.addEntity(e);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
    }


}
