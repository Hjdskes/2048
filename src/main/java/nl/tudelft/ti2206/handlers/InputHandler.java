package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.MenuScreen;
import nl.tudelft.ti2206.state.ContinuingState;
import nl.tudelft.ti2206.state.GameState;
import nl.tudelft.ti2206.state.LostState;
import nl.tudelft.ti2206.state.RunningState;
import nl.tudelft.ti2206.state.WonState;

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

	//Primary State
	private GameState state;
	
	/** List of possible States*/
	private GameState runningState;
	private GameState wonState;
	private GameState lostState;
	private GameState continuingState;
	
	
	
	/**
	 * Creates a new InputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the current Grid.
	 */
	public InputHandler(Grid grid) {
		this.grid = grid;
		
		runningState = new RunningState(this);
		wonState = new WonState(this);
		lostState = new LostState(this);
		continuingState = new ContinuingState(this);
	}
	
	public GameState getWonState() { return wonState; }
	public GameState getLostState() { return lostState; }
	public GameState getRunningState() { return runningState; }
	public GameState getContinuingState() { return continuingState; }

	
	public void setState(GameState state) {
		this.state = state;
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
		}
		return false;
	}
}
