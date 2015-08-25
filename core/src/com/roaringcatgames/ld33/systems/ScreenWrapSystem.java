package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.BoundsComponent;
import com.roaringcatgames.ld33.components.MovementComponent;
import com.roaringcatgames.ld33.components.ScreenWrapComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/24/15 @ 8:24 PM.
 */
public class ScreenWrapSystem extends IteratingSystem {

    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<MovementComponent> mm;
    ComponentMapper<TransformComponent> tm;

    private float left;
    private float right;

    public ScreenWrapSystem(float leftSide, float rightSide){
        super(Family.all(TransformComponent.class, MovementComponent.class, ScreenWrapComponent.class).get());
        this.left = leftSide;
        this.right = rightSide;

        bm = ComponentMapper.getFor(BoundsComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {


        BoundsComponent bc = bm.get(entity);
        MovementComponent mc = mm.get(entity);
        TransformComponent tc = tm.get(entity);

        if((bc.bounds.x > right && mc.velocity.x > 0) ||
           (bc.bounds.x + bc.bounds.width < left && mc.velocity.x < 0)    ){
            tc.scale.x *= -1f;
            mc.velocity.x *= -1f;
        }
    }
}
