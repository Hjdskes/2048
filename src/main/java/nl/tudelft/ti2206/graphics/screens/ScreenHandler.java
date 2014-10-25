package nl.tudelft.ti2206.graphics.screens;


/**
 * The ScreenHandler is responsible for managing all the screens.
 * 
 * It keeps a reference to the current screen to be able to modify the screen.
 */
public class ScreenHandler {
	/** The unique singleton instance of this class. */
	private static ScreenHandler instance = new ScreenHandler();

	/** The screen currently being managed, which is also the
	 * screen currently on display. */
	private Screen screen;

	/** Overrides the default constructor. */
	private ScreenHandler() {
	}

	/**
	 * @return The singleton instance of the ScreenHandler.
	 */
	public static ScreenHandler getInstance() {
		return instance;
	}

	/**
	 * Sets the specified screen to be the current screen. Disposes the current
	 * screen if it is not null and resizes the screen if necessary.
	 *
	 * @param screen
	 *            The screen.
	 */
	public void set(Screen screen) {
		if (this.screen != null) {
			dispose();
		}
		this.screen = screen;
		screen.create();
	}

	/**
	 * Disposes cleanly of the screen.
	 * */
	public void dispose() {
		screen.dispose();
	}

	/**
	 * Draws the screen.
	 */
	public void draw() {
		screen.draw();
	}

	/**
	 * Updates the screen.
	 */
	public void update() {
		screen.update();
	}

	/**
	 * Restarts the current screen.
	 */
	public void restart() {
		screen.restart();
	}

	/**
	 * @return The current screen.
	 */
	public Screen getScreen() {
		return screen;
	}
}
