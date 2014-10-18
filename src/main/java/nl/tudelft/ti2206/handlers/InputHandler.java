package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.screens.menuscreens.MenuScreen;

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

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** The current command that can be executed. */
	private Command command;

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
			setCommand(new MoveDownCommand(grid));
			command.execute();
			return true;
		case Keys.DPAD_UP:
			logger.debug(className, "User pressed key: UP");
			setCommand(new MoveUpCommand(grid));
			command.execute();
			return true;
		case Keys.DPAD_LEFT:
			logger.debug(className, "User pressed key: LEFT");
			setCommand(new MoveLeftCommand(grid));
			command.execute();
			return true;
		case Keys.DPAD_RIGHT:
			logger.debug(className, "User pressed key: RIGHT");
			setCommand(new MoveRightCommand(grid));
			command.execute();
			return true;
		case Keys.ESCAPE:
			logger.debug(className, "User pressed key: ESCAPE");
			ProgressHandler.getInstance().saveGame(grid);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					ScreenHandler.getInstance().set(new MenuScreen());
				}
			});
			return true;
		}
		return false;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
}
