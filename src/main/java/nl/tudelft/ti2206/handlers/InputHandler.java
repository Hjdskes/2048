package nl.tudelft.ti2206.handlers;

import java.util.Timer;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.MenuScreen;
import nl.tudelft.ti2206.solver.Benchmark;
import nl.tudelft.ti2206.solver.Benchmark.Strategy;

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

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	Timer solver = null;

	private Benchmark bmark;

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
			logger.debug(className, "User pressed key: DOWN");
			grid.move(Direction.DOWN);
			return true;
		case Keys.DPAD_UP:
			logger.debug(className, "User pressed key: UP");
			grid.move(Direction.UP);
			return true;
		case Keys.DPAD_LEFT:
			logger.debug(className, "User pressed key: LEFT");
			grid.move(Direction.LEFT);
			return true;
		case Keys.DPAD_RIGHT:
			logger.debug(className, "User pressed key: RIGHT");
			grid.move(Direction.RIGHT);
			return true;
		case Keys.ESCAPE:
			logger.debug(className, "User pressed key: ESCAPE");
			ProgressHandler.getInstance().saveGame(grid);
			ScreenHandler.getInstance().add(new MenuScreen());
			return true;

		case Keys.S:

			if (bmark == null) {
				logger.debug(className,
						"Solving this grid! At least, trying to...");
				bmark = new Benchmark(grid, Strategy.HUMAN, 100, 5);
				bmark.start();
			} else if (bmark.isRunning()) {
				bmark.stop();
				bmark = null;
				logger.debug(className, "Autosolver stopped.");
			}
		}
		return false;
	}
}
