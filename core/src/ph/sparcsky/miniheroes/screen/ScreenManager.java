package ph.sparcsky.miniheroes.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import ph.sparcsky.miniheroes.core.GameCore;

public class ScreenManager {

    private Stack<BaseScreen> stack;
    private GameCore game;

    public ScreenManager(GameCore game) {
        this.game = game;
        stack = new Stack<BaseScreen>();
    }

    public void removeScreen(BaseScreen screen) {
        if (!stack.isEmpty()) stack.pop();
        stack.remove(screen);
    }

    public void setScreen(BaseScreen screen) {
        if (!stack.contains(screen)) {
            stack.push(screen);
            game.setScreen(screen);
        }
    }

    private Screen getCurrentScreen() {
        return stack.peek();
    }

    public void update(float delta) {
        stack.peek().update(delta);
    }

    public void render(SpriteBatch batch) {
        stack.peek().render(batch);
    }

    public void dispose() {
        getCurrentScreen().dispose();
    }
}
