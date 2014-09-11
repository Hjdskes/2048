 package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * 
 * @author group-21
 */
public class RestartButton extends SimpleButton {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param buttonUp
	 * @param buttonDown
	 */
	public RestartButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown) {
		super(x, y, width, height, buttonUp, buttonDown);
	}

	@Override
	public void onClick(GameWorld world) {
		world.setGameState(GameState.RUNNING);
		world.restart();
	}
}
