package com.roaringcatgames.ld33;

/**
 * Created by barry on 8/22/15 @ 4:43 PM.
 */
public class DanceMove {

    public float targetMillis;
    public DanceMoveType moveType;

    public DanceMove(float targetMillis, DanceMoveType moveType){
        this.targetMillis = targetMillis;
        this.moveType = moveType;
    }
}
