package nl.tudelft.ti2206.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ButtonHandler {

	private static final int GAME_WIDTH = 600;
	private static final int GAP = 15;
	private static final int BUTTON_WIDTH = 140;
	// private static final int BUTTON_HEIGHT = 70;

	private static Button continueButton;
	private static Button restartButton;

	/**
	 * Loads all buttons and sets their event listeners and locations.
	 */
	public static void load() {
		// init the restart button
		restartButton = new Button(AssetHandler.getSkin()
				.getDrawable("newgame"));
		restartButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Restart");
			}
		});
		restartButton.setX(GAME_WIDTH / 2 - BUTTON_WIDTH / 2);
		restartButton.setY(GAP);

		continueButton = new Button(AssetHandler.getSkin().getDrawable(
				"continue"));
		// TODO: Set location
	}

	/**
	 * 
	 * @return The continue button
	 */
	public static Button getContinuButton() {
		return continueButton;
	}

	/**
	 * 
	 * @return The restart button.
	 */
	public static Button getRestartButton() {
		return restartButton;
	}
}
