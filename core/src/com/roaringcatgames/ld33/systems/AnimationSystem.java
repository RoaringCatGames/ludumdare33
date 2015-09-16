package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.AnimationComponent;
import com.roaringcatgames.ld33.components.RCGTextureComponent;
import com.roaringcatgames.ld33.components.StateComponent;

/**
 * Created by barry on 8/22/15 @ 1:30 AM.
 */
public class AnimationSystem extends IteratingSystem {

    ComponentMapper<RCGTextureComponent> tm;
    ComponentMapper<AnimationComponent> am;
    ComponentMapper<StateComponent> sm;

    public AnimationSystem(){
        super(Family.all(RCGTextureComponent.class,
                         AnimationComponent.class,
                         StateComponent.class).get());

        tm = ComponentMapper.getFor(RCGTextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ani = am.get(entity);
        StateComponent state = sm.get(entity);

        if(ani.animations.containsKey(state.get())){
            RCGTextureComponent tex = tm.get(entity);
            tex.region = ani.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
        }

        state.time += deltaTime;
    }
}
