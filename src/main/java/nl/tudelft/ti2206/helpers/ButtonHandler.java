package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.buttons.RestartButton;

public class ButtonHandler {

	private static RestartButton restartButton;

	/**
	 * Initializes all buttons.
	 */
	public static void load() {
		restartButton = initRestartButton();
	}

	/**
	 * 
	 * @return the RestartButton object
	 */
	public static RestartButton getRestartButton() {
		return restartButton;
	}

	/**
	 * Initialize a restart button at its designated location.
	 */
	private static RestartButton initRestartButton() {
		return new RestartButton(AssetHandler.newgame.getX(),
				AssetHandler.newgame.getY(), AssetHandler.newgame.getWidth(),
				AssetHandler.newgame.getHeight(), AssetHandler.newgame,
				AssetHandler.newgame);
	}
}
