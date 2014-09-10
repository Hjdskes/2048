 package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * 
 * @author group-21
 */
public class RestartButton extends SimpleButton {
	/** */
	private GameWorld world;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param buttonUp
	 * @param buttonDown
	 * @param world
	 */
	public RestartButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown, GameWorld world) {
		super(x, y, width, height, buttonUp, buttonDown);
		this.world = world;
	}

	@Override
	public void onClick() {
		world.setGameState(GameState.RUNNING);
		world.restart();
	}
}
