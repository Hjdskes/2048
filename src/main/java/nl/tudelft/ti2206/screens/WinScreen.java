package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The WinScreen is displayed when the player has won. It is semi-transparent,
 * and offers the ability to restart or continue playing.
 */
public class WinScreen extends Screen {
	/** The button used to put the game into continuing state. */
	private ContinueButton continueButton;

	/** The button used to restart the game. */
	private RestartButton restartButton;

	/** Constructs a new WinScreen. */
	public WinScreen() {
		stage = new Stage();
		restartButton = new RestartButton();
		continueButton = new ContinueButton();
	}

	@Override
	public void create() {
		super.create();
		stage.addActor(new Image(AssetHandler.getSkin(), "wonoverlay"));
		stage.addActor(continueButton);
		stage.addActor(restartButton);
	}

	@Override
	public boolean isOverlay() {
		return true;
	}
}
