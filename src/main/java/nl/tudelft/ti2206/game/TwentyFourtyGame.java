package nl.tudelft.ti2206.game;

import java.util.Stack;

import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
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

	/** A stack of screens. */
	private Stack<Screen> screens;

	/** The current state of the game. */
	private static GameState curState;

	@Override
	public void create() {
		/* Load all our assets. */
		AssetHandler.load();
		AssetHandler.loadSkinFile(Gdx.files.internal("src/main/resources/skin.json"));

		screens = new Stack<Screen>();
		addScreen(new MenuScreen());
	}

	@Override
	public void dispose() {
		getScreen().dispose();
		AssetHandler.dispose();
		for (Screen screen : screens) {
			if (screen == null) {
				continue;
			}
			screen.dispose();
		}
		screens.clear();
	}

	public void addScreen(Screen screen) {
		setScreen(screens.push(screen));
		System.out.println(screens.size());
	}

	public void popScreen() {
		if (screens.size() > 1) {
			Screen screen = screens.pop();
			setScreen(screens.peek());
			screen.hide();
			screen.dispose();
			//setScreen(screens.get(screens.size()-1));
			//screens.pop().dispose();
			//setScreen(screens.peek());
			//screen.dispose();
		}
		System.out.println(screens.size());
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
