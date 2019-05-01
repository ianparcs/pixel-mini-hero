package ph.sparcsky.miniheroes.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.HashMap;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.WorldRenderer;
import ph.sparcsky.miniheroes.asset.Asset;

public class Player extends Entity {

    private TextureRegion currentFrame;
    private float stateTime;
    private boolean flip;

    private Body body;
    private HashMap<State, Animation<TextureRegion>> stateAnim;

    public Player(Asset asset) {
        this.x = x * 5;
        this.y = y * 6;

        stateAnim = new HashMap<State, Animation<TextureRegion>>();
        stateAnim.put(State.IDLE, asset.getAnimation(.09f, Constant.Anim.HERO_IDLE, Animation.PlayMode.LOOP));
        stateAnim.put(State.RUN, asset.getAnimation(.09f, Constant.Anim.HERO_RUN, Animation.PlayMode.LOOP));
        stateAnim.put(State.JUMP, asset.getAnimation(.05f, Constant.Anim.HERO_JUMP, Animation.PlayMode.LOOP));

        width = stateAnim.get(State.RUN).getKeyFrame(stateTime).getRegionWidth() / Constant.PIXEL_SIZE;
        height = stateAnim.get(State.RUN).getKeyFrame(stateTime).getRegionHeight() / Constant.PIXEL_SIZE;
    }

    public void definePlayer(WorldRenderer world) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x, y);

        PolygonShape footShape = new PolygonShape();
        PolygonShape bodyShape = new PolygonShape();

        footShape.setAsBox(width / 2, (height / 2) / 2, new Vector2(0, -0.5f), 0);
        bodyShape.setAsBox(width / 2, height / 2);

        FixtureDef footFixture = new FixtureDef();
        footFixture.shape = footShape;
        footFixture.isSensor = true;

        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.friction = 0f;
        bodyFixtureDef.density = 1f;
        bodyFixtureDef.restitution = 0.0f;
        bodyFixtureDef.shape = bodyShape;

        body = world.createBody(bodyDef);
        body.createFixture(footFixture).setUserData("FOOT");
        body.createFixture(bodyFixtureDef).setUserData("BODY");

        footShape.dispose();
        bodyShape.dispose();

    }

    public void update(float delta) {
        stateTime += delta;

        currentFrame = stateAnim.get(getState()).getKeyFrame(stateTime);
        width = stateAnim.get(State.RUN).getKeyFrame(stateTime).getRegionWidth() / Constant.PIXEL_SIZE;
        height = stateAnim.get(State.RUN).getKeyFrame(stateTime).getRegionHeight() / Constant.PIXEL_SIZE;

        x = body.getPosition().x - width / 2;
        y = body.getPosition().y - height / 2;

        move();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, flip ? x + width : x, y, flip ? -width : width, height);
    }

    private void move() {
        float speedX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            speedX = 5;
            flip = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            speedX = -5;
            flip = true;
        }

        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }

    public State getState() {
        State state = State.IDLE;
        if (body.getLinearVelocity().x >= 5 || body.getLinearVelocity().x < 0) {
            state = State.RUN;
        }

        if ((int)body.getLinearVelocity().y > 0) {
            System.out.println(body.getLinearVelocity().y);
            state = State.JUMP;
        }
        if (body.getLinearVelocity().y == 0 && body.getLinearVelocity().x == 0) {
            state = State.IDLE;
        }
        return state;
    }

    public void jump() {
        float impulse = body.getMass() * 7;
        body.applyLinearImpulse(new Vector2(body.getLinearVelocity().x, impulse), body.getWorldCenter(), true);
    }

    private enum State {
        IDLE, RUN, JUMP
    }
}
