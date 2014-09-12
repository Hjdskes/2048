package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A restart button to restart the game.
 * 
 * @author group-21
 */
public class RestartButton extends SimpleButton {

	/**
	 * Creates a new RestartButton with the specified parameters.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @param width
	 *            The width
	 * @param height
	 *            The height
	 * @param buttonUp
	 *            The Sprite shown when the button is not pressed
	 * @param buttonDown
	 *            The Sprite shown when the button is pressed
	 */
	public RestartButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown) {
		super(x, y, width, height, buttonUp, buttonDown);
	}

	/**
	 * Handles the click event. Restarts the game.
	 */
	@Override
	public void onClick(GameWorld world) {
		world.restart();
	}
}
