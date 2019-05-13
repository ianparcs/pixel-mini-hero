package ph.sparcsky.miniheroes.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

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

        Container<Button> dPadLeft = new Container<Button>(leftButton).fill();
        Container<Button> dPadRight = new Container<Button>(rightButton).fill();
        Container<Button> aPadContainer = new Container<Button>(aButton).fill();
        Container<Button> bPadContainer = new Container<Button>(bButton).fill();
        Container<Button> upPadContainer = new Container<Button>(upButton).fill();
        Container<Button> downPadContainer = new Container<Button>(downButton).fill();

        Value padSize = Value.percentHeight(0.01f, table);

        Table buttonGroup = new Table();
        buttonGroup.add(bPadContainer).padLeft(padSize).width(Value.percentWidth(0.5f, buttonGroup)).grow().right();
        buttonGroup.row();
        buttonGroup.add(aPadContainer).padRight(padSize).width(Value.percentWidth(0.5f, buttonGroup)).grow().left();

        Table dPadGroup = new Table();
        dPadGroup.add(upPadContainer).width(Value.percentWidth(0.5f, buttonGroup)).colspan(2).center().grow();

        dPadGroup.row();
        dPadGroup.add(dPadLeft).width(Value.percentWidth(0.5f, buttonGroup)).grow();
        dPadGroup.add(dPadRight).width(Value.percentWidth(0.5f, buttonGroup)).grow();
        dPadGroup.row();

        dPadGroup.add(downPadContainer).colspan(2).width(Value.percentWidth(0.5f, buttonGroup)).center().grow();

        Value dPadHeight = Value.percentHeight(.23f, table);
        Value dPadWidth = Value.percentWidth(.23f, table);
        Value buttonWidth = Value.percentWidth(.16f, table);
        Value buttonHeight = Value.percentHeight(.16f, table);
        float pad = Value.percentHeight(.5f, table).get() + Value.percentWidth(.5f, table).get();

        table.add(dPadGroup).pad(pad).size(dPadWidth, dPadHeight).bottom().left().grow();
        table.add(buttonGroup).pad(pad).size(buttonWidth, buttonHeight).bottom().right().grow();

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
