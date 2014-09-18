package nl.tudelft.ti2206.buttons;

import nl.tudelft.ti2206.game.TwentyFourtyGame;

/**
 * The MenuButton class represents a basic button to be used in menus.
 * It defines an abstract onClick method, which derived buttons must
 * implement to define their own behavior.
 */
public abstract class MenuButton extends Button {
	/**
	 * Creates a new MenuButton object with specified parameters.
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
	public MenuButton(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Each derived button should override this method to ensure specific
	 * behavior when it is clicked. The TwentyFourtyGame parameter is provided
	 * to allow access to current game.
	 * 
	 * @param game
	 *            A reference to the current game.
	 */
	public abstract void onClick(TwentyFourtyGame game);
}
