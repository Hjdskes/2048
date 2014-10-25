package nl.tudelft.ti2206.graphics.screens.overlays;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.buttons.RestartButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The LoseScreen is displayed when the player has lost. It is semi-transparent,
 * and offers the ability to restart.
 */
public class LoseScreen {
	/** The background image. */
	private Image image;

	/** The button used to restart the game. */
	private RestartButton restartButton;

	/** The stage of the parent screen. */
	private Stage stage;

	/** Constructs a new LoseScreen. */
	public LoseScreen(Screen parent, Grid grid) {
		stage = parent.getStage();

		image = new Image(AssetHandler.getInstance().getSkin(), "lostoverlay");
		restartButton = new RestartButton(grid, false);

		addActors();
	}

	/** Constructor used for mock insertion */
	public LoseScreen(Stage stage, Image image, RestartButton restartButton) {
		this.stage = stage;
		this.image = image;
		this.restartButton = restartButton;
		addActors();
	}

	/** Adds all required actors to stage. */ 
	private void addActors() {
		stage.addActor(image);
		stage.addActor(restartButton);
	}
}
