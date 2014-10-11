package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.net.Networking;
import nl.tudelft.ti2206.screens.menuscreens.MenuScreen;
import nl.tudelft.ti2206.state.ContinuingState;
import nl.tudelft.ti2206.state.GameState;
import nl.tudelft.ti2206.state.LostState;
import nl.tudelft.ti2206.state.RunningState;
import nl.tudelft.ti2206.state.WaitingState;
import nl.tudelft.ti2206.state.WonState;

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

	/** The current state of the game. */
	private static GameState curState;
	
	/** List of possible States*/
	private static GameState runningState = new RunningState();
	private static GameState wonState = new WonState();
	private static GameState lostState = new LostState();
	private static GameState continuingState = new ContinuingState();
	private static GameState waitingState = new WaitingState();

	/** The AssetHandler instance. */
	private AssetHandler assetHandler = AssetHandler.getInstance();

	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	@Override
	public void create() {
		logger.info(className, "Launching game...");
		logger.debug(className, "Skin is loaded and menu screen is launched.");

		/* Load all our assets. */
		assetHandler.load();
		assetHandler.loadSkinFile(Gdx.files
				.internal("skin.json"));

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
		logger.info(className, "Closing game...");
		Networking.getInstance().disconnect();
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
		logger.info("TwentyFourtyGame", "Changing current game state to '"
				+ state.getClass().getSimpleName() + "'.");
		curState = state;
	}

	/**
	 * @return True if the game is currently running.
	 */
	public static boolean isRunning() {
		return (curState instanceof RunningState);
	}

	/**
	 * @return True if the current game is lost.
	 */
	public static boolean isLost() {
		return (curState instanceof LostState);
	}

	/**
	 * @return True if the current game is won.
	 */
	public static boolean isWon() {
		return (curState instanceof WonState);
	}

	/**
	 * @return True if game is in continuing state.
	 */
	public static boolean isContinuing() {
		return 	(curState instanceof ContinuingState);
	}
	
	/**
	 * @return True if game is in waiting state.
	 */
	public static boolean isWaiting() {
		return (curState instanceof WaitingState);
	}
	

	/**
	 * @return GameState The running game state.
	 */
	public static GameState getRunningState() {
		return runningState;
	}

	/**
	 * @return GameState The won game state.
	 */
	public static GameState getWonState() {
		return wonState;
	}

	/**
	 * @return GameState The lost game state.
	 */
	public static GameState getLostState() {
		return lostState;
	}

	/**
	 * @return GameState The continuing game state.
	 */
	public static GameState getContinuingState() {
		return continuingState;
	}

	/**
	 * @return GameState The waiting game state.
	 */
	public static GameState getWaitingState() {
		return waitingState;
	}

	/** Mock insertion method. Used for testing only. */
	public void setMockObjects(ScreenHandler screenHandlerMock,
			AssetHandler assetHandlerMock, Logger loggerMock) {
		screenHandler = screenHandlerMock;
		assetHandler = assetHandlerMock;
		logger = loggerMock;
	}
}
