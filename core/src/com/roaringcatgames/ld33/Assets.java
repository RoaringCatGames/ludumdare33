package com.roaringcatgames.ld33;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by barry on 8/22/15 @ 12:35 AM.
 */
public class Assets {

    private static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    private static Class<Music> MUSIC = Music.class;

    private static final String ANI_ATLAS = "animations/animations.atlas";
    private static final String SPRITE_ATLAS = "sprites/sprites.atlas";

    public static AssetManager am;

    public static AssetManager load(){
        am = new AssetManager();

        am.load(ANI_ATLAS, TEXTURE_ATLAS);
        am.load(SPRITE_ATLAS, TEXTURE_ATLAS);
        am.load(INTRO_MUSIC, MUSIC);
        am.load(SONG1_MUSIC, MUSIC);
        am.load(SONG2_MUSIC, MUSIC);
        return am;
    }

    public static Music getIntroMusic(){
        return am.get(INTRO_MUSIC, MUSIC);
    }
    public static Music getSong1(){
        return am.get(SONG1_MUSIC);
    }

    public static Array<TextureAtlas.AtlasRegion> getPlayerFrames(String state, boolean...isPlayer2){
        boolean isP2 = isPlayer2 != null && isPlayer2.length > 0 && isPlayer2[0];
        Array<TextureAtlas.AtlasRegion> regions;

        if(state == null){ state = ""; }
        switch(state){
            case States.DEFAULT:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_FRONT);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_FRONT);
                break;
            case States.PUNCH:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_PUNCH);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_PUNCH);
                break;
            case States.KICK:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_KICK);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_KICK);
                break;
            case States.TAIL:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_TAIL);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_TAIL);
                break;
            case States.FIRE:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_FIRE);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_FIRE);
                break;
            case States.WIN:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_WIN);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_WIN);
                break;
            default:
                if(!isP2)
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_WIN);
                else
                    regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P2_WIN);
                break;
        }

        return regions;
    }

    public static Array<TextureAtlas.AtlasRegion> getSweatFrames(){
        return am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(SWEAT);
    }

    public static TextureRegion getTVFrame(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(TV_COVER);
    }
    public static TextureRegion getSelectKnob(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(KNOB);
    }

    private static String P1_FRONT = "Player1/Front/Front";
    private static String P1_PUNCH = "Player1/Punch/Punch";
    private static String P1_KICK = "Player1/Kick/Kick";
    private static String P1_FIRE = "Player1/Fire/Fire";
    private static String P1_TAIL = "Player1/Tail/Tail";
    private static String P1_WIN = "Player1/Win/Win";

    private static String P2_FRONT = "Player2/Front/Front";
    private static String P2_PUNCH = "Player2/Punch/Punch";
    private static String P2_KICK = "Player2/Kick/Kick";
    private static String P2_FIRE = "Player2/Fire/Fire";
    private static String P2_TAIL = "Player2/Tail/Tail";
    private static String P2_WIN = "Player2/Win/Win";
    private static String SWEAT = "Sweat";

    private static String INTRO_MUSIC = "music/intro.mp3";
    private static String SONG1_MUSIC = "music/hoverwhip.mp3";
    private static String SONG2_MUSIC = "music/clusterblock.mp3";
    private static String TV_COVER = "tv";
    private static String KNOB = "??";
}
