package com.roaringcatgames.ld33.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by barry on 8/23/15 @ 2:16 AM.
 */
public class KeyPressedComponent implements Component {
    public int actionKey;
    public String targetState = "DEFAULT";
}
