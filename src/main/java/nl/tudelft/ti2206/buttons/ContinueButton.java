package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;
import nl.tudelft.ti2206.gameobjects.Drawable;
import nl.tudelft.ti2206.handlers.AssetHandler;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A button to continue the game when 2048 has been reached.
 */
public class ContinueButton extends GameButton implements Drawable {
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
	 */
	public ContinueButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Handles the click event. Sets the game state to GameState.CONTINUING.
	 */
	@Override
	public void onClick(GameWorld world) {
		world.setGameState(GameState.CONTINUING);
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(AssetHandler.continueb, super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}
}
