package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.GameWorld;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * The SimpleButton class represents a basic button, from which other buttons
 * can be derived.
 * 
 * @author group-21
 */
public abstract class SimpleButton {
	/** The coordinates and dimension. */
	private float x, y, width, height;
	/** The Sprites to be shown. */
	private Sprite buttonUp, buttonDown;
	/** The boundaries of the SimpleButton */
	private Rectangle bounds;
	/** Determines whether the button has been pressed or not */
	protected boolean isPressed = false;

	/**
	 * Creates a new SimpleButton object with specified parameters.
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
	 *            The Sprite when the button has not been pressed
	 * @param buttonDown
	 *            The Sprite when the bustton is pressed
	 */
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

	/**
	 * Determines if the button is clicked.
	 * 
	 * @param screenX
	 *            The x coordinate clicked
	 * @param screenY
	 *            The y coordinate clicked
	 * @return
	 */
	private boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	/**
	 * Draws the button, depending on whether it is pressed or not.
	 * 
	 * @param batcher
	 *            The SpriteBatcher that draws the button.
	 */
	public void draw(SpriteBatch batcher) {
		if (isPressed) {
			batcher.draw(buttonDown, x, y, width, height);
		} else {
			batcher.draw(buttonUp, x, y, width, height);
		}
	}

	/**
	 * Checks if the button is touched/clicked.
	 * 
	 * @param screenX
	 *            The x coordinate
	 * @param screenY
	 *            The y coordinate
	 * @return
	 */
	public boolean isTouchDown(int screenX, int screenY) {
		if (isClicked(screenX, screenY)) {
			isPressed = true;
			return true;
		}
		return false;
	}

	/**
	 * Resets isPressed if the button is pressed when it has been pressed
	 * before.
	 * 
	 * @param screenX
	 *            The x coordinate
	 * @param screenY
	 *            The y coordinate
	 * @return
	 */
	public boolean isTouchUp(int screenX, int screenY) {
		if (isClicked(screenX, screenY)) {
			isPressed = false;
			return true;
		}
		return false;
	}

	/**
	 * Each button should override this method to ensure specific behavior when
	 * the button is clicked. Parameter world is provided to allow access to the
	 * game objects.
	 * 
	 * @param world
	 *            The GameWorld
	 */
	public abstract void onClick(GameWorld world);
}
