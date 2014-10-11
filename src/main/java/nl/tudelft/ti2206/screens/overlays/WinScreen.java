package nl.tudelft.ti2206.screens.overlays;

import nl.tudelft.ti2206.buttons.ContinueButton;
import nl.tudelft.ti2206.buttons.RestartButton;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.Screen;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawSimple;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The WinScreen is displayed when the player has won. It is semi-transparent,
 * and offers the ability to restart or continue playing.
 */
public class WinScreen extends Screen {
	/** The background image. */
	private Image image;

	/** The button used to put the game into continuing state. */
	private ContinueButton continueButton;

	/** The button used to restart the game. */
	private RestartButton restartButton;

	/** Constructs a new WinScreen. */
	public WinScreen(Grid grid) {
		stage = new Stage();
		image = new Image(AssetHandler.getInstance().getSkin(), "wonoverlay");
		restartButton = new RestartButton(grid, false);
		continueButton = new ContinueButton();
		this.setDrawBehavior(new DrawSimple(stage));
	}

	/** Constructor used for mock insertion */
	public WinScreen(Stage stage, Image image, RestartButton restartButton,
			ContinueButton continueButton) {
		this.stage = stage;
		this.image = image;
		this.restartButton = restartButton;
		this.continueButton = continueButton;
		this.setDrawBehavior(new DrawSimple(stage));
	}

	@Override
	public void create() {
		super.create();
		stage.addActor(image);
		stage.addActor(continueButton);
		stage.addActor(restartButton);
	}

	@Override
	public boolean isOverlay() {
		return true;
	}
}
