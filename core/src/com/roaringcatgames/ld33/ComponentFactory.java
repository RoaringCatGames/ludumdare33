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

    public TransformComponent createTransformComponent(float x, float y, float scaleX, float scaleY, float rotation){//, float originX, float originY){
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        transform.position.set(x, y, 0f);
        transform.scale.set(scaleX, scaleY);
        transform.rotation = rotation;
        //transform.origin.set(originX, originY);

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

    public MovementComponent createMovementComponent(){
        return createMovementComponent(0f, 0f, 0f, 0f);
    }

    public MovementComponent createMovementComponent(float xVel, float yVel, float accelX, float accelY){
        MovementComponent move = engine.createComponent(MovementComponent.class);
        move.velocity.set(xVel, yVel);
        move.accel.set(accelX, accelY);
        return move;
    }

    public BoundsComponent createBoundsComponent(float x, float y, float width, float height){
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(x, y, width, height);
        return bounds;
    }

    public PlayerComponent createPlayerComponent(String name) {
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        player.name = name;
        return player;
    }

    public DanceMoveComponent createDanceMoveComponent(DanceMoveType moveType) {
        DanceMoveComponent move = engine.createComponent(DanceMoveComponent.class);
        move.moveType = moveType;
        return move;
    }

    public RotateToComponent createRotateToComponent(float targetRotation, float rotationSpeed) {
        RotateToComponent rotateTo = engine.createComponent(RotateToComponent.class);
        rotateTo.targetRotation = targetRotation;
        rotateTo.rotationSpeed = rotationSpeed;
        return rotateTo;
    }

    public StateTextureComponent createStateTextureComponent() {
        return engine.createComponent(StateTextureComponent.class);
    }

    public KeyPressedComponent createKeyPressedComponent(int key, String state) {
        KeyPressedComponent keypress = engine.createComponent(KeyPressedComponent.class);
        keypress.actionKey = key;
        keypress.targetState = state;
        return keypress;
    }
}
