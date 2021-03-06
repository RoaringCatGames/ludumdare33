package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.ld33.components.TextComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/23/15 @ 4:55 PM.
 */
public class TextRendererSystem extends IteratingSystem {

    SpriteBatch batch;
    OrthographicCamera cam;
    ComponentMapper<TextComponent> tm;
    ComponentMapper<TransformComponent> tfm;
    Array<Entity> renderQueue;

    public TextRendererSystem(SpriteBatch batch){
        super(Family.all(TextComponent.class, TransformComponent.class).get());
        this.batch = batch;
        this.cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.cam.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0f);

        renderQueue = new Array<Entity>();
        tm = ComponentMapper.getFor(TextComponent.class);
        tfm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        for(Entity entity:renderQueue) {
            TextComponent text = tm.get(entity);
            TransformComponent transform = tfm.get(entity);

            if (text.text != null && !transform.isHidden) {
                GlyphLayout textData = new GlyphLayout(text.font, text.text);
                float xTranslatedPosition = ((transform.position.x)*(cam.viewportWidth/37.5f))-(textData.width/2f);
                float yTranslatedPosition = ((transform.position.y)*(cam.viewportHeight/25f))-(textData.height/2f);
                text.font.draw(batch, textData, xTranslatedPosition, yTranslatedPosition);
            }
        }

        batch.end();

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);

    }
}
