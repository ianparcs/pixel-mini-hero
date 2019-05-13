package ph.sparcsky.miniheroes.core;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class Scene2DBuilder {

    public static Container<Label> buildLabel(String name, Label.LabelStyle style) {
        Container<Label> label = new Container<com.badlogic.gdx.scenes.scene2d.ui.Label>(new Label(name, style));
        label.setOrigin(label.getActor().getWidth() / 2, label.getActor().getHeight() / 2);
        label.setTransform(true);
        label.addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(0.1f, 0.1f, .5f),
                Actions.scaleTo(1, 1, 1))));

        return label;
    }
}
