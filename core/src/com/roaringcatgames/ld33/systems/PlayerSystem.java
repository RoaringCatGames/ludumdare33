package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.States;
import com.roaringcatgames.ld33.components.AnimationComponent;
import com.roaringcatgames.ld33.components.PlayerComponent;
import com.roaringcatgames.ld33.components.StateComponent;

/**
 * Created by barry on 8/22/15 @ 3:43 PM.
 */
public class PlayerSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<AnimationComponent> am;
    ComponentMapper<StateComponent> sm;

    public PlayerSystem(){
        super(Family.all(PlayerComponent.class,
                         AnimationComponent.class,
                         StateComponent.class).get());

        pm = ComponentMapper.getFor(PlayerComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ac = am.get(entity);
        StateComponent sc = sm.get(entity);


        if(ac.animations.get(sc.get()).isAnimationFinished(sc.time)){
            sc.set(States.DEFAULT);
        }
    }
}
