package ph.sparcsky.miniheroes;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Body2DBuilder {


    public Body buildPlayer(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(player.getX(), player.getY());

        CircleShape circle = new CircleShape();
        circle.setRadius(.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        return body;
    }
}
