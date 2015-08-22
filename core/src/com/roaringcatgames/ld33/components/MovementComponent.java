package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 8/22/15 @ 9:48 AM.
 */
public class MovementComponent implements Component {
    public Vector2 velocity = new Vector2();
    public Vector2 accel = new Vector2();
}
