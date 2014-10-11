package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.handlers.ScreenHandler;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button to restart the current game.
 */
public class RestartButton extends TextButton {
	/** The singleton reference to the ScreenHandler class. */
	private static ScreenHandler screenHandler = ScreenHandler.getInstance();

	/** Constructs a new RestartButton. */
	public RestartButton(final Grid grid, boolean center) {
		super("Restart", AssetHandler.getInstance().getSkin());
		if (center) {
			this.setX(TwentyFourtyGame.GAME_WIDTH / 2 - this.getPrefWidth() / 2);
		} else {
			this.setX(TwentyFourtyGame.GAME_WIDTH / 2 + 2*TwentyFourtyGame.GAP);
		}
		this.setY(TwentyFourtyGame.GAP);

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenHandler.removeTop();
				grid.restart();
			}
		});
	}
}
