package ph.sparcsky.miniheroes.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.HashMap;

public class GamePadController implements GameController {

    private HashMap<Pad, Button> buttonMap;

    public GamePadController() {
        buttonMap = new HashMap<Pad, Button>();
    }

    public void addButton(Pad pad, Button button) {
        buttonMap.put(pad, button);
    }

    private Button get(Pad pad) {
        return buttonMap.get(pad);
    }

    @Override
    public boolean isKeyPressed(Pad pad) {
        return get(pad).isPressed();
    }

    @Override
    public boolean isKeyJustPressed(Pad pad) {
        return get(pad).isPressed() && Gdx.input.justTouched();
    }

}
