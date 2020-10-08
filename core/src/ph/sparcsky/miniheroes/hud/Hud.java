package ph.sparcsky.miniheroes.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ph.sparcsky.miniheroes.asset.Asset;

public class Hud {

    protected Stage hud;
    protected Table table;

    public Hud(Asset asset) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        table = new Table();
        table.setFillParent(true);

        hud = new Stage(new FitViewport(width, height));
        hud.addActor(table);

        Gdx.input.setInputProcessor(hud);
    }

    public void update(float delta) {
        hud.act(delta);
    }

    public void hide() {
        table.remove();
    }

    public void resize(int screenWidth, int screenHeight) {
        hud.getViewport().update(screenWidth, screenHeight);
    }

    public void draw() {
        hud.draw();
    }

    public void dispose() {
        hud.dispose();
    }
}
