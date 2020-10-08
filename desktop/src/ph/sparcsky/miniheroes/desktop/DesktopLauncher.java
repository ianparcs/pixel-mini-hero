package ph.sparcsky.miniheroes.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ph.sparcsky.miniheroes.core.GameCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mini Pixel Hero";
		config.width = 512;
		config.height = 512;
		config.forceExit = true;
		new LwjglApplication(new GameCore(), config);
	}
}
