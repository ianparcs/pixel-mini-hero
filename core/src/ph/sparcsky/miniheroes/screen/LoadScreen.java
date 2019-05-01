package ph.sparcsky.miniheroes.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ph.sparcsky.miniheroes.GameCore;

public class LoadScreen extends BaseScreen {

    public LoadScreen(GameCore game) {
        super(game);
    }

    @Override
    public void show() {
        asset.load();
    }

    @Override
    public void update(float delta) {
        System.out.println("Loading: " + asset.getProgress() * 100 + "%");

        if (asset.isLoadFinished()) {
            screenManager.removeScreen(this);
            screenManager.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        clearScreen(0, 0, 0);
    }
}
