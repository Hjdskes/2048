package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The MultiWaitScreen is displayed when the local player is 
 * waiting for the opponent to finish his moves.
 */
public class MultiWaitScreen extends Screen {
	/** The background image. */
	private Image image;

	/** The button to go back to the menu. */
	private MenuButton menuButton;

	/** Constructs a new MultiWaitScreen. */
	public MultiWaitScreen() {
		stage = new Stage();
		image = new Image(AssetHandler.getInstance().getSkin(), "multiwaitoverlay");
		menuButton = new MenuButton();
		this.setDrawBehavior( new SimpleDraw(stage));
	}

	/** Constructor used for mock insertion */
	public MultiWaitScreen(Stage stage, Image image, MenuButton menuButton) {
		this.stage = stage;
		this.image = image;
		this.menuButton = menuButton;
		this.setDrawBehavior( new SimpleDraw(stage));
	}

	@Override
	public void create() {
		super.create();
		stage.addActor(image);
		stage.addActor(menuButton);
	}

	@Override
	public boolean isOverlay() {
		return true;
	}
}
