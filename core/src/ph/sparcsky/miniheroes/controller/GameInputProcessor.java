package ph.sparcsky.miniheroes.controller;

import com.badlogic.gdx.Gdx;

import ph.sparcsky.miniheroes.entity.Player;

public class GameInputProcessor {

    private GameController input;

    public GameInputProcessor(GameController controller) {
        this.input = controller;
    }

    public void update(Player player) {
        if (input.isKeyJustPressed(GameController.Pad.A) &&player.isGroundHit()) {
            player.jump();
        } else if (input.isKeyPressed(GameController.Pad.LEFT)) {
            player.moveLeft();
        } else if (input.isKeyPressed(GameController.Pad.RIGHT)) {
            player.moveRight();
        } else {
            player.idle();
        }
    }
}
