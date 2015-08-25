package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.MathUtil;
import com.roaringcatgames.ld33.components.OvalPathComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/24/15 @ 7:58 PM.
 */
public class OvalPathSystem extends IteratingSystem {

    ComponentMapper<TransformComponent> tm;
    ComponentMapper<OvalPathComponent> om;

    public OvalPathSystem(){
        super(Family.all(TransformComponent.class, OvalPathComponent.class).get());

        tm = ComponentMapper.getFor(TransformComponent.class);
        om = ComponentMapper.getFor(OvalPathComponent.class);

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        OvalPathComponent oc = om.get(entity);
        TransformComponent tc = tm.get(entity);

        oc.elapsedTime += deltaTime;


        float currentXSinePos = (float) MathUtil.getSineYForTime(oc.xShakeSpeed, oc.xScale, oc.elapsedTime);
        float targetX = tc.position.x + currentXSinePos - oc.lastXSinePos;
        oc.lastXSinePos = currentXSinePos;
        float currentYSinePos = (float)MathUtil.getSineYForTime(oc.yShakeSpeed, oc.yScale, oc.elapsedTime);
        float targetY = tc.position.y + currentYSinePos - oc.lastYSinePos;
        oc.lastYSinePos = currentYSinePos;
        tc.position.set(targetX, targetY, tc.position.z);
    }
}
