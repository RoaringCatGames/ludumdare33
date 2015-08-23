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
                return Float.compare(bc2.bounds.y, bc1.bounds.y);
            }
        };
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(p1MovesQueue.size > 0) {
            p1MovesQueue.sort(comparator);
            Entity e = p1MovesQueue.get(0);
            World.TOP_P1_MOVE = e;
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
        if(World.isOutOfRange(bc.bounds.y)){
            this.getEngine().removeEntity(entity);
        }else if(dmc.isPlayer1){
            p1MovesQueue.add(entity);
        }else{
            p2MovesQueue.add(entity);
        }
    }


}
