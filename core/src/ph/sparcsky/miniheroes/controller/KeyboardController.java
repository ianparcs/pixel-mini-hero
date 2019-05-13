package ph.sparcsky.miniheroes.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;

public class KeyboardController implements GameController {

    private HashMap<Pad, Integer> keys;

    public KeyboardController() {
        keys = new HashMap<Pad, Integer>();
        keys.put(Pad.A, Input.Keys.SPACE);
        keys.put(Pad.B, Input.Keys.X);
        keys.put(Pad.LEFT, Input.Keys.LEFT);
        keys.put(Pad.RIGHT, Input.Keys.RIGHT);
    }

    @Override
    public boolean isKeyPressed(Pad pad) {
        return Gdx.input.isKeyPressed(keys.get(pad));
    }

    @Override
    public boolean isKeyJustPressed(Pad pad) {
        return Gdx.input.isKeyJustPressed(keys.get(pad));
    }
}
