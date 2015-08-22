package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.ld33.components.AnimationComponent;
import com.roaringcatgames.ld33.components.StateComponent;
import com.roaringcatgames.ld33.components.TextureComponent;
import com.roaringcatgames.ld33.components.TransformComponent;
import com.roaringcatgames.ld33.systems.AnimationSystem;
import com.roaringcatgames.ld33.systems.PulsingSystem;
import com.roaringcatgames.ld33.systems.RenderingSystem;

/**
 * Created by barry on 8/22/15 @ 12:36 AM.
 */
public class MenuScreen extends ScreenAdapter {

    MonsterDancer game;
    PooledEngine engine;
    ComponentFactory componentFactory;

    Entity player1;
    Entity player2;

    public MenuScreen(MonsterDancer game){
        super();
        this.game = game;

        engine = new PooledEngine();

        engine.addSystem(new PulsingSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(game.batch));

        componentFactory = new ComponentFactory(engine);

        createPlayers();
    }

    private void createPlayers() {
        float p1Origin= 128f, p1x = 4f, p1y = 4f, p1sx = 1f, p1sy = 1f, p1r = 90f, p1frameTime = 0.08f;
        float p2Origin = 128f, p2x = 12f, p2y = 4f, p2sx = 1f, p2sy = 1f, p2r = -90f, p2frameTime = 0.16f;

        player1 = buildPlayerEntity(p1Origin, p1x, p1y, p1sx, p1sy, p1r, p1frameTime);
        engine.addEntity(player1);

        player2 = buildPlayerEntity(p2Origin, p2x, p2y, p2sx, p2sy, p2r, p2frameTime);
        engine.addEntity(player2);
    }

    private Entity buildPlayerEntity(float size, float x, float y, float scaleX, float scaleY, float rotation, float frameTime) {
        Entity e = engine.createEntity();
        TextureComponent textureComponent = componentFactory.createTextureComponent();
        TransformComponent transform = componentFactory.createTransformComponent(x, y, scaleX, scaleY, rotation,
                size * 0.5f, size * 0.5f);

        AnimationComponent p1AniComp = componentFactory.createAnimationComponent();
        Animation p1Ani = new Animation(frameTime, Assets.getPlayerFrames());
        p1AniComp.animations.put(States.DEFAULT, p1Ani);
        StateComponent p1State = componentFactory.createStateComponent(States.DEFAULT, true);
        e.add(textureComponent);
        e.add(transform);
        e.add(p1AniComp);
        e.add(p1State);

        return e;
    }

    public void update(float deltaTime){
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        engine.update(deltaTime);
    }

    @Override
    public void render(float delta) {
        update(delta);
    }


}
