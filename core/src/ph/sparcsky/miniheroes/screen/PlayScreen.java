package ph.sparcsky.miniheroes.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Contact;

import ph.sparcsky.miniheroes.CollisionListener;
import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.GameCore;
import ph.sparcsky.miniheroes.MapBodyBuilder;
import ph.sparcsky.miniheroes.WorldRenderer;
import ph.sparcsky.miniheroes.entity.Player;

public class PlayScreen extends BaseScreen {

    private static int jumpCount = 0;
    private WorldRenderer world;
    private Player player;
    private boolean groundHit;

    public PlayScreen(GameCore game) {
        super(game);
        Box2D.init();
    }

    @Override
    public void show() {
        TiledMap map = asset.get(Constant.LEVEL_1);
        world = new WorldRenderer(map);

        player = new Player(asset);
        player.definePlayer(world);

        MapObjects objects = map.getLayers().get("ground").getObjects();
        MapBodyBuilder.buildShapes(world, objects);

        world.getWorld().setContactListener(new CollisionListener() {
            @Override
            public void beginContact(Contact contact) {
                groundHit = true;
            }

            @Override
            public void endContact(Contact contact) {
                groundHit = false;
            }

        });
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        world.resize(width, height);
    }


    @Override
    public void update(float delta) {
        player.update(delta);
        world.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && groundHit) {
            player.jump();
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        clearScreen(0, 1, 1);

        world.draw(batch);

        batch.begin();
        player.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }
}
