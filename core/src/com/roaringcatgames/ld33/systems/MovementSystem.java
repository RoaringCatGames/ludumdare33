package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.roaringcatgames.ld33.components.MovementComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/22/15 @ 9:50 AM.
 */
public class MovementSystem extends IteratingSystem{

    private Vector2 moveCalc = new Vector2();

    ComponentMapper<TransformComponent> tm;
    ComponentMapper<MovementComponent> mm;

    public MovementSystem(){
        super(Family.all(TransformComponent.class,
                         MovementComponent.class).get());
        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);
        MovementComponent mvm = mm.get(entity);

        moveCalc.set(mvm.accel).scl(deltaTime);
        mvm.velocity.add(moveCalc);

        moveCalc.set(mvm.velocity).scl(deltaTime);
        tfm.position.add(moveCalc.x, moveCalc.y, 0.0f);
    }
}
