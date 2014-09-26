package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.log.Logger.Level;
import nl.tudelft.ti2206.screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * The InputHandler processes input events, from for example the keyboard or the
 * mice. Currently, only the keyboard is needed.
 */
public class InputHandler extends InputListener {
	/**
	 * A reference to the current Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;

	private String className = this.getClass().getSimpleName();

	private Logger logger = Logger.getInstance();

	/**
	 * Creates a new InputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the current Grid.
	 */
	public InputHandler(Grid grid) {
		this.grid = grid;
	}

	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		switch (keycode) {
		case Keys.DPAD_DOWN:
			logger.message(Level.INFO, className,
					"Move is made in direction DOWN");
			grid.move(Direction.DOWN);
			return true;
		case Keys.DPAD_UP:
			logger.message(Level.INFO, className,
					"Move is made in direction UP");
			grid.move(Direction.UP);
			return true;
		case Keys.DPAD_LEFT:
			logger.message(Level.INFO, className,
					"Move is made in direction LEFT");
			grid.move(Direction.LEFT);
			return true;
		case Keys.DPAD_RIGHT:
			logger.message(Level.INFO, className,
					"Move is made in direction RIGHT");
			grid.move(Direction.RIGHT);
			return true;
		case Keys.ESCAPE:
			logger.message(Level.INFO, className,
					"User pressed escape key.");
			ProgressHandler.getInstance().saveGame(grid);
			ScreenHandler.getInstance().add(new MenuScreen());
			return true;
		}
		return false;
	}
}
