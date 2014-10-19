package nl.tudelft.ti2206.utils.input;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.ScreenHandler;
import nl.tudelft.ti2206.graphics.screens.menuscreens.MenuScreen;
import nl.tudelft.ti2206.utils.commands.Command;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveRightCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.handlers.ProgressHandler;
import nl.tudelft.ti2206.utils.log.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * The InputHandler processes input events, from for example the keyboard or the
 * mice. Currently, only the keyboard is needed.
 */
public class InputHandler extends InputListener {

	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = InputHandler.class.getSimpleName();

	/**
	 * A reference to the current Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

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
			logger.debug(CLASSNAME, "User pressed key: DOWN");
			executeCommand(new MoveDownCommand(grid));		
			return true;
		case Keys.DPAD_UP:
			logger.debug(CLASSNAME, "User pressed key: UP");
			executeCommand(new MoveUpCommand(grid));	
			return true;
		case Keys.DPAD_LEFT:
			logger.debug(CLASSNAME, "User pressed key: LEFT");
			executeCommand(new MoveLeftCommand(grid));	
			return true;
		case Keys.DPAD_RIGHT:
			logger.debug(CLASSNAME, "User pressed key: RIGHT");
			executeCommand(new MoveRightCommand(grid));		
			return true;
		case Keys.ESCAPE:
			logger.debug(CLASSNAME, "User pressed key: ESCAPE");
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

	/** Sets and executes the provided command. */
	public void executeCommand(Command command) {
		this.command = command;
		this.command.execute();
	}

	/** Returns the current command. */
	public Command getCommand() {
		return command;
	}
}
