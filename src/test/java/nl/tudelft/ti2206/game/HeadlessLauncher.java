package nl.tudelft.ti2206.game;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/**
 * This class launches an instance of TwentyFourtyGame, via a headless launcher.
 * This is required for testing on the remote DevHub server.
 */
public class HeadlessLauncher extends Launcher {
	/** The configuration for the game window. */
	private HeadlessApplicationConfiguration config;

	/**
	 * Main entry point for the game.
	 * 
	 * @param args
	 *            The command line arguments. These are ignored.
	 */
	public static void main(final String[] args) {
		HeadlessLauncher launcher = new HeadlessLauncher();
		launcher.launch();
	}

	/**
	 * Creates a new Launcher object. This object is automatically configured.
	 */
	public HeadlessLauncher() {
		config = new HeadlessApplicationConfiguration();
	}

	@Override
	public void launch() {
		new HeadlessApplication(new HeadlessTwentyFourtyGame(), config);
	}
}
