package ph.sparcsky.miniheroes.screen;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Box2D;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.controller.GameController;
import ph.sparcsky.miniheroes.controller.GameInputProcessor;
import ph.sparcsky.miniheroes.controller.GamePadController;
import ph.sparcsky.miniheroes.controller.KeyboardController;
import ph.sparcsky.miniheroes.core.GameCore;
import ph.sparcsky.miniheroes.entity.Player;
import ph.sparcsky.miniheroes.hud.AndroidHud;
import ph.sparcsky.miniheroes.hud.Hud;
import ph.sparcsky.miniheroes.world.GameWorld;

public class PlayScreen extends BaseScreen {

    private Player player;

    private GameController controller;
    private GameWorld world;
    private Hud gameHud;

    public PlayScreen(GameCore game) {
        super(game);
        Box2D.init();
    }


    @Override
    public void show() {
        TiledMap map = asset.get(Constant.LEVEL_1);
        world = new GameWorld(map);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller = new GamePadController();
            gameHud = new AndroidHud(asset);
            ((AndroidHud)gameHud).addGamePad((GamePadController) controller);

        } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            gameHud = new Hud(asset);
            controller = new KeyboardController();
        }

        GameInputProcessor playerController = new GameInputProcessor(controller);

        player = new Player(playerController, asset);
        player.definePlayer(world);
        player.setCollisionListener(world);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        world.resize(width, height);
        gameHud.resize(width, height);
    }

    @Override
    public void update(float delta) {
        player.update(delta);
        world.update(delta);
        gameHud.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        clearScreen(0.160f, 0.117f, 0.192f);

        world.draw(batch);

        batch.begin();
        player.draw(batch);
        batch.end();

        gameHud.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        gameHud.dispose();
    }
}
