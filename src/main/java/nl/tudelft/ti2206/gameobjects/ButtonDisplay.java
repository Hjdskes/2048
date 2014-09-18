package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.game.Game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ButtonHandler {

	/** The width of the game */
	private static final int GAME_WIDTH = Game.GAME_WIDTH;
	/** The gap between the edge of the game and the button. */
	private static final int GAP = 15;
	/** The button width */
	private static final int BUTTON_WIDTH = 140;

	/** The button used to continue playing after the game is won. */
	private static Button continueButton;
	/** The button used to restart the game. */
	private static Button restartButton;

	/**
	 * Loads all buttons and sets their event listeners and locations.
	 */
	public static void load() {
		initRestartButton();
		initContinueButton();
	}
	
	/**
	 * Initializes the restart button
	 */
	private static void initRestartButton() {
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
	}
	
	private static void initContinueButton() {
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
