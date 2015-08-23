package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by barry on 8/22/15 @ 3:59 PM.
 */
public class World {

    public static float WIDTH_METERS = 37.5f;
    public static float HEIGHT_METERS = 25.0f;
    public static float CENTERX_M = WIDTH_METERS/2f;
    public static float CENTERY_M = HEIGHT_METERS/2f;

    public static Rectangle SCREEN = new Rectangle(1.875f, 2f, 28.125f, 21f);

    public static float MOVE_SIZE = 2f;

    public static Entity TOP_P1_MOVE;
    public static Entity TOP_P2_MOVE;
}
