package dnf.gupoublex;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetBase;

public class DesktopLauncher {
	public static void main (String[] arg) {
		String version = DesktopLauncher.class.getPackage().getSpecificationVersion();
		if(version == null)
			version = "1.0";
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//screen width&height
		config.width  = SetBase.desktop_width;
		config.height = SetBase.desktop_height;
		//preferences file path
		if(SharedLibraryLoader.isMac)
			config.preferencesDirectory = "GuPoubleXGame/DNF/";
		else if(SharedLibraryLoader.isLinux)
			config.preferencesDirectory = ".GuPoubleXGame/DNF/";
		else if(SharedLibraryLoader.isWindows)
			config.preferencesDirectory = "GuPoubleXGame/DNF/";
		//front FPS
		config.foregroundFPS = SetBase.fps;
		//backFPS
		config.backgroundFPS = SetBase.fps;
		//sound max
		config.audioDeviceBufferCount = SetBase.sdt;
		//sound play all
		config.audioDeviceSimultaneousSources = SetBase.sdt;
		//use OpenGL
		config.allowSoftwareMode = true;
		//title
		config.title = SetBase.AppName;
		//icon windows 32x32 or 16x16 mac 128x128
		config.addIcon("icon.png", FileType.Internal);
		//sys
		config.vSyncEnabled = true;
		//can change window
		config.resizable = false;
		config.forceExit = true;
		config.useGL30 = true;
		config.pauseWhenBackground = true;
		config.samples = 8;
		new LwjglApplication(new GuPoubleXGame(config.width, config.height, config.preferencesDirectory), config);
	}
}