package nl.tudelft.ti2206.game;

import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;

/**
 * The Game class is used to hook into LibGDX. It implements LibGDX's
 * ApplicationListener interface.
 * 
 * Through implementing this interface, we receive events from LibGDX by which
 * we control the game.
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

	@Override
	public void create() {
		/* Load all our assets. */
		AssetHandler.load();
		AssetHandler.loadSkinFile(Gdx.files.internal("src/main/resources/skin.json"));

		setScreen(new Menu());
	}

	@Override
	public void dispose() {
		getScreen().dispose();
		AssetHandler.dispose();
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
