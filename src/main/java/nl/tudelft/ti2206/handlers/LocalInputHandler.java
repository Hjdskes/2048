package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.log.Logger;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * The InputHandler processes input events and sends these over the network to
 * another instance.
 */
public class LocalInputHandler extends InputListener {

	/** The singleton reference to the Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/**
	 * A reference to the local Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;
	
	/** The recent command of the local player */
	private Command command;

	/**
	 * Creates a new LocalInputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the local Grid.
	 */
	public LocalInputHandler(Grid grid) {
		this.grid = grid;
		sendGrid();
	}

	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		switch (keycode) {
		case Keys.DPAD_DOWN:
			logger.info(className, "Move is made in the direction DOWN");
			setCommand(new MoveDownCommand(grid));
			command.execute();
			networking.sendString("MOVE[D]");
			sendGrid();
			return true;
		case Keys.DPAD_UP:
			logger.info(className, "Move is made in the direction UP");
			setCommand(new MoveUpCommand(grid));
			command.execute();
			networking.sendString("MOVE[U]");
			sendGrid();
			return true;
		case Keys.DPAD_LEFT:
			logger.info(className, "Move is made in the direction LEFT");
			setCommand(new MoveLeftCommand(grid));
			command.execute();
			networking.sendString("MOVE[L]");
			sendGrid();
			return true;
		case Keys.DPAD_RIGHT:
			logger.info(className, "Move is made in the direction RIGHT");
			setCommand(new MoveRightCommand(grid));
			command.execute();
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
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
}