package ph.sparcsky.miniheroes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class WorldRenderer {

    private Box2DDebugRenderer debugRenderer;
    private MapRenderer mapRenderer;
    private World world;

    public OrthographicCamera camera;
    private Viewport gamePort;
    private boolean debug;

    public WorldRenderer(TiledMap tiledMap) {

        camera = new OrthographicCamera();
        gamePort = new StretchViewport(32, 32, camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, Constant.Math.GRAVITY), true);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Constant.Math.SCALE);
        debugRenderer = new Box2DDebugRenderer();

    }


    public Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public void update() {
        camera.update();
        mapRenderer.setView(camera);

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) debug = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) debug = false;

    }

    public void draw(SpriteBatch batch) {
        mapRenderer.render();

        if (debug) {
            debugRenderer.render(world, camera.combined);
        }

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        batch.setProjectionMatrix(camera.combined);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

    public World getWorld() {
        return world;
    }
}
