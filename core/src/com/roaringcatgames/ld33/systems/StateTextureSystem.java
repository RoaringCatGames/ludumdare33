package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.StateComponent;
import com.roaringcatgames.ld33.components.StateTextureComponent;
import com.roaringcatgames.ld33.components.TextureComponent;

import javax.xml.soap.Text;

/**
 * Created by barry on 8/23/15 @ 1:09 AM.
 */
public class StateTextureSystem extends IteratingSystem {

    ComponentMapper<TextureComponent> tm;
    ComponentMapper<StateTextureComponent> stm;
    ComponentMapper<StateComponent> sm;

    public StateTextureSystem(){
        super(Family.all(TextureComponent.class,
                StateTextureComponent.class,
                StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        stm = ComponentMapper.getFor(StateTextureComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity,float deltaTime) {
        TextureComponent tc = tm.get(entity);
        StateComponent sc = sm.get(entity);
        StateTextureComponent stc = stm.get(entity);

        if(stc.regions.containsKey(sc.get())){
            tc.region = stc.regions.get(sc.get());
        }
    }
}
