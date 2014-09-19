package nl.tudelft.ti2206.gameobjects;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * This class creates, positions and updates the buttons for restarting or
 * continuing the game. It extends Group so an instance of this class can be
 * added to the stage.
 */
public class ButtonDisplay extends Group {
	/** The button width. */
	private static final int BUTTON_WIDTH = 140;

	/** The button height. */
	private static final int BUTTON_HEIGHT = 70;

	/** References to the buttons. */
	private Button continueButton;
	private Button restartButton;

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
		restartButton = new TextButton("Restart", AssetHandler.getSkin());
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Restart");
			}
		});
		restartButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - BUTTON_WIDTH / 2);
		restartButton.setY(TwentyFourtyGame.GAP);
		this.addActor(restartButton);
	}

	/**
	 * Initializes the continue button.
	 */
	private void initContinueButton() {
		continueButton = new TextButton("Continue!", AssetHandler.getSkin());
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TwentyFourtyGame.setState(GameState.CONTINUING);
			}
		});
		continueButton.setX(TwentyFourtyGame.GAME_WIDTH / 2 - BUTTON_WIDTH / 2);
		continueButton.setY(BUTTON_HEIGHT + 2 * TwentyFourtyGame.GAP);
		continueButton.setVisible(false);

		this.addActor(continueButton);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (TwentyFourtyGame.isWon()) {
			continueButton.setVisible(true);
		}
	}
}
