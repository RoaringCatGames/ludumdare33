package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 8/24/15 @ 8:01 PM.
 */
public class OvalPathComponent implements Component {

    public float xScale;
    public float yScale;
    public float xShakeSpeed;
    public float yShakeSpeed;
    public float elapsedTime;
    public float lastXSinePos;
    public float lastYSinePos;
}
