package nl.tudelft.ti2206.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {
	private static final Logger log = LoggerFactory.getLogger(Launcher.class);
	
	public static void main(String[] args) {
		// This main method is called when starting your game.
		log.info("Starting game...");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "2048";
		config.width = 600;
		config.height = 600;
		new LwjglApplication(new TwentyFourtyGame(), config);
	}
}
