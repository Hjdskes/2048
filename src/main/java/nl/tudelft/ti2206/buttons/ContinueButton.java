package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A button to continue the game when 2048 has been reached.
 * 
 * @author group-21
 */
public class ContinueButton extends SimpleButton {
	/**
	 * Creates a new ContinueButton with the specified parameters.
	 * 
	 * @param x
	 *            The x-coordinate for the button.
	 * @param y
	 *            The y-coordinate for the button.
	 * @param width
	 *            The width for the button.
	 * @param height
	 *            The height for the button.
	 * @param buttonUp
	 *            The Sprite shown when the button is not pressed.
	 * @param buttonDown
	 *            The Sprite shown when the button is pressed.
	 */
	public ContinueButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown) {
		super(x, y, width, height, buttonUp, buttonDown);
	}

	/**
	 * Handles the click event. Sets the game state to GameState.CONTINUING.
	 */
	@Override
	public void onClick(GameWorld world) {
		world.setGameState(GameState.CONTINUING);
	}
}
