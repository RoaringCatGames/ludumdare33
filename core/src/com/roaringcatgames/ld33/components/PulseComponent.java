package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 8/20/15 @ 8:27 PM.
 */
public class PulseComponent implements Component {


    public float maxScale = 1f;
    public float minScale = 0.5f;
    public float pulseSpeed = 0.1f;
    //TODO: Time elapsed so we can use a sinefunction?
}
