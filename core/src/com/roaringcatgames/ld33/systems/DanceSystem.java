package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.StateComponent;

/**
 * Created by barry on 8/22/15 @ 10:47 AM.
 */
public class DanceSystem extends IteratingSystem {

    public DanceSystem(){
        super(Family.all(StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
