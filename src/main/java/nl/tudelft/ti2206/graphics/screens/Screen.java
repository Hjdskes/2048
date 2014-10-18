package nl.tudelft.ti2206.graphics.screens;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBehavior;
import nl.tudelft.ti2206.graphics.screens.overlays.ConnectionLostScreen;
import nl.tudelft.ti2206.graphics.screens.overlays.LoseScreen;
import nl.tudelft.ti2206.graphics.screens.overlays.MultiLoseScreen;
import nl.tudelft.ti2206.graphics.screens.overlays.MultiWaitScreen;
import nl.tudelft.ti2206.graphics.screens.overlays.MultiWinScreen;
import nl.tudelft.ti2206.graphics.screens.overlays.WinScreen;
import nl.tudelft.ti2206.utils.log.Logger;

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

	/** True if the stage of the screen contains actors of an overlay. */
	private boolean hasOverlay;
	
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
		if (!isMulti && isWon) {
			new WinScreen(this, grid);
		} else if (isMulti && isWon) {
			new MultiWinScreen(this);
		} else if (!isMulti && !isWon) {
			new LoseScreen(this, grid);
		} else {
			new MultiLoseScreen(this);
		}
		hasOverlay = true;
	}

	/**
	 * Adds a ConnectionLostScreen to the current screen.
	 */
	public void addConnectionLostOverlay() {
		new ConnectionLostScreen(this);
		hasOverlay = true;
	}
	
	/**
	 * Adds a MultiWaitScreen to the current screen.
	 * @param localWaiting
	 * 			True if the local player is in the waiting state.
	 */
	public void addMultiWaitScreenOverlay(boolean localWaiting) {
		new MultiWaitScreen(this, localWaiting);
		hasOverlay = true;
	}

	/**
	 * Restarts the current Screen, removing all actors and re-adding the wanted
	 * actors.
	 */
	public void restart() {
		stage.getActors().clear();
		create();
		hasOverlay = false;
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
	
	public boolean hasOverlay() {
		return hasOverlay;
	}
}