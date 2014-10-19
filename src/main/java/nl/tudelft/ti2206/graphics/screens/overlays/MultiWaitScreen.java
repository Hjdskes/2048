package nl.tudelft.ti2206.graphics.screens.overlays;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.graphics.screens.Screen;
import nl.tudelft.ti2206.utils.handlers.AssetHandler;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The MultiWaitScreen is displayed when the local player is waiting for the
 * opponent to finish his moves.
 */
public class MultiWaitScreen {
	/** The background image. */
	private Image image;

	/** The button to go back to the menu. */
	private MenuButton menuButton;

	/** The stage of the parent screen */
	private Stage stage;

	/** Constructs a new MultiWaitScreen. */
	public MultiWaitScreen(Screen parent, boolean localWaiting) {
		stage = parent.getStage();
		image = new Image(AssetHandler.getInstance().getSkin(),
				"multiwaitoverlay");
		menuButton = new MenuButton();
		addActors();

		setImageX(localWaiting);
	}

	/** Constructor used for mock insertion */
	public MultiWaitScreen(Stage stage, Image image, MenuButton menuButton,
			boolean isLocalWaiting) {
		this.stage = stage;
		this.image = image;
		this.menuButton = menuButton;
		addActors();
		setImageX(isLocalWaiting);
	}

	/** Adds all required actors to stage. */
	private void addActors() {
		stage.addActor(image);
		stage.addActor(menuButton);
	}

	/** Sets the overlay on the other side of the screen if its the opponent. */
	private void setImageX(boolean isLocalWaiting) {
		if (!isLocalWaiting)
			image.setX(TwentyFourtyGame.GAME_WIDTH);
	}
}
