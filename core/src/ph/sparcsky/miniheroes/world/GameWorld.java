package ph.sparcsky.miniheroes.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.collision.CollisionListener;


public class GameWorld {

    private Box2DDebugRenderer debugRenderer;
    private MapRenderer mapRenderer;
    private World world;

    private OrthographicCamera camera;
    private Viewport gamePort;
    private boolean debug;

    public GameWorld(TiledMap map) {

        camera = new OrthographicCamera();
        gamePort = new StretchViewport(32, 32, camera);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, Constant.Math.GRAVITY), true);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Constant.Math.SCALE);
        debugRenderer = new Box2DDebugRenderer();

        MapLayers mapLayers = map.getLayers();
        for (MapLayer mapLayer : mapLayers) {
            boolean sensor = mapLayer.getName().equalsIgnoreCase("ladder");
            MapBodyBuilder.buildShapes(this, mapLayer, sensor);
        }
    }


    public Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public void update(float delta) {
        camera.update();
        mapRenderer.setView(camera);
        world.step(delta, 6, 2);

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) debug = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) debug = false;
    }

    public void draw(SpriteBatch batch) {
        if (debug) {
            debugRenderer.render(world, camera.combined);
        }

        batch.setProjectionMatrix(camera.combined);
        mapRenderer.render();

    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }


    public void setContactListener(CollisionListener collisionListener) {
        world.setContactListener(collisionListener);
    }

    public boolean isCollide(String objectOne, String objectTwo, Contact contact) {
        Object a = contact.getFixtureA().getUserData();
        Object b = contact.getFixtureB().getUserData();
        return a.equals(objectOne) && b.equals(objectTwo);
    }
}
