package com.roaringcatgames.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
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

    private static Texture loadTexture(String fileName){
        return new Texture(Gdx.files.internal(fileName));
    }

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

    public static Array<TextureAtlas.AtlasRegion> getPlayerFrames(String state){
        Array<TextureAtlas.AtlasRegion> regions;

        if(state == null){ state = ""; }
        switch(state){
            case States.DEFAULT:
                regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_FRONT);
                break;
            case States.PUNCH:
                regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_PUNCH);
                break;
            case States.KICK:
                regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_PUNCH);
                break;
            case States.TAIL:
                regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_PUNCH);
                break;
            case States.FIRE:
                regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_PUNCH);
                break;
            default:
                regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(P1_FRONT);
                break;
        }

        return regions;
    }

    public static TextureRegion getTVFrame(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(TV_COVER);
    }


    private static String P1_FRONT = "Front";
    private static String P1_PUNCH = "Punch";
    private static String INTRO_MUSIC = "music/intro.mp3";
    private static String SONG1_MUSIC = "music/hoverwhip.mp3";
    private static String SONG2_MUSIC = "music/clusterblock.mp3";
    private static String TV_COVER = "tv";
}
