package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.log.Logger.Level;
import nl.tudelft.ti2206.screens.MenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * The main game class. It keeps track of the screens via the screenHandler and
 * of the game state.
 * 
 * It also calls, via the screenHandler, the update and draw methods on all the
 * actors.
 */
public class TwentyFourtyGame extends Game {
	/** The width of the game */
	public static final int GAME_WIDTH = 600;

	/** The height of the game */
	public static final int GAME_HEIGHT = 600;

	/** The gap between all the elements. */
	public static final int GAP = 15;

	/** Enumeration indicating what state the game is currently in. */
	public static enum GameState {
		RUNNING, LOST, WON, CONTINUING
	}

	/** The current state of the game. */
	private static GameState curState;

	/** The AssetHandler instance. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** The singleton reference to the Logger class. */
	private static Logger logger = Logger.getInstance();
	
	/** Get current class name for logging output. */
	private final String className = this.getClass().getSimpleName();

	@Override
	public void create() {

		logger.message(Level.DEBUG, className,
				"Skin is loaded and menu screen is launched.");

		/* Load all our assets. */
		assetHandler.load();
		assetHandler.loadSkinFile(Gdx.files
				.internal("src/main/resources/skin.json"));

		/* Push a menu screen onto the screen stack. */
		screenHandler.add(new MenuScreen());
	}

	@Override
	public void render() {
		super.render();
		screenHandler.update();
		screenHandler.draw();
	}

	@Override
	public void dispose() {

		logger.message(Level.INFO, className, "Closing game...");

		screenHandler.dispose();
		assetHandler.dispose();

		logger.dispose();
	}

	@Override
	public void resize(int width, int height) {
		screenHandler.resize(width, height);
	}

	/**
	 * @return The current game state.
	 */
	public static GameState getState() {
		return curState;
	}

	/**
	 * Sets the new state of the game.
	 * 
	 * @param state
	 *            The new state of the game.
	 */
	public static void setState(GameState state) {
		logger.message(Level.INFO, "TwentyFourtyGame",
				"Changing current game state to " + state);
		curState = state;
	}

	/**
	 * @return True if the game is currently running.
	 */
	public static boolean isRunning() {
		return (curState == GameState.RUNNING);
	}

	/**
	 * @return True if the current game is lost.
	 */
	public static boolean isLost() {
		return (curState == GameState.LOST);
	}

	/**
	 * @return True if the current game is won.
	 */
	public static boolean isWon() {
		return (curState == GameState.WON);
	}

	/**
	 * @return True if game is in continuing state.
	 */
	public static boolean isContinuing() {
		return (curState == GameState.CONTINUING);
	}
}
