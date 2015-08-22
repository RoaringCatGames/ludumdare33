package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 8/22/15 @ 1:20 AM.
 */
public class StateComponent implements Component {
    private String state = "DEFAULT";
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(String newState){
        state = newState;
        time = 0.0f;
    }

    public String get(){
        return state;
    }
}
