package com.pool.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pool.game.PoolMainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pool";
		cfg.width = 1280;
		cfg.height = 720;
//		cfg.fullscreen = true;
		new LwjglApplication(new PoolMainClass(), cfg);
	}
}
