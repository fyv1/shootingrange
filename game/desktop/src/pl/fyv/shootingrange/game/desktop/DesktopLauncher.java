package pl.fyv.shootingrange.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.fyv.shootingrange.game.ShootingRange;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "shooting range";
		config.width = 1280;
		config.height = 720;
		config.pauseWhenMinimized = false;
		config.addIcon("img/ico/ico.png", Files.FileType.Internal);
		new LwjglApplication(new ShootingRange(), config);
	}
}
