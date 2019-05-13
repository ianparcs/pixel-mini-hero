package ph.sparcsky.miniheroes.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ph.sparcsky.miniheroes.asset.Asset;
import ph.sparcsky.miniheroes.core.GameCore;

public abstract class BaseScreen implements Screen {


    protected int width = Gdx.graphics.getWidth();
    protected int height = Gdx.graphics.getHeight();

    int widthHeight = width + height;

    ScreenManager screenManager;
    SpriteBatch batch;
    GameCore game;
    Asset asset;

    public BaseScreen(GameCore game) {
        this.screenManager = game.screenManager;
        this.batch = game.batch;
        this.asset = game.asset;
        this.game = game;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    protected void clearScreen(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 255);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        asset.dispose();
    }
}
