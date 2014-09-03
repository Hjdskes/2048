package nl.tudelft.ti2206.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		// This main method is called when starting your game.
		log.info("Starting game...");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "2048";

		config.width = WIDTH;
		config.height = HEIGHT;

		log.info("config.width = " + config.width + ", config.height = "
				+ config.height);

		new LwjglApplication(new TwentyFourtyGame(), config);
	}
}

