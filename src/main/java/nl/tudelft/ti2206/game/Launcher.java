package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.log.Logger.LogLevel;

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
	
	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * Main entry point for the game.
	 * 
	 * @param args
	 *            The command line arguments. These are ignored.
	 */
	public static void main(String[] args) {
		
		// Launcher.java [loglevel] [log to file]
		// `loglevel` = { none, info, error, debug, all }
		// `log to file` = { true, 1, enable } 
		// both arguments are parsed case insensitive, so DEBUG is the
		// same as debug, or deBuG
		
		
		// remove this later!
		System.out.println("arguments passed:");
		for (int i = 0; i < args.length; i++)
			System.out.println("args[" + i + "] = " + args[i]);
		
		// default logging level, set this to Level.NONE later!
		LogLevel logLevel = LogLevel.ALL;
		
		if (args.length > 0 && !args[0].isEmpty()) {
			if (args[0].equalsIgnoreCase("all"))
				logLevel = LogLevel.ALL;
			else if (args[0].equalsIgnoreCase("info"))
				logLevel = LogLevel.INFO;
			else if (args[0].equalsIgnoreCase("error"))
				logLevel = LogLevel.ERROR;
			else if (args[0].equalsIgnoreCase("debug"))
				logLevel = LogLevel.DEBUG;
			else if (args[0].equalsIgnoreCase("none"))
				logLevel = LogLevel.NONE;
		}
		
		System.out.println("setting log level to " + logLevel);
		logger.setLevel(logLevel);
		
		if (logLevel.ordinal() > LogLevel.NONE.ordinal() && args.length > 1 && !args[1].isEmpty()) {
			if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("enable")) {
				System.out.println("logging to file...");
				logger.setLogFile("2048");
			}
		}
		
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
