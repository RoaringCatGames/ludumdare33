package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.ld33.components.TextureComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

import java.util.Comparator;

/**
 * Created by barry on 8/18/15 @ 8:27 PM.
 */
public class RenderingSystem extends IteratingSystem {

    static final float FRUSTUM_WIDTH = 37.5f;
    static final float FRUSTUM_HEIGHT = 25.0f;
    static final float PPM = 32.0f;
    public static final float PIXELS_TO_METRES = 1.0f / PPM;

    public static float PixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METRES;
    }

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(transformM.get(entityB).position.z -
                        transformM.get(entityA).position.z);
            }
        };

        this.batch = batch;

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width/2f;
            float originY = height/2f;

            batch.draw(tex.region,
                    t.position.x - originX, t.position.y - originY,
                    originX, originY,
                    width, height,
                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y),
                    t.rotation);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}