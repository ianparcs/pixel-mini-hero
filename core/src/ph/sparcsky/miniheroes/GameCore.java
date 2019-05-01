package ph.sparcsky.miniheroes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ph.sparcsky.miniheroes.asset.Asset;
import ph.sparcsky.miniheroes.screen.LoadScreen;
import ph.sparcsky.miniheroes.screen.ScreenManager;

public class GameCore extends Game {

    public ScreenManager screenManager;
    public Asset asset;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        asset = new Asset();

        screenManager = new ScreenManager(this);
        screenManager.setScreen(new LoadScreen(this));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        screenManager.update(delta);
        screenManager.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        screenManager.dispose();
        System.out.println("Main core dispose");
    }
}
