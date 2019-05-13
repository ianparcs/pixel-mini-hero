package ph.sparcsky.miniheroes.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.HashMap;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.asset.Asset;
import ph.sparcsky.miniheroes.collision.CollisionListener;
import ph.sparcsky.miniheroes.controller.GameInputProcessor;
import ph.sparcsky.miniheroes.world.GameWorld;

public class Player extends Entity {

    float speedX = 0;
    private TextureRegion currentFrame;
    private float stateTime;
    private boolean flip;
    private Body body;
    private HashMap<State, Animation<TextureRegion>> stateAnim;

    private boolean groundHit;
    private boolean canClimb;

    private GameInputProcessor inputProcessor;

    public Player(GameInputProcessor inputProcessor, Asset asset) {
        this.inputProcessor = inputProcessor;
        this.x = x * 5;
        this.y = y * 6;

        stateAnim = new HashMap<State, Animation<TextureRegion>>();
        stateAnim.put(State.IDLE, asset.getAnimation(.5f, Constant.Anim.HERO_IDLE, Animation.PlayMode.LOOP));
        stateAnim.put(State.RUN, asset.getAnimation(.1f, Constant.Anim.HERO_RUN, Animation.PlayMode.LOOP_PINGPONG));
        stateAnim.put(State.JUMP, asset.getAnimation(.05f, Constant.Anim.HERO_JUMP, Animation.PlayMode.LOOP));

        width = stateAnim.get(State.IDLE).getKeyFrame(stateTime).getRegionWidth() / Constant.PIXEL_SIZE;
        height = stateAnim.get(State.IDLE).getKeyFrame(stateTime).getRegionHeight() / Constant.PIXEL_SIZE;
    }

    public void definePlayer(GameWorld world) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x, y);

        PolygonShape foot = new PolygonShape();
        CircleShape bodyShape = new CircleShape();

        foot.setAsBox((width / 2) / 4, (height / 2) / 10, new Vector2(0, -0.5f), 0);
        bodyShape.setRadius((width / 2 + height / 2) / 2);

        FixtureDef footFixture = new FixtureDef();
        footFixture.shape = foot;
        footFixture.restitution = 0f;
        footFixture.isSensor = true;

        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.friction = 0f;
        bodyFixtureDef.density = 1f;
        bodyFixtureDef.restitution = 0.0f;
        bodyFixtureDef.shape = bodyShape;

        body = world.createBody(bodyDef);
        body.createFixture(footFixture).setUserData("hero.foot");
        body.createFixture(bodyFixtureDef).setUserData("hero.body");

        foot.dispose();
        bodyShape.dispose();
    }

    public void update(float delta) {
        stateTime += delta;

        currentFrame = stateAnim.get(getState()).getKeyFrame(stateTime);

        width = stateAnim.get(State.RUN).getKeyFrame(stateTime).getRegionWidth() / Constant.PIXEL_SIZE;
        height = stateAnim.get(State.RUN).getKeyFrame(stateTime).getRegionHeight() / Constant.PIXEL_SIZE;

        x = body.getPosition().x - width / 2;
        y = body.getPosition().y - height / 2;

        inputProcessor.update(this);

        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentFrame, flip ? x + width : x, y, flip ? -width : width, height);
    }

    public void moveRight() {
        speedX = 5;
        flip = false;
    }

    public void moveLeft() {
        speedX = -5;
        flip = true;
    }




    public State getState() {
        State state = State.IDLE;
        int bodyX = (int) body.getLinearVelocity().x;
        int bodyY = (int) body.getLinearVelocity().y;

        if (bodyX >= 5 || bodyX < 0) {
            state = State.RUN;
        }
        if (bodyY > 0) {
            state = State.JUMP;
        }

        return state;
    }

    public void jump() {
        float impulse = body.getMass() * 7;
        body.applyLinearImpulse(new Vector2(body.getLinearVelocity().x, impulse), body.getWorldCenter(), true);
    }

    public void climb() {
        body.setLinearVelocity(body.getLinearVelocity().x, 3);
    }

    public void setCollisionListener(final GameWorld world) {
        world.setContactListener(new CollisionListener() {
            @Override
            public void beginContact(Contact contact) {
                if (world.isCollide("ladder", "hero.body", contact)) {
                    canClimb = true;
                } else if (world.isCollide("ground", "hero.foot", contact)) {
                    groundHit = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (world.isCollide("ladder", "hero.body", contact)) {
                    canClimb = false;
                } else if (world.isCollide("ground", "hero.foot", contact)) {
                    groundHit = false;
                }
            }
        });
    }


    public boolean isCanClimb() {
        return canClimb;
    }

    public boolean isGroundHit() {
        return groundHit;
    }

    public void idle() {
        speedX = 0;
    }

    private enum State {
        IDLE, RUN, JUMP, CLIMB
    }
}
