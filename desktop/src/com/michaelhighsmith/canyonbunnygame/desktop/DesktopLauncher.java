package com.michaelhighsmith.canyonbunnygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.michaelhighsmith.canyonbunnygame.CanyonBunnyMain;

import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {
	private static boolean rebuildAtlas = false; //it was true, but i changed to false after the first build so that it doesn't crash trying to repack
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {

		if(rebuildAtlas){
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.atlasExtension="";
			settings.debug = drawDebugOutline;
			//(source folder that contains our image files, destination folder where the texture atlas should be created,
			// name of the description file that is needed to load and use the texture atlas)
			TexturePacker.process(settings, "assets-raw", "images", "canyonbunny.pack");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Canyon Bunny";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new CanyonBunnyMain(), config);
	}
}
