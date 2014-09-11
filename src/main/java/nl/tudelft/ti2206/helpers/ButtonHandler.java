package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;

public class ButtonHandler {

	private static RestartButton restartButton;
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
	 * 
	 * @return the RestartButton object
	 */
	public static RestartButton getRestartButton() {
		return restartButton;
	}
	
	public static ContinueButton getContinueButton() {
		return continueButton;
	}
}
