package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.ld33.World;
import com.roaringcatgames.ld33.components.*;

import java.util.Comparator;

/**
 * Created by barry on 8/22/15 @ 10:47 AM.
 */
public class DanceSystem extends IteratingSystem {

    ComponentMapper<DanceMoveComponent> dmm;
    ComponentMapper<BoundsComponent> bm;
    ComponentMapper<StateComponent> sm;

    Comparator<Entity> comparator;
    Array<Entity> p1MovesQueue;
    Array<Entity> p2MovesQueue;


    float _perfectY = (World.SCREEN.height + World.SCREEN.y) - (World.MOVE_SIZE*0.75f);
    float _maxPerfectY = _perfectY + (World.MOVE_SIZE*0.25f);
    float _minPerfectY = _perfectY - (World.MOVE_SIZE*0.25f);

    public DanceSystem(){
        super(Family.all(DanceMoveComponent.class,
                         BoundsComponent.class,
                         TextureComponent.class,
                         StateComponent.class,
                         StateTextureComponent.class,
                         MovementComponent.class).get());

        dmm = ComponentMapper.getFor(DanceMoveComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        p1MovesQueue = new Array<Entity>();
        p2MovesQueue = new Array<Entity>();
        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                BoundsComponent bc1 = bm.get(o1);
                BoundsComponent bc2 = bm.get(o2);
                return Float.compare(bc1.bounds.y, bc2.bounds.y);
            }
        };
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(p1MovesQueue.size > 0) {

            p1MovesQueue.sort(comparator);
            Entity e = p1MovesQueue.get(0);
            DanceMoveComponent dmc = dmm.get(e);
            World.TOP_P1_MOVE = e;
//            if(Gdx.input.isKeyJustPressed(dmc.key)){
//                //Check if in position. I could cheat here hard and use hard-coded meter values.
//                //  probably what's up
//                BoundsComponent bc = bm.get(e);
//                Vector2 center = new Vector2();
//                bc.bounds.getCenter(center);
//                if(isInPerfectRange(center.y)){
//                    //Do Perfect things
//                    Gdx.app.log("Dance System", "Perfect Range");
//                }else if(isInOkRange(center.y)){
//                    Gdx.app.log("Dance System", "OK Range");
//                }else{
//                    Gdx.app.log("Dance System", "Bounds y : " + center.y + " not below " + _maxPerfectY + " and not above " + _minPerfectY);
//                }
//            }
        }

        if(p2MovesQueue.size > 0){
            p2MovesQueue.sort(comparator);
            Entity e = p2MovesQueue.get(0);
            World.TOP_P2_MOVE = e;
        }

        p1MovesQueue.clear();
        p2MovesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DanceMoveComponent dmc = dmm.get(entity);

        BoundsComponent bc = bm.get(entity);
        if(bc.bounds.x > _maxPerfectY){
            this.getEngine().removeEntity(entity);
        }else if(dmc.isPlayer1){
            p1MovesQueue.add(entity);
        }else{
            p2MovesQueue.add(entity);
        }
    }

    private boolean isInPerfectRange(float yPos){

        return yPos >= _minPerfectY && yPos <= _maxPerfectY;
    }

    private boolean isInOkRange(float yPos){
        float minOkY = _minPerfectY - (World.MOVE_SIZE*0.25f);
        float maxOkY = _maxPerfectY + (World.MOVE_SIZE*0.25f);
        return yPos >= minOkY && yPos <= maxOkY;
    }
}
