package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.ld33.components.BoundsComponent;

/**
 * Created by barry on 8/22/15 @ 11:00 AM.
 */
public class DebugRenderer extends IteratingSystem {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Array<Entity> processQueue;
    ComponentMapper<BoundsComponent> bm;


    public DebugRenderer(SpriteBatch batch){
        super(Family.all(BoundsComponent.class).get());
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        processQueue = new Array<Entity>();

        bm = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        Gdx.gl20.glLineWidth(1f);
        shapeRenderer.setProjectionMatrix(this.getEngine().getSystem(RenderingSystem.class).getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);
        for(Entity e:processQueue) {
            BoundsComponent bounds = bm.get(e);
            shapeRenderer.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
            shapeRenderer.circle(bounds.bounds.x + (bounds.bounds.width/2f)-0.2f,
                                 bounds.bounds.y + (bounds.bounds.height/2f)-0.2f,
                                 0.1f);
        }
        shapeRenderer.end();
        processQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
         processQueue.add(entity);
    }
}
