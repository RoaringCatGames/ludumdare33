package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 8/22/15 @ 11:57 PM.
 */
public class KnobComponent implements Component {
    public float targetOnePlayerRotation = 0f;
    public float targetTwoPlayerRotation = 180f;
    public float knobSpeed = 180f;
}
