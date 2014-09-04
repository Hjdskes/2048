package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class RestartButton extends SimpleButton {

	private GameWorld world;
	
	public RestartButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown, GameWorld world) {
		super(x, y, width, height, buttonUp, buttonDown);
		this.world = world;
	}

	public void onClick() {
		world.restart();
	}
}
