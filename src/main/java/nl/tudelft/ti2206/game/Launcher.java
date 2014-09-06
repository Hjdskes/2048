package nl.tudelft.ti2206.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * This class launches an instance of 2048 and sets up the logging facility.
 * 
 * @author group-21
 */
public class Launcher {
	/** The width of the window. */
	private static final int WIDTH = 600;
	/** The height of the window. */
	private static final int HEIGHT = 600;
	/** The logging facility. */
	private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

	/**
	 * Main entry point for the game.
	 * 
	 * @param args
	 *            command line arguments. These are ignored.
	 */
	public static void main(final String[] args) {
		// This main method is called when starting your game.
		LOG.info("Starting game...");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "2048";

		config.width = WIDTH;
		config.height = HEIGHT;

		// check if we're running on Mac OS and load the appropriate icon
		if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0)
			config.addIcon("src/main/resources/images/icons/2048_mac.png",
					FileType.Internal);
		// otherwise load icon for windows/linux
		else
			config.addIcon(
					"src/main/resources/images/icons/2048_linux_windows.png",
					FileType.Internal);

		LOG.info("Window width and height: " + config.width + "x"
				+ config.height);
		new LwjglApplication(new TwentyFourtyGame(), config);
	}
}
