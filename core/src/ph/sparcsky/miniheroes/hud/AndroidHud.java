package ph.sparcsky.miniheroes.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import ph.sparcsky.miniheroes.Constant;
import ph.sparcsky.miniheroes.asset.Asset;
import ph.sparcsky.miniheroes.controller.GameController;
import ph.sparcsky.miniheroes.controller.GamePadController;

public class AndroidHud extends Hud {

    private Button aButton;
    private Button bButton;
    private Button leftButton;
    private Button rightButton;
    private Button upButton;
    private Button downButton;

    public AndroidHud(Asset asset) {
        super(asset);

        Skin skin = asset.get(Constant.Data.UI);

        aButton = skin.get("a-button", Button.class);
        bButton = skin.get("b-button", Button.class);
        leftButton = skin.get("left-pad", Button.class);
        rightButton = skin.get("right-pad", Button.class);
        upButton = skin.get("up-pad", Button.class);
        downButton = skin.get("down-pad", Button.class);

        Container<Button> padLeft = new Container<Button>(leftButton).fill();
        Container<Button> padRight = new Container<Button>(rightButton).fill();
        Container<Button> padA = new Container<Button>(aButton).fill();
        Container<Button> padB = new Container<Button>(bButton).fill();
        Container<Button> padUp = new Container<Button>(upButton).fill();
        Container<Button> padDown = new Container<Button>(downButton).fill();

        Value padSize = Value.percentWidth(0.01f, table);

        Table padKey = new Table();
        padKey.add(padA).padLeft(padSize).height(Value.percentWidth(0.5f, padKey)).width(Value.percentWidth(0.5f, padKey)).grow().right();
        padKey.row();
        padKey.add(padB).padRight(padSize).height(Value.percentWidth(0.5f, padKey)).width(Value.percentWidth(0.5f, padKey)).grow().left();

        final Table padArrows = new Table();
        padArrows.add(padUp).width(Value.percentWidth(0.3f, padArrows)).colspan(2).center().grow();
        padArrows.row();
        padArrows.add(padLeft).width(Value.percentWidth(0.3f, padArrows)).grow();
        padArrows.add(padRight).width(Value.percentWidth(0.3f, padArrows)).grow();
        padArrows.row();
        padArrows.add(padDown).colspan(2).width(Value.percentWidth(0.3f, padArrows)).center().grow();

        Value dPadHeight = Value.percentHeight(.40f, table);
        Value dPadWidth = Value.percentWidth(.25f, table);
        Value buttonWidth = Value.percentWidth(.17f, table);
        Value buttonHeight = Value.percentHeight(.17f, table);

        table.add(padArrows).pad(Value.percentHeight(0.05f, table)).size(dPadWidth, dPadHeight).bottom().left().grow();
        table.add(padKey).pad(Value.percentHeight(0.15f, table)).size(buttonWidth, buttonHeight).bottom().right().grow();
        table.setColor(0,0,0,0.5f);
    }

    public void addGamePad(GamePadController gamePad) {
        gamePad.addButton(GameController.Pad.A, aButton);
        gamePad.addButton(GameController.Pad.B, bButton);
        gamePad.addButton(GameController.Pad.LEFT, leftButton);
        gamePad.addButton(GameController.Pad.RIGHT, rightButton);
        gamePad.addButton(GameController.Pad.UP, upButton);
        gamePad.addButton(GameController.Pad.DOWN, downButton);
    }
}
