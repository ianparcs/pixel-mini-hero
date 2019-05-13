package ph.sparcsky.miniheroes.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import ph.sparcsky.miniheroes.Constant;

public class Asset {

    private FileHandleResolver fileResolver;
    private AssetManager assetManager;

    public Asset() {
        assetManager = new AssetManager();
        fileResolver = new InternalFileHandleResolver();
    }

    public void load() {
        loadTexture();
        loadMap();
        loadFont();
    }

    private void loadMap() {
        TmxMapLoader loader = new TmxMapLoader(fileResolver);

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Nearest;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        assetManager.setLoader(TiledMap.class, loader);
        assetManager.load(Constant.LEVEL_1, TiledMap.class,params);
        assetManager.load(Constant.MENU_BG, TiledMap.class,params);

    }

    private void loadTexture() {
        assetManager.load(Constant.Atlas.PLAYER, TextureAtlas.class);
        assetManager.load(Constant.Atlas.UI, TextureAtlas.class);

        SkinLoader.SkinParameter loader = new SkinLoader.SkinParameter(Constant.Atlas.UI);
        assetManager.load(Constant.Data.UI, Skin.class, loader);
    }

    private void loadFont() {
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(fileResolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(fileResolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter mainFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mainFont.fontFileName = Constant.Font.YOSTER;
        mainFont.fontParameters.size = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / 30;


        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = Constant.Font.YOSTER;
        smallFont.fontParameters.size = (Gdx.graphics.getWidth() + Gdx.graphics.getHeight()) / 60;


        assetManager.load(Constant.Font.MAIN, BitmapFont.class, mainFont);
        assetManager.load(Constant.Font.SMALL, BitmapFont.class, smallFont);

    }

    public boolean isLoadFinished() {
        return assetManager.update();
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    public Array<TextureAtlas.AtlasRegion> getFrame(String frames) {
        TextureAtlas gameAtlas = assetManager.get(Constant.Atlas.PLAYER);
        return gameAtlas.findRegions(frames);
    }

    public TextureAtlas getAtlas(String name) {
        return assetManager.get(name);
    }

    public Animation<TextureRegion> getAnimation(float duration, String frameName, Animation.PlayMode playMode) {
        return new Animation<TextureRegion>(duration, getFrame(frameName), playMode);
    }

    public <T> T get(String asset) {
        return assetManager.get(asset);
    }


    public void dispose() {
        assetManager.dispose();
    }
}
