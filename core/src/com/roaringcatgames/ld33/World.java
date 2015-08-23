package com.roaringcatgames.ld33;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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


    private static float _perfectY = (World.SCREEN.height + World.SCREEN.y) - (World.MOVE_SIZE*0.75f);
    private static float _maxPerfectY = _perfectY + (World.MOVE_SIZE*0.5f);
    private static float _minPerfectY = _perfectY - (World.MOVE_SIZE*0.5f);

    public static boolean isOutOfRange(float yPos){
        return yPos > _maxPerfectY;
    }

    public static boolean isInPerfectRange(Rectangle r){
        Gdx.app.log("WORLD", "Rectangle is x: " + r.x + " y: " + r.y + " w: " + r.width + " h: " + r.height);
        return isInPerfectRange(r.y + (r.height*0.5f));
    }

    public static boolean isInPerfectRange(float yPos){
        Gdx.app.log("WORLD", "Range position: " + yPos);
        return yPos >= _minPerfectY && yPos <= _maxPerfectY;
    }

    public static boolean isInOkRange(Rectangle r){
        Gdx.app.log("WORLD", "Rectangle is x: " + r.x + " y: " + r.y + " w: " + r.width + " h: " + r.height);
        return isInOkRange(r.y + (r.height*0.5f));
    }
    public static boolean isInOkRange(float yPos){
        Gdx.app.log("WORLD", "Range position: " + yPos);
        float minOkY = _minPerfectY - (World.MOVE_SIZE*0.5f);
        float maxOkY = _maxPerfectY + (World.MOVE_SIZE*0.5f);
        return yPos >= minOkY && yPos <= maxOkY;
    }

    public static float getScreenCenter() {
        return World.SCREEN.x + (World.SCREEN.width/2f);
    }
}
