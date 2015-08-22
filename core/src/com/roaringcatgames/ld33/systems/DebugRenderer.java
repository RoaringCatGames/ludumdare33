package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.roaringcatgames.ld33.ComponentFactory;
import com.roaringcatgames.ld33.components.BoundsComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/22/15 @ 11:00 AM.
 */
public class DebugRenderer extends IteratingSystem {

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private PooledEngine engine;
    private ShapeRenderer shapeRenderer;
    private Array<Entity> processQueue;
    ComponentMapper<BoundsComponent> bm;
    ComponentFactory componentFactory;

    Entity camBounds;

    private boolean isDebugMode = false;

    public DebugRenderer(SpriteBatch batch, PooledEngine engine, OrthographicCamera camera){
        super(Family.all(BoundsComponent.class).get());
        this.batch = batch;
        this.engine = engine;
        this.cam = camera;

        shapeRenderer = new ShapeRenderer();
        processQueue = new Array<Entity>();
        componentFactory = new ComponentFactory(engine);

        bm = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
            isDebugMode = !isDebugMode;
            if(isDebugMode){
                camBounds = engine.createEntity();
                float width = cam.viewportWidth;
                float height = cam.viewportHeight;
                TransformComponent tc = componentFactory.createTransformComponent(width/2f, height/2f, 1f, 1f, 0f);

                BoundsComponent bc = componentFactory.createBoundsComponent(0f, 0f, cam.viewportWidth, cam.viewportHeight);
                camBounds.add(tc);
                camBounds.add(bc);
                engine.addEntity(camBounds);
            }else{
                engine.removeEntity(camBounds);
            }
        }

        if(isDebugMode) {
            Gdx.gl20.glLineWidth(1f);
            shapeRenderer.setProjectionMatrix(this.getEngine().getSystem(RenderingSystem.class).getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            for (Entity e : processQueue) {
                BoundsComponent bounds = bm.get(e);
                shapeRenderer.rect(bounds.bounds.x, bounds.bounds.y, bounds.bounds.width, bounds.bounds.height);
                shapeRenderer.circle(bounds.bounds.x + (bounds.bounds.width / 2f) - 0.2f,
                        bounds.bounds.y + (bounds.bounds.height / 2f) - 0.2f,
                        0.1f);
            }
            shapeRenderer.end();
        }

        processQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
         processQueue.add(entity);
    }
}
