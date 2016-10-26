package hiof.magnus.mpgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hiof.magnus.mpgame.MPGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "MPGame";
        config.width = 272;
        config.height = 408;
		new LwjglApplication(new MPGame(), config);
	}
}
