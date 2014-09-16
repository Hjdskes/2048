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
	/** The boundaries of the button. */
	private Rectangle bounds;
	/** Determines whether the button has been pressed or not. */
	protected boolean isPressed = false;

	/**
	 * Creates a new SimpleButton object with specified parameters.
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
	 *            The x-coordinate of the click.
	 * @param screenY
	 *            The y-coordinate of the click.
	 * @return True if the button is clicked, false otherwise.
	 */
	private boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	/**
	 * Draws the button. Automatically draws the correct Sprite.
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
	 * Checks if the button is touched or clicked.
	 * 
	 * @param screenX
	 *            The x-coordinate of the click.
	 * @param screenY
	 *            The y-coordinate of the click.
	 * @return True if the button is clicked, false otherwise.
	 */
	public boolean isTouchDown(int screenX, int screenY) {
		if (isClicked(screenX, screenY)) {
			isPressed = true;
			return true;
		}
		return false;
	}

	/**
	 * Checks if the button is released. This is only used to reset the
	 * isPressed instance variable.
	 * 
	 * @param screenX
	 *            The x-coordinate of the click.
	 * @param screenY
	 *            The y-coordinate of the click.
	 * @return True if the button is released, false otherwise.
	 */
	public boolean isTouchUp(int screenX, int screenY) {
		if (isClicked(screenX, screenY)) {
			isPressed = false;
			return true;
		}
		return false;
	}

	/**
	 * Each derived button should override this method to ensure specific
	 * behavior when it is clicked. The GameWorld parameter is provided to allow
	 * access to the game objects.
	 * 
	 * @param world
	 *            A reference to the current GameWorld.
	 */
	public abstract void onClick(GameWorld world);
}
