package com.roaringcatgames.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by barry on 8/22/15 @ 12:35 AM.
 */
public class Assets {

    private static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;

    private static final String ANI_ATLAS = "animations/animations.atlas";



    public static AssetManager am;

    private static Texture loadTexture(String fileName){
        return new Texture(Gdx.files.internal(fileName));
    }

    public static AssetManager load(){
        am = new AssetManager();

        am.load(ANI_ATLAS, TEXTURE_ATLAS);
//        TEST_TR = new TextureRegion(loadTexture("badlogic.jpg"));
//        TEST_ANI = new TextureRegion[]{
//                new TextureRegion(loadTexture("badlogic.jpg")),
//                new TextureRegion(loadTexture("badlogic2.jpg")),
//                new TextureRegion(loadTexture("badlogic3.jpg")),
//                new TextureRegion(loadTexture("badlogic4.jpg"))
//        };

        return am;
    }

    public static Array<TextureAtlas.AtlasRegion> getPlayerFrames(){
        Array<TextureAtlas.AtlasRegion> regions = am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions("test/badlogic");
        Gdx.app.log("Assets", "Region Count: " + regions.size);
        return regions;
    }
}
