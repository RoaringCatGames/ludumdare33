package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
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

    static final DanceMoveType[] moves = new DanceMoveType[]{
            DanceMoveType.KICK,
            DanceMoveType.FIRE,
            DanceMoveType.TAIL,
            DanceMoveType.PUNCH
    };

    MonsterDancer game;
    PooledEngine engine;
    ComponentFactory componentFactory;

    Entity player1;
    Entity player2;
    int songIndex = 0;
    Music currentSong;
    Array<Entity> p1Targets;
    Array<Entity> p2Targets;

    private int state;

    public GameScreen(MonsterDancer game){
        super();
        this.game = game;

        engine = new PooledEngine();

        engine.addSystem(new DanceSystem());
        engine.addSystem(new PulsingSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new StateTextureSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new DebugRenderer(game.batch, engine, engine.getSystem(RenderingSystem.class).getCamera()));

        componentFactory = new ComponentFactory(engine);

        createPlayers();

        createTVFrame();

        state = GAME_READY;
    }

    private void createPlayers() {

        float   p1x = (World.SCREEN.width/4f) + World.SCREEN.x,
                p1y = World.SCREEN.y + (PlayerComponent.HEIGHT_M/2f),
                p1sx = 1f,
                p1sy = 1f,
                p1r = 0f,
                p1frameTime = 0.08f;

        player1 = buildPlayerEntity("P1", p1x, p1y, p1sx, p1sy, p1r, p1frameTime);
        engine.addEntity(player1);

        if(game.is2Player) {
            float p2x = ((World.SCREEN.width / 4f) * 3f) + World.SCREEN.x,
                    p2y = World.SCREEN.y + (PlayerComponent.HEIGHT_M / 2f),
                    p2sx = -1f,
                    p2sy = 1f,
                    p2r = 0f,
                    p2frameTime = 0.16f;

            player2 = buildPlayerEntity("P2", p2x, p2y, p2sx, p2sy, p2r, p2frameTime);
            engine.addEntity(player2);
        }

        //MAYBE???
        Entity sweat = engine.createEntity();
        TextureComponent tc = componentFactory.createTextureComponent();
        sweat.add(tc);
        TransformComponent tfc = componentFactory.createTransformComponent(p1x - (PlayerComponent.WIDTH_M/2f), PlayerComponent.HEIGHT_M, 1f, 1f, 0f);
        sweat.add(tfc);
        StateComponent sc = componentFactory.createStateComponent(States.ON, true);
        sweat.add(sc);
        AnimationComponent ac = componentFactory.createAnimationComponent();
        ac.animations.put(States.ON, new Animation(p1frameTime, Assets.getSweatFrames()));
        sweat.add(ac);
        engine.addEntity(sweat);
    }

    private Entity buildPlayerEntity(String name, float x, float y, float scaleX, float scaleY, float rotation, float frameTime) {
        Entity e = engine.createEntity();
        TextureComponent textureComponent = componentFactory.createTextureComponent();
        TransformComponent transform = componentFactory.createTransformComponent(x, y, scaleX, scaleY, rotation);

        boolean isP2 = name == "P2";
        AnimationComponent aniComp = componentFactory.createAnimationComponent();
        Animation ani = new Animation(frameTime, Assets.getPlayerFrames(States.DEFAULT, isP2));
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

            generateMoveTargets();
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

        if(game.is2Player) {
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
    }

    private void updatePaused(float deltaTime){

    }

    private void updateSongOver(float deltaTime){

    }

    private void updateGameOver(float deltaTime){

    }

    private void generateMoveTargets(){

        float y = (World.SCREEN.height + World.SCREEN.y) - (World.MOVE_SIZE*0.75f);


        p1Targets = new Array<Entity>();
        p2Targets = new Array<Entity>();
        for(DanceMoveType dmt:moves){

            //P1
            Entity e = engine.createEntity();
            float x = getDanceMoveXPosition(dmt, false);

            TransformComponent tfc = componentFactory.createTransformComponent(x, y, 1f, 1f, 0f);
            e.add(tfc);
            TextureComponent tc = componentFactory.createTextureComponent();
            e.add(tc);
            BoundsComponent bc = componentFactory.createBoundsComponent(x, y, World.MOVE_SIZE, World.MOVE_SIZE);
            e.add(bc);
            engine.addEntity(e);

            p1Targets.add(e);

            if(game.is2Player) {
                //P2
                Entity e2 = engine.createEntity();
                float x2 = getDanceMoveXPosition(dmt, true);

                TransformComponent tfc2 = componentFactory.createTransformComponent(x2, y, 1f, 1f, 0f);
                e2.add(tfc2);
                TextureComponent tc2 = componentFactory.createTextureComponent();
                e2.add(tc2);
                BoundsComponent bc2 = componentFactory.createBoundsComponent(x2, y, World.MOVE_SIZE, World.MOVE_SIZE);
                e2.add(bc2);
                engine.addEntity(e2);
                p2Targets.add(e2);
            }
        }
    }

    private Song getSong(int songIndex){

        Song s = new Song("s1", 900f, 68);
        float targetMillis = 0f;
        int index = 0;
        float beatsInSong = s.getLengthInSeconds()*1000f/s.getMillisPerBeat();
        for(int i = 0; i<beatsInSong;i++){
            targetMillis += s.getMillisPerBeat();
            DanceMoveType dmt = moves[index++%4];
            s.addMove(targetMillis, dmt);
        }

        return s;
    }

    private void generateDanceMoves(Song song){



        float distanceBetweenQuarter = 6f;
        float initialY = World.HEIGHT_METERS - (World.MOVE_SIZE /2f) - distanceBetweenQuarter;

        //How many meters per millisecond
        float metersPerSecond = distanceBetweenQuarter / (song.getMillisPerBeat()/1000f);


        for(DanceMove m:song.getMoves()){
            createDanceMoveEntity(initialY, metersPerSecond, m, true);
            if(game.is2Player) {
                createDanceMoveEntity(initialY, metersPerSecond, m, false);
            }
        }
    }

    private void createDanceMoveEntity(float initialY, float metersPerSecond, DanceMove m, boolean isPlayer1) {
        Entity e = engine.createEntity();

        int key;
        if(isPlayer1) {
            key = m.moveType == DanceMoveType.KICK ? Input.Keys.A :
                    m.moveType == DanceMoveType.FIRE ? Input.Keys.W :
                            m.moveType == DanceMoveType.TAIL ? Input.Keys.S :
                                    Input.Keys.D;
        }else{
            key = m.moveType == DanceMoveType.KICK ? Input.Keys.LEFT :
                    m.moveType == DanceMoveType.FIRE ? Input.Keys.UP :
                            m.moveType == DanceMoveType.TAIL ? Input.Keys.DOWN :
                                    Input.Keys.RIGHT;
        }
        float x = getDanceMoveXPosition(m.moveType, !isPlayer1);
        float y = initialY - (metersPerSecond*(m.targetMillis/1000f));
        float scale = 0.5f; //button images are off scale-wise

        TextureComponent txc = componentFactory.createTextureComponent();
        StateComponent sc = componentFactory.createStateComponent(States.DEFAULT);
        TransformComponent tc = componentFactory.createTransformComponent(x, y, scale, scale, 0f);
        BoundsComponent bc = componentFactory.createBoundsComponent(tc.position.x, tc.position.y, World.MOVE_SIZE, World.MOVE_SIZE);
        MovementComponent mc = componentFactory.createMovementComponent(0f, metersPerSecond, 0f, 0f);
        DanceMoveComponent dc = componentFactory.createDanceMoveComponent(m.moveType, key, isPlayer1);
        StateTextureComponent stc = componentFactory.createStateTextureComponent();
        stc.regions.put(States.DEFAULT, Assets.getDefaultKeyFrame(key, isPlayer1));
        stc.regions.put(States.PRESSED, Assets.getPressedKeyFrame(key, isPlayer1));

        e.add(dc);
        e.add(mc);
        e.add(bc);
        e.add(tc);
        e.add(stc);
        e.add(txc);
        e.add(sc);
        engine.addEntity(e);
    }

    private float getDanceMoveXPosition(DanceMoveType moveType, boolean...isPlayer2) {

        boolean isP2 = (isPlayer2 != null && isPlayer2.length > 0 && isPlayer2[0]);

        float midX = World.SCREEN.width/4f;
        if(isP2) { midX *= 3f; }
        midX += World.SCREEN.x;

        return moveType == DanceMoveType.KICK ? midX-(World.MOVE_SIZE*1.5f) :
               moveType == DanceMoveType.FIRE ? midX-(World.MOVE_SIZE/2f) :
               moveType == DanceMoveType.TAIL ? midX+(World.MOVE_SIZE/2f) :
                                                midX+(World.MOVE_SIZE*1.5f);
    }

    @Override
    public void render(float delta) {
        update(delta);
    }


}
