package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;

/**
 * The ButtonHandler is responsible of managing the buttons.
 *
 * @author group-21
 */
public class ButtonHandler {
	/** The restart button. */
	private static RestartButton restartButton;
	/** The continue button. */
	private static ContinueButton continueButton;

	/**
	 * Initializes all buttons.
	 */
	public static void load() {
		restartButton = new RestartButton(AssetHandler.newgame.getX(),
				AssetHandler.newgame.getY(), AssetHandler.newgame.getWidth(),
				AssetHandler.newgame.getHeight(), AssetHandler.newgame,
				AssetHandler.newgame);
		
		continueButton = new ContinueButton(AssetHandler.continuebutton.getX(),
				AssetHandler.continuebutton.getY(), AssetHandler.continuebutton.getWidth(),
				AssetHandler.continuebutton.getHeight(), AssetHandler.continuebutton,
				AssetHandler.continuebutton);
	}

	/**
	 * Gets the button used to initiate a restart.
	 * @return The RestartButton object.
	 */
	public static RestartButton getRestartButton() {
		return restartButton;
	}

	/**
	 * Gets the button used to continue playing after winning.
	 * @return The ContinueButton object.
	 */
	public static ContinueButton getContinueButton() {
		return continueButton;
	}
}
