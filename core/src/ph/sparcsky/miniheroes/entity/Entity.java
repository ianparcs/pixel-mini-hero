package ph.sparcsky.miniheroes.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected boolean flipX;
    protected boolean flipY;

    protected Sprite sprite;

    public Entity() {
        this.x = 1;
        this.y = 1;
        sprite = new Sprite();
    }

    public abstract void update(float delta);

    public void draw(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.setOriginCenter();
        sprite.draw(batch);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
