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
	/** The background image. */
	private Image image;

	/** The button used to restart the game. */
	private RestartButton restartButton;

	/** Constructs a new LoseScreen. */
	public LoseScreen() {
		stage = new Stage();
		image = new Image(AssetHandler.getInstance().getSkin(), "lostoverlay");
		restartButton = new RestartButton();
	}

	/** Constructor used for mock insertion */
	public LoseScreen(Stage stage, Image image, RestartButton restartButton) {
		this.stage = stage;
		this.image = image;
		this.restartButton = restartButton;
	}

	@Override
	public void create() {
		super.create();

		stage.addActor(image);
		stage.addActor(restartButton);
	}

	@Override
	public void draw() {
		stage.draw();
	}

	@Override
	public boolean isOverlay() {
		return true;
	}
}
