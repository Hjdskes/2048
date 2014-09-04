package nl.tudelft.ti2206.buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SimpleButton {

	private float x, y, width, height;

	private Sprite buttonUp, buttonDown;

	private Rectangle bounds;

	protected boolean isPressed = false;

	public SimpleButton(float x, float y, float width, float height,
			Sprite buttonUp, Sprite buttonDown) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;

		bounds = new Rectangle(x, y, width, height);

	}

	private boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	public void draw(SpriteBatch batcher) {
		if (isPressed) {
			batcher.draw(buttonDown, x, y, width, height);
		} else {
			batcher.draw(buttonUp, x, y, width, height);
		}
	}

	public boolean isTouchDown(int screenX, int screenY) {

		if (isClicked(screenX, screenY)) {
			isPressed = true;
			return true;
		}

		return false;
	}

	public boolean isTouchUp(int screenX, int screenY) {

		// It only counts as a touchUp if the button is in a pressed state.
		if (bounds.contains(screenX, screenY) && isPressed) {
			isPressed = false;
			return true;
		}

		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}

}
