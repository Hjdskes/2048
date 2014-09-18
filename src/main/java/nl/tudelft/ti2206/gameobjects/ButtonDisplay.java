package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.Game;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * This class creates, positions and updates the buttons for restarting or
 * continuing the game. It extends Group so an instance of this class can be
 * added to the stage.
 */
public class ButtonDisplay extends Group {
	/** The width of the game. */
	private static final int GAME_WIDTH = Game.GAME_WIDTH;

	/** The gap between the edge of the game and the button. */
	private static final int GAP = 15;

	/** The button width. */
	private static final int BUTTON_WIDTH = 140;

	/** The button height. */
	private static final int BUTTON_HEIGHT = 70;

	/**
	 * Loads all buttons and sets their event listeners and locations.
	 */
	public ButtonDisplay() {
		initRestartButton();
		initContinueButton();
	}

	/**
	 * Initializes the restart button.
	 */
	private void initRestartButton() {
		Button restartButton = new Button(AssetHandler.getSkin().getDrawable(
				"newgame"));
		restartButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Restart");
			}
		});
		restartButton.setX(GAME_WIDTH / 2 - BUTTON_WIDTH / 2);
		restartButton.setY(GAP);
		this.addActor(restartButton);
	}

	/**
	 * Initializes the continue button.
	 */
	private void initContinueButton() {
		Button continueButton = new Button(AssetHandler.getSkin().getDrawable(
				"continue"));
		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Continue");
			}
		});
		continueButton.setX(GAME_WIDTH / 2 - BUTTON_WIDTH / 2);
		continueButton.setY(BUTTON_HEIGHT + 2 * GAP);
		this.addActor(continueButton);
	}
}
