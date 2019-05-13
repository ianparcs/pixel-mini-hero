package ph.sparcsky.miniheroes.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.core.GameCore;
import ph.sparcsky.miniheroes.core.Scene2DBuilder;
import ph.sparcsky.miniheroes.entity.Actor;
import ph.sparcsky.miniheroes.world.GameWorld;

public class MenuScreen extends BaseScreen {

    private Stage stage;

    private GameWorld world;
    private Actor jumpyActor;
    private Actor runnyActor;

    public MenuScreen(GameCore game) {
        super(game);

        TiledMap tiledMap = asset.get(Constant.MENU_BG);
        world = new GameWorld(tiledMap);

        jumpyActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_JUMP, Animation.PlayMode.LOOP));
        runnyActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_IDLE, Animation.PlayMode.LOOP));

        jumpyActor.setPosition(18, 6);
        runnyActor.setPosition(6, 11);
    }

    @Override
    public void show() {

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = asset.get(Constant.Font.MAIN);

        Label.LabelStyle menuOptionStyle = new Label.LabelStyle();
        menuOptionStyle.font = asset.get(Constant.Font.SMALL);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Container<Label> lblTitle = Scene2DBuilder.buildLabel("Mini Pixel Heroes", titleStyle);
        Container<Label> lblPlay = Scene2DBuilder.buildLabel("Play", menuOptionStyle);
        Container<Label> lblSettings = Scene2DBuilder.buildLabel("Settings", menuOptionStyle);
        Container<Label> lblCredit = Scene2DBuilder.buildLabel("Credits", menuOptionStyle);
        Container<Label> lblExit = Scene2DBuilder.buildLabel("Exit", menuOptionStyle);

        table.add(lblTitle).padTop(height * .09f).padBottom(height * .09f).row();
        table.add(lblPlay).padBottom(widthHeight * .01f).row();
        table.add(lblSettings).padBottom(widthHeight * .01f).row();
        table.add(lblCredit).padBottom(widthHeight * .01f).row();
        table.add(lblExit).padBottom(widthHeight * .01f).row();
        table.padBottom(widthHeight * .2f);

        stage = new Stage(new StretchViewport(width, height), batch);
        stage.addActor(table);

        lblPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.setScreen(new PlayScreen(game));
            }
        });

        lblExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        world.resize(width, height);
    }

    @Override
    public void update(float delta) {
        world.update(delta);
        jumpyActor.update(delta);
        runnyActor.update(delta);
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        clearScreen(0.160f, 0.117f, 0.192f);

        world.draw(batch);

        batch.begin();
        jumpyActor.draw(batch);
        runnyActor.draw(batch);
        batch.end();

        stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        stage.dispose();
    }

}
