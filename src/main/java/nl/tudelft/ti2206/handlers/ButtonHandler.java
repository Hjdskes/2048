package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.game.GameWorld;

/**
 * The ButtonHandler is responsible for managing the buttons.
 */
public class ButtonHandler {
	/** The width of the game window. */
	private static final int GAME_WIDTH = 600;

	/** The height of the game window. */
	private static final int GAME_HEIGHT = 600;

	/** The width of a gap, which is between all the tiles. */
	private static final int GAP = 15;

	/** The restart button. */
	private static RestartButton restartButton;

	/** The continue button. */
	private static ContinueButton continueButton;

	/**
	 * Initializes all buttons.
	 */
	public static void load() {
		restartButton = new RestartButton(GAME_WIDTH / 2
				- AssetHandler.restartbutton.getWidth() / 2, GAME_HEIGHT - GAP
				- AssetHandler.restartbutton.getHeight(),
				AssetHandler.restartbutton.getWidth(),
				AssetHandler.restartbutton.getHeight());

		continueButton = new ContinueButton(GAME_WIDTH / 2
				- AssetHandler.continuebutton.getWidth() / 2, GAME_HEIGHT - GAP
				* 3 - AssetHandler.continuebutton.getHeight() * 2,
				AssetHandler.continuebutton.getWidth(),
				AssetHandler.continuebutton.getHeight());
	}

	/**
	 * Delegates a touchDown event from the InputHandler to the correct button.
	 * 
	 * @param world
	 *            A reference to the current GameWorld, so the buttons can
	 *            interact with it.
	 * @param screenX
	 *            The x-coordinate of the touchDown event.
	 * @param screenY
	 *            The y-coordinate of the touchDown event.
	 * @return True if the touchDown event is processed, false otherwise.
	 */
	public static boolean touchDown(GameWorld world, int screenX, int screenY) {
		if (restartButton.isTouchDown(screenX, screenY)) {
			restartButton.onClick(world);
			return true;
		}

		if (continueButton.isTouchDown(screenX, screenY)) {
			continueButton.onClick(world);
			return true;
		}

		return false;
	}

	/**
	 * @return The RestartButton object.
	 */
	public static RestartButton getRestartButton() {
		return restartButton;
	}

	/**
	 * @return The ContinueButton object.
	 */
	public static ContinueButton getContinueButton() {
		return continueButton;
	}
}
