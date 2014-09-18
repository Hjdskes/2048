package nl.tudelft.ti2206.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Drawable;
import nl.tudelft.ti2206.handlers.AssetHandler;

/**
 * A button to restart the game.
 */
public class RestartButton extends SimpleButton implements Drawable {
	/**
	 * Creates a new RestartButton with the specified parameters.
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
	public RestartButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Handles the click event. Restarts the game.
	 */
	@Override
	public void onClick(GameWorld world) {
		world.restart();
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(AssetHandler.restart, super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}
}
