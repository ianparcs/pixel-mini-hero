package ph.sparcsky.miniheroes.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ph.sparcsky.miniheroes.Constant;

public class Actor extends Entity {

    private Animation<TextureRegion> animation;
    private TextureRegion currentFrame;
    private float stateTime;

    public Actor(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void update(float delta) {
        stateTime += delta;

        width = currentFrame.getRegionWidth() * Constant.Math.SCALE;
        height = currentFrame.getRegionHeight() * Constant.Math.SCALE;
    }

    @Override
    public void draw(SpriteBatch batch) {
        currentFrame = animation.getKeyFrame(stateTime, true);

        sprite.setRegion(currentFrame);
        sprite.setFlip(flipX, flipY);
        sprite.setSize(width, height);

        super.draw(batch);
    }

}
