package nl.tudelft.ti2206.graphics.screens;

import com.badlogic.gdx.Gdx;

/**
 * The ScreenHandler is responsible for managing all the screens.
 * 
 * It keeps a reference to the current screen to be able to modify the screen.
 */
public class ScreenHandler {
	/** The unique singleton instance of this class. */
	private static ScreenHandler instance = new ScreenHandler();

	private Screen screen;

	/** Overrides the default constructor. */
	private ScreenHandler() {
	}

	/**
	 * 
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
		screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
	 * Pauses the screen.
	 */
	public void pause() {
		screen.pause();
	}

	/**
	 * Resizes the screen.
	 *
	 * @param width
	 *            The new game window width (in pixels).
	 * @param height
	 *            The new game window height (in pixels).
	 */
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	/**
	 * Resumes the screen.
	 */
	public void resume() {
		screen.resume();
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
