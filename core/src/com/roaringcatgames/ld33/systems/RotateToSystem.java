package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.roaringcatgames.ld33.components.RotateToComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/22/15 @ 11:53 PM.
 */
public class RotateToSystem extends IteratingSystem {

    ComponentMapper<RotateToComponent> rm;
    ComponentMapper<TransformComponent> tm;

    public RotateToSystem(){
        super(Family.all(RotateToComponent.class,
                         TransformComponent.class).get());
        rm = ComponentMapper.getFor(RotateToComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        RotateToComponent rc = rm.get(entity);
        TransformComponent tc = tm.get(entity);

        if(tc.rotation == rc.targetRotation ){
            rc.rotationSpeed = 0f;
        }else if(rc.rotationSpeed < 0f && tc.rotation > rc.targetRotation) {
            float adjust = rc.rotationSpeed*deltaTime;
            tc.rotation += adjust;
            if(tc.rotation < rc.targetRotation){
                tc.rotation = rc.targetRotation;
            }
        }else if(rc.rotationSpeed > 0f && tc.rotation < rc.targetRotation){

            float adjust = rc.rotationSpeed*deltaTime;
            tc.rotation += adjust;
            if(tc.rotation > rc.targetRotation){
                tc.rotation = rc.targetRotation;
            }
        }
    }
}
