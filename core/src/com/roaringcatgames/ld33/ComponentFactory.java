package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roaringcatgames.ld33.components.*;

/**
 * Created by barry on 8/22/15 @ 1:05 AM.
 */
public class ComponentFactory {
    private PooledEngine engine;

    public ComponentFactory(PooledEngine e){
        this.engine = e;
    }

    public TextureComponent createTextureComponent(){
        return createTextureComponent(null);
    }


    public TextureComponent createTextureComponent(TextureRegion region){
        TextureComponent comp = engine.createComponent(TextureComponent.class);
        if(region != null){
            comp.region = region;
        }
        return comp;
    }

    public TransformComponent createTransformComponent(float x, float y, float scaleX, float scaleY, float rotation, float originX, float originY){
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.position.set(x, y, 0f);
        transform.scale.set(scaleX, scaleY);
        transform.rotation = rotation;
        transform.origin.set(originX, originY);

        return transform;
    }

    public PulseComponent createPulseComponent(float maxScale, float minScale, float pulseSpeed){
        PulseComponent pulse = engine.createComponent(PulseComponent.class);
        pulse.maxScale = maxScale;
        pulse.minScale = minScale;
        pulse.pulseSpeed = pulseSpeed;

        return pulse;
    }

    public AnimationComponent createAnimationComponent(){
        AnimationComponent ani = engine.createComponent(AnimationComponent.class);
        return ani;
    }

    public StateComponent createStateComponent(String startState, boolean...isLooping){
        StateComponent state = engine.createComponent(StateComponent.class);
        state.set(startState);
        if(isLooping != null && isLooping.length > 0){
            state.isLooping = isLooping[0];
        }
        return state;
    }
}
