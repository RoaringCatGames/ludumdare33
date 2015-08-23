package com.roaringcatgames.ld33;

import com.roaringcatgames.ld33.systems.VisibilityToggleSystem;

/**
 * Created by barry on 8/23/15 @ 10:42 AM.
 */
public class TwoPlayerVisibilityToggle implements VisibilityToggleSystem.IVisibilityToggle {
    private MonsterDancer game;

    public TwoPlayerVisibilityToggle(MonsterDancer game){
        this.game = game;
    }

    @Override
    public boolean isShowing() {
        return game.is2Player;
    }

    @Override
    public boolean isShowing(String toggleType) {
        return game.is2Player;
    }
}
