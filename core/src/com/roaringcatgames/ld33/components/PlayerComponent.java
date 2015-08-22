package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;
import com.roaringcatgames.ld33.systems.RenderingSystem;

/**
 * Created by barry on 8/22/15 @ 1:24 PM.
 */
public class PlayerComponent implements Component {

    public static final float WIDTH_P = 213f;
    public static final float WIDTH_M = RenderingSystem.PixelsToMeters(WIDTH_P);
    public static final float HEIGHT_P = 384f;
    public static final float HEIGHT_M = RenderingSystem.PixelsToMeters(HEIGHT_P);
}
