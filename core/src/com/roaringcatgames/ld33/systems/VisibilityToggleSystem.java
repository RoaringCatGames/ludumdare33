package com.roaringcatgames.ld33.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ld33.components.ToggleComponent;
import com.roaringcatgames.ld33.components.TransformComponent;

/**
 * Created by barry on 8/23/15 @ 10:19 AM.
 */
public class VisibilityToggleSystem extends IteratingSystem {

    public interface IVisibilityToggle{
        boolean isShowing();
        boolean isShowing(String toggleType);
    }

    private IVisibilityToggle toggler;
    ComponentMapper<ToggleComponent> tm;
    ComponentMapper<TransformComponent> tfm;

    public VisibilityToggleSystem(IVisibilityToggle toggler){
        super(Family.all(ToggleComponent.class, TransformComponent.class).get());

        this.toggler = toggler;
        tm = ComponentMapper.getFor(ToggleComponent.class);
        tfm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ToggleComponent tc = tm.get(entity);
        TransformComponent tfc = tfm.get(entity);

        boolean isShowing;
        if(tc.toggleType != null && tc.toggleType != ""){
            isShowing = toggler.isShowing(tc.toggleType);
        }else{
            isShowing = toggler.isShowing();
        }

        tfc.isHidden = !isShowing;
    }
}
