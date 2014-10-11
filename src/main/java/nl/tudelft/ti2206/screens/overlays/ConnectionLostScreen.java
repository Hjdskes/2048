package nl.tudelft.ti2206.screens.overlays;

import nl.tudelft.ti2206.buttons.MenuButton;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.Screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The ConnectionLostScreen is displayed when the connection drops.
 */
public class ConnectionLostScreen {
	/** The transparent background image. */
	private Image image;

	/** The button to go back to the menu. */
	private MenuButton menuButton;

	/** The stage of the parent screen */
	private Stage stage;
	
	/** Constructs a new ConnectionLostScreen. */
	public ConnectionLostScreen(Screen parent) {
		stage = parent.getStage();
		image = new Image(AssetHandler.getInstance().getSkin(), "connectionlostoverlay");
		menuButton = new MenuButton();
		addActors();
	}

	/** Constructor used for mock insertion */
	public ConnectionLostScreen(Stage stage, Image image, MenuButton menuButton) {
		this.stage = stage;
		this.image = image;
		this.menuButton = menuButton;
		addActors();
	}

	public void addActors() {
		stage.addActor(image);
		stage.addActor(menuButton);
	}
}
