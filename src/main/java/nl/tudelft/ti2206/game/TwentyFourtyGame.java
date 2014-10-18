package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.menuscreens.MenuScreen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.net.Networking;
import nl.tudelft.ti2206.utils.states.ContinuingState;
import nl.tudelft.ti2206.utils.states.GameState;
import nl.tudelft.ti2206.utils.states.LostState;
import nl.tudelft.ti2206.utils.states.RunningState;
import nl.tudelft.ti2206.utils.states.WaitingState;
import nl.tudelft.ti2206.utils.states.WonState;

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
	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = TwentyFourtyGame.class
			.getSimpleName();

	/** The width of the game */
	public static final int GAME_WIDTH = 600;

	/** The height of the game */
	public static final int GAME_HEIGHT = 600;

	/** The gap between all the elements. */
	public static final int GAP = 15;

	/** The current state of the game. */
	private static GameState curState;

	/** The AssetHandler instance. */
	private static AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	@Override
	public void create() {
		logger.setLevel(PreferenceHandler.getInstance().getLogLevel());
		logger.info(CLASSNAME, "Launching game...");
		logger.debug(CLASSNAME, "Skin is loaded and menu screen is launched.");

		/* Load all our assets. */
		assetHandler.load();
		assetHandler.loadSkinFile(Gdx.files.internal("skin.json"));

		/* Show a menu screen. */
		screenHandler.set(new MenuScreen());
	}

	@Override
	public void render() {
		super.render();
		screenHandler.update();
		screenHandler.draw();
	}

	@Override
	public void dispose() {
		logger.info(CLASSNAME, "Closing game...");
		Networking.getInstance().disconnect();
		screenHandler.dispose();
		assetHandler.dispose();
		logger.dispose();
	}

	/**
	 * Sets the new state of the game.
	 * 
	 * @param state
	 *            The new state of the game.
	 */
	public static void setState(GameState state) {
		logger.info("TwentyFourtyGame", "Changing current game state to '"
				+ state.getClass().getSimpleName() + "'.");
		curState = state;
	}


	/**
	 * @return The current game state.
	 */
	public static GameState getState() {
		return curState;
	}

	/**
	 * @return True if the game is currently running.
	 */
	public static boolean isRunning() {
		return curState instanceof RunningState;
	}

	/**
	 * @return True if the current game is lost.
	 */
	public static boolean isLost() {
		return curState instanceof LostState;
	}

	/**
	 * @return True if the current game is won.
	 */
	public static boolean isWon() {
		return curState instanceof WonState;
	}

	/**
	 * @return True if game is in continuing state.
	 */
	public static boolean isContinuing() {
		return curState instanceof ContinuingState;
	}

	/**
	 * @return True if game is in waiting state.
	 */
	public static boolean isWaiting() {
		return curState instanceof WaitingState;
	}

	/** Mock insertion method. Used for testing only. */
	public void setMockObjects(ScreenHandler screenHandlerMock,
			AssetHandler assetHandlerMock, Logger loggerMock) {
		screenHandler = screenHandlerMock;
		assetHandler = assetHandlerMock;
		logger = loggerMock;
	}
}
