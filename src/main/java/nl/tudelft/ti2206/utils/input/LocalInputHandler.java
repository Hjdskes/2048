package nl.tudelft.ti2206.utils.input;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.Command;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveRightCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.net.Networking;
import nl.tudelft.ti2206.utils.security.MoveValidator;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * The InputHandler processes input events and sends these over the network to
 * another instance.
 */
public class LocalInputHandler extends InputListener {

	/** Get current class name, used for logging output. */
	private static final String CLASSNAME = LocalInputHandler.class
			.getSimpleName();

	/** The singleton reference to the Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/**
	 * A reference to the local Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;

	/** The recent command of the local player */
	private Command command;

	private MoveValidator validator;

	/**
	 * Creates a new LocalInputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the local Grid.
	 */
	public LocalInputHandler(Grid grid) {
		this.grid = grid;
		sendGrid();
		validator = new MoveValidator(grid,true);
	}

	public MoveValidator getMoveValidator() {
		return validator;
	}	

	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		validator.validate();
		switch (keycode) {
		case Keys.DPAD_DOWN:
			logger.info(CLASSNAME, "Move is made in the direction DOWN");
			executeCommand(new MoveDownCommand(grid));
			networking.sendString("MOVE[D]");
			sendGrid();
			return true;
		case Keys.DPAD_UP:
			logger.info(CLASSNAME, "Move is made in the direction UP");
			executeCommand(new MoveUpCommand(grid));
			networking.sendString("MOVE[U]");
			sendGrid();;
			return true;
		case Keys.DPAD_LEFT:
			logger.info(CLASSNAME, "Move is made in the direction LEFT");
			executeCommand(new MoveLeftCommand(grid));
			networking.sendString("MOVE[L]");
			sendGrid();
			return true;
		case Keys.DPAD_RIGHT:
			logger.info(CLASSNAME, "Move is made in the direction RIGHT");
			executeCommand(new MoveRightCommand(grid));
			networking.sendString("MOVE[R]");
			sendGrid();
			return true;
		}
		return false;
	}

	/**
	 * Sends the local Grid over the network.
	 */
	private void sendGrid() {
		networking.sendString("GRID[" + grid.toString() + "]");
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