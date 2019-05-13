package ph.sparcsky.miniheroes.controller;

public interface GameController  {

    boolean isKeyPressed(Pad pad);

    boolean isKeyJustPressed(Pad pad);

    enum Pad {
        A, B, LEFT, RIGHT, UP, DOWN

    }

}
