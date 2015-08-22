package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.BoundsComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/22/15 @ 11:17 AM.
 */
public class BoundsSystem extends IteratingSystem {

    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<TransformComponent> tm;
    public BoundsSystem(){
        super(Family.all(BoundsComponent.class,
                         TransformComponent.class).get());

        bm = ComponentMapper.getFor(BoundsComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tfm = tm.get(entity);
        BoundsComponent bounds = bm.get(entity);

        bounds.bounds.x = tfm.position.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = tfm.position.y - bounds.bounds.height * 0.5f;
    }
}
