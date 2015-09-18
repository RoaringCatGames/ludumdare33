package com.roaringcatgames.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by barry on 8/22/15 @ 12:35 AM.
 */
public class Assets {

    private static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    private static Class<Music> MUSIC = Music.class;
    private static Class<BitmapFont> BITMAP_FONT = BitmapFont.class;
    private static Class<Sound> SOUND = Sound.class;

    private static final String FONT = "fonts/courier-new-bold-32.fnt";
    private static final String ANI_ATLAS = "animations/animations.atlas";
    private static final String SPRITE_ATLAS = "sprites/sprites.atlas";


    public static TextureRegion splashScreen;

    public static AssetManager am;

    public static void loadSplash() {
        splashScreen = new TextureRegion(new Texture(Gdx.files.internal("splash.jpg")));
    }
    public static AssetManager load(){
        am = new AssetManager();

        am.load(ANI_ATLAS, TEXTURE_ATLAS);
        am.load(SPRITE_ATLAS, TEXTURE_ATLAS);
        am.load(INTRO_MUSIC, MUSIC);
        am.load(SONG1_MUSIC, MUSIC);
        am.load(SONG2_MUSIC, MUSIC);
        am.load(FONT, BITMAP_FONT);
        am.load(SFX_OK, SOUND);
        am.load(SFX_GOOD, SOUND);
        am.load(SFX_BAD, SOUND);
        return am;
    }

    public static Music getIntroMusic(){
        return am.get(INTRO_MUSIC, MUSIC);
    }
    public static Music getSong1(){
        return am.get(SONG1_MUSIC);
    }
    public static Music getSong2(){
        return am.get(SONG2_MUSIC);
    }

    public static Sound getGoodSound(){ return am.get(SFX_GOOD, SOUND); }
    public static Sound getOkSound(){ return am.get(SFX_OK, SOUND); }
    public static Sound getBadSound(){ return am.get(SFX_BAD, SOUND); }

    public static TextureAtlas.AtlasRegion getMoonFrame(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("City/FannyMoon");
    }
    public static BitmapFont getFont(){
        return am.get(FONT, BITMAP_FONT);
    }

    public static TextureAtlas.AtlasRegion getBoat(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("City/Beast");
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
    public static TextureRegion getSpacebarFrame(String state){
        if(state == States.DEFAULT){
            return am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(SPACEBAR).get(0);
        }else{
            return am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions(SPACEBAR).get(1);
        }
    }

    public static TextureAtlas.AtlasRegion getTitleFrame(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(BANNER);
    }

    public static TextureAtlas.AtlasRegion getBackLights(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(BACKLIGHTS);
    }

    public static TextureAtlas.AtlasRegion getFrontLights(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(FRONTLIGHTS);
    }

    public static TextureAtlas.AtlasRegion getBackgroundFrame(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(BG);
    }

    public static TextureAtlas.AtlasRegion getMenuBackground(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(BG_MENU);
    }

    public static TextureAtlas.AtlasRegion getFrontWave(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(WAVE_1);
    }
    public static TextureAtlas.AtlasRegion getBackWave(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion(WAVE_2);
    }
    public static Array<TextureAtlas.AtlasRegion> getBackCityFrames(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegions(BG_CITY);
    }
    public static Array<TextureAtlas.AtlasRegion> getFrontCityFrames(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegions(FG_CITY);
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
    private static String SFX_OK = "music/ok.mp3";
    private static String SFX_GOOD = "music/good.mp3";
    private static String SFX_BAD = "music/bad.mp3";
    private static String TV_COVER = "tv";
    private static String KNOB = "Knob";
    private static String SPACEBAR = "SPACE";
    private static String BANNER = "Banner";
    private static String BACKLIGHTS = "LightingBack";
    private static String FRONTLIGHTS = "LightingFront";
    private static String BG = "Background/Background";
    private static String BG_MENU = "Background/Background1";
    private static String WAVE_1 = "City/Wave1";
    private static String WAVE_2 = "City/Wave2";
    private static String BG_CITY = "City/SkylineBack";
    private static String FG_CITY = "City/SkylineFront";


    private static String A = "Keys/A";
    private static String S = "Keys/S";
    private static String D = "Keys/D";
    private static String F = "Keys/F";

    private static String J = "Keys/J";
    private static String K = "Keys/K";
    private static String L = "Keys/L";
    private static String SEMI = "Keys/Semi";

//    private static String LEFT = "Keys/Left";
//    private static String UP = "Keys/Up";
//    private static String DOWN = "Keys/Down";
//    private static String RIGHT = "Keys/Right";


    public static TextureAtlas.AtlasRegion getTargetKeyFrame(int key){
        TextureAtlas atlas = am.get(SPRITE_ATLAS, TEXTURE_ATLAS);
        String regionKey = "TargetKeys/";
        TextureAtlas.AtlasRegion region = null;
        switch(key){
            case Input.Keys.A:
                region = atlas.findRegion(regionKey + "targetA");
                break;
            case Input.Keys.W:
                region = atlas.findRegion(regionKey + "targetW");
                break;
            case Input.Keys.S:
                region = atlas.findRegion(regionKey + "targetS");
                break;
            case Input.Keys.D:
                region = atlas.findRegion(regionKey + "targetD");
                break;
            case Input.Keys.LEFT:
                region = atlas.findRegion(regionKey + "targetLeft");
                break;
            case Input.Keys.UP:
                region = atlas.findRegion(regionKey + "targetUp");
                break;
            case Input.Keys.DOWN:
                region = atlas.findRegion(regionKey + "targetDown");
                break;
            case Input.Keys.RIGHT:
                region = atlas.findRegion(regionKey + "targetRight");
                break;
        }
        return region;
    }

    public static TextureAtlas.AtlasRegion getDefaultKeyFrame(int key, boolean isP1){
        return getKeyCodeFrame(key, isP1, 0);
    }

    public static TextureAtlas.AtlasRegion getPressedKeyFrame(int key, boolean isP1){
        return getKeyCodeFrame(key, isP1, 1);
    }

    private static TextureAtlas.AtlasRegion getKeyCodeFrame(int key, boolean isP1, int index) {
        TextureAtlas atlas = am.get(ANI_ATLAS, TEXTURE_ATLAS);
        String regionKey = isP1 ? "Player1/" : "Player2/";
        TextureAtlas.AtlasRegion region = null;
        switch(key){
            case Input.Keys.A:
                region = atlas.findRegions(regionKey + A).get(index);
                break;
            case Input.Keys.S:
                region = atlas.findRegions(regionKey + S).get(index);
                break;
            case Input.Keys.D:
                region = atlas.findRegions(regionKey + D).get(index);
                break;
            case Input.Keys.F:
                region = atlas.findRegions(regionKey + F).get(index);
                break;
            case Input.Keys.J:
                region = atlas.findRegions(regionKey + J).get(index);
                break;
            case Input.Keys.K:
                region = atlas.findRegions(regionKey + K).get(index);
                break;
            case Input.Keys.L:
                region = atlas.findRegions(regionKey + L).get(index);
                break;
            case Input.Keys.SEMICOLON:
                region = atlas.findRegions(regionKey + SEMI).get(index);
                break;
        }

        return region;
    }


}
