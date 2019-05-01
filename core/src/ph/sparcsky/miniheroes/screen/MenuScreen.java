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
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.GameCore;
import ph.sparcsky.miniheroes.MapBodyBuilder;
import ph.sparcsky.miniheroes.Scene2DBuilder;
import ph.sparcsky.miniheroes.WorldRenderer;
import ph.sparcsky.miniheroes.entity.Actor;

public class MenuScreen extends BaseScreen {

    private Stage stage;

    private List<Actor> actors;
    private WorldRenderer world;

    public MenuScreen(GameCore game) {
        super(game);

        TiledMap tiledMap = asset.get(Constant.MENU_BG);
        world = new WorldRenderer(tiledMap);

        Actor swordActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_SWORD_ATTACK, Animation.PlayMode.LOOP));
        Actor piqueActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_PIQUE_ATTACK, Animation.PlayMode.LOOP));
        Actor axeActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_AXE_ATTACK, Animation.PlayMode.LOOP));
        Actor bowActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_BOW_ATTACK, Animation.PlayMode.LOOP));
        Actor jumpyActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_JUMP, Animation.PlayMode.LOOP));
        Actor runnyActor = new Actor(asset.getAnimation(.09f, Constant.Anim.HERO_RUN, Animation.PlayMode.LOOP));

        swordActor.setPosition(12, 6.6f);
        piqueActor.setPosition(18, 6.9f);
        jumpyActor.setPosition(20f, 14);
        bowActor.setPosition(24, 15);
        axeActor.setPosition(4, 13);
        runnyActor.setPosition(15, 14);
        piqueActor.setFlip(true, false);
        bowActor.setFlip(true, false);

        actors = new ArrayList<Actor>();
        actors.add(jumpyActor);
        actors.add(swordActor);
        actors.add(runnyActor);
        actors.add(piqueActor);
        actors.add(bowActor);
        actors.add(axeActor);
    }

    @Override
    public void show() {

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = asset.get(Constant.Font.MAIN);

        Label.LabelStyle menuOptionStyle = new Label.LabelStyle();
        menuOptionStyle.font = asset.get(Constant.Font.SMALL);

        Table table = new Table().top();
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

        stage = new Stage(new FitViewport(width, height), batch);
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
        world.update();
        for (Actor actor : actors) actor.update(delta);
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        clearScreen(0, 0, 0);

        world.draw(batch);
        batch.begin();
        for (Actor actor : actors) actor.draw(batch);
        batch.end();

        stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        stage.dispose();
        System.out.println("Menu screen dispose");
    }

}
