package nl.tudelft.ti2206.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

 /** This class launches an instance of TwentyFourtyGame. */
public class Launcher {
	/** The width of the game window. */
	private static final int WIDTH = 600;

	/** The height of the game window. */
	private static final int HEIGHT = 600;

	/** The configuration for the game window. */
	LwjglApplicationConfiguration config;

	/**
	 * Main entry point for the game.
	 * 
	 * @param args
	 *            The command line arguments. These are ignored.
	 */
	public static void main(String[] args) {
		
		
		
		Launcher launcher = new Launcher();
		launcher.launch();
	}

	/** 
	 * Creates a new Launcher object. This object is automatically configured.
	 */
	public Launcher() {
		config = new LwjglApplicationConfiguration();
		configure();
	}

	/**
	 * Configures the application window.
	 */
	private void configure() {
		config.title = "2048";
		config.resizable = false;
		config.width = WIDTH;
		config.height = HEIGHT;

		/*
		 * Check if we're running on Mac OS or other, since Mac OS requires a
		 * different icon.
		 */
		if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
			config.addIcon("src/main/resources/images/icons/2048_mac.png",
					FileType.Internal);
		} else {
			config.addIcon(
					"src/main/resources/images/icons/2048_linux_windows.png",
					FileType.Internal);
		}
	}

	/**
	 * Launches a new LwjglApplication, containing the game.
	 */
	public void launch() {
		new LwjglApplication(new TwentyFourtyGame(), config);
	}
}
