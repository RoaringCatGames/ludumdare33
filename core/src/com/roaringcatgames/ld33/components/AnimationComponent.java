package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by barry on 8/22/15 @ 1:13 AM.
 */
public class AnimationComponent implements Component {
    public ArrayMap<String, Animation> animations = new ArrayMap<String, Animation>();

}
