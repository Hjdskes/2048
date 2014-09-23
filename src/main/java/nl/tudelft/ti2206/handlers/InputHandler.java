package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.screens.MenuScreen;
import nl.tudelft.ti2206.screens.ScreenHandler;

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
			grid.move(Direction.DOWN);
			return true;
		case Keys.DPAD_UP:
			grid.move(Direction.UP);
			return true;
		case Keys.DPAD_LEFT:
			grid.move(Direction.LEFT);
			return true;
		case Keys.DPAD_RIGHT:
			grid.move(Direction.RIGHT);
			return true;
		case Keys.ESCAPE:
			ProgressHandler.getInstance().saveGame(grid);
			ScreenHandler.add(new MenuScreen());
			return true;
		}
		return false;
	}
}
