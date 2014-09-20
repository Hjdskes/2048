package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The LoseScreen is displayed when the player has lost. It is semi-transparent,
 * and offers the ability to restart.
 */
public class LoseScreen extends Screen {
	/** The button used to restart the game. */
	private RestartButton restartButton;

	/** Constructs a new LoseScreen. */
	public LoseScreen() {
		stage = new Stage();
		restartButton = new RestartButton();
	}

	@Override
	public void create() {
		super.create();

		stage.addActor(new Image(AssetHandler.getSkin(), "lostoverlay"));
		stage.addActor(restartButton);
	}

	@Override
	public boolean isOverlay() {
		return true;
	}
}
