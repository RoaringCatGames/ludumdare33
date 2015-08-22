package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.PulseComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/20/15 @ 8:24 PM.
 */
public class PulsingSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<PulseComponent> pm;

    public PulsingSystem() {
        super(Family.all(TransformComponent.class, PulseComponent.class).get());
        tm = ComponentMapper.getFor(TransformComponent.class);
        pm = ComponentMapper.getFor(PulseComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent t = tm.get(entity);
        PulseComponent p = pm.get(entity);

        float pulseAdjust = (p.pulseSpeed * deltaTime);

        t.scale.x += pulseAdjust;
        t.scale.y += pulseAdjust;

        if(t.scale.x >= p.maxScale && p.pulseSpeed > 0f){
            p.pulseSpeed *= -1f;
            t.scale.x = p.maxScale;
            t.scale.y = p.maxScale;
        }else if(t.scale.x <= p.minScale && p.pulseSpeed < 0f){
            p.pulseSpeed *= -1f;
            t.scale.x = p.minScale;
            t.scale.y = p.minScale;
        }

    }


}
