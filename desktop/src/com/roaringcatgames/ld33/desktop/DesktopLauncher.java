package com.roaringcatgames.ld33.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.roaringcatgames.ld33.MonsterDancer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Become THE Monster";
		config.width = 1200;
		config.height = 800;
		new LwjglApplication(new MonsterDancer(), config);
	}
}
