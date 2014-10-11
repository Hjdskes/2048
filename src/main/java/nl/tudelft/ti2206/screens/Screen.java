package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.drawbehaviour.DrawBehavior;
import nl.tudelft.ti2206.screens.overlays.ConnectionLostScreen;
import nl.tudelft.ti2206.screens.overlays.MultiWinScreen;
import nl.tudelft.ti2206.screens.overlays.WinScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * An abstract class for screens.
 * 
 * Code based on:
 * http://gamedev.stackexchange.com/questions/75902/how-to-design-
 * transparent-screen-in-libgdx
 */
public abstract class Screen implements Disposable {
	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The scene graph. */
	protected Stage stage;

	/** The DrawBehavior variable to determine the draw implementation */
	protected DrawBehavior drawbehavior;

	/**
	 * Called when the screen is shown. Used for initialization.
	 */
	public void create() {
		logger.debug(className, "Creating window...");
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Draws the screen with the help of the DrawBehavior implementations.
	 */
	public void draw() {
		drawbehavior.draw();
	}

	public void setDrawBehavior(DrawBehavior newDrawBehavior) {
		drawbehavior = newDrawBehavior;
	}

	/**
	 * Pauses the screen.
	 */
	public void pause() {
	}

	/**
	 * Called when the screen is resized.
	 * 
	 * @param width
	 *            The new game window width (in pixels).
	 * @param height
	 *            The new game window height (in pixels).
	 */
	public void resize(int width, int height) {
	}

	/**
	 * Resumes the screen after a pause.
	 */
	public void resume() {
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Updates the screen.
	 */
	public void update() {
		stage.act();
	}

	/**
	 * Adds a Lost or Won overlay, depending on whether the game is won, lost,
	 * and if it's in multiplayer mode.
	 * 
	 * @param isMulti
	 *            True if the game is in Multiplayer mode.
	 * @param isWon
	 *            True if the game is won.
	 * @param grid
	 *            The grid of the current game.
	 */
	public void addLWOverlay(boolean isMulti, boolean isWon, Grid grid) {
		if (isMulti && isWon) {
			new WinScreen(this, grid);
		} else if (!isMulti && isWon) {
			new MultiWinScreen(this);
		}
	}

	/**
	 * Adds a ConnectionLostScreen to the current screen.
	 */
	public void addConnectionLostOverlay() {
		new ConnectionLostScreen(this);
	}

	/**
	 * Restarts the current Screen, removing all actors and re-adding the wanted
	 * actors.
	 */
	public void restart() {
		stage.getActors().clear();
		create();
	}

	@Override
	public void dispose() {
		logger.debug(className, "Closing window...");
		stage.dispose();
	}

	/**
	 * @return The stage.
	 */
	public Stage getStage() {
		return stage;
	}
}