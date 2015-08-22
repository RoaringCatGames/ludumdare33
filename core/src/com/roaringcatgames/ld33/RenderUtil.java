package com.roaringcatgames.ld33;

import com.roaringcatgames.ld33.systems.RenderingSystem;

/**
 * Created by barry on 8/22/15 @ 1:08 PM.
 */
public class RenderUtil {

    public static float PixelsToMeters(float pixelValue){
        return pixelValue * RenderingSystem.PIXELS_TO_METRES;
    }
}
