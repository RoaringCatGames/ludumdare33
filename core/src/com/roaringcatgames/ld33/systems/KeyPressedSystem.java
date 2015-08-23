package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.roaringcatgames.ld33.States;
import com.roaringcatgames.ld33.components.KeyPressedComponent;
import com.roaringcatgames.ld33.components.StateComponent;

/**
 * Created by barry on 8/23/15 @ 2:17 AM.
 */
public class KeyPressedSystem extends IteratingSystem {

    ComponentMapper<StateComponent> sm;
    ComponentMapper<KeyPressedComponent> kpm;

    public KeyPressedSystem(){
        super(Family.all(KeyPressedComponent.class,
                         StateComponent.class).get());

        sm = ComponentMapper.getFor(StateComponent.class);
        kpm = ComponentMapper.getFor(KeyPressedComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        KeyPressedComponent kpc = kpm.get(entity);
        StateComponent sc = sm.get(entity);
        if(Gdx.input.isKeyPressed(kpc.actionKey)){
            sc.set(kpc.targetState);
        }else{
            sc.set(States.DEFAULT);
        }
    }
}
