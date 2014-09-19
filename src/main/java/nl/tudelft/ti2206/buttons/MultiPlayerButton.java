package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Drawable;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.screens.MultiGameScreen;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A button to launch a multiplayer game.
 */
public class MultiPlayerButton extends MenuButton implements Drawable {
	/**
	 * Creates a new MultiPlayerButton with the specified parameters.
	 * 
	 * @param x
	 *            The x-coordinate for the button.
	 * @param y
	 *            The y-coordinate for the button.
	 * @param width
	 *            The width for the button.
	 * @param height
	 *            The height for the button.
	 */
	public MultiPlayerButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Handles the click event. Launches a multiplayer game.
	 */
	@Override
	public void onClick(TwentyFourtyGame game) {
		game.setScreen(new MultiGameScreen());
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(AssetHandler.multi, super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}
}
