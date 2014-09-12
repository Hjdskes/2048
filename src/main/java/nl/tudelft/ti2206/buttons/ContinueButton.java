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
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param buttonUp
	 * @param buttonDown
	 */
	public ContinueButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown) {
		super(x, y, width, height, buttonUp, buttonDown);
	}

	/**
	 * Handles the click event. Sets the gameState to CONTINUING.
	 */
	@Override
	public void onClick(GameWorld world) {
		world.setGameState(GameState.CONTINUING);
	}
}
