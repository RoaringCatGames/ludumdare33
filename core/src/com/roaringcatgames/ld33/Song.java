package com.roaringcatgames.ld33;

import com.badlogic.gdx.utils.Array;

/**
 * Created by barry on 8/22/15 @ 4:41 PM.
 */
public class Song {

    private float millisPerBeat;
    private String name;
    private float lengthInSeconds;
    private Array<DanceMove> moves;
    public Song(String name, float millisPerBeat, float lengthSeconds){
        this.name = name;
        this.millisPerBeat = millisPerBeat;
        this.lengthInSeconds = lengthSeconds;
        moves = new Array<DanceMove>();
    }

    public float getMillisPerBeat(){
        return millisPerBeat;
    }

    public float getLength(){
        return lengthInSeconds;
    }
    public void  addMove(float targetMillis, DanceMoveType type){
        moves.add(new DanceMove(targetMillis, type));
    }

    public Array<DanceMove> getMoves(){
        return moves;
    }
}
