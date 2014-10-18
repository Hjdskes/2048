package nl.tudelft.ti2206.utils.input;


import java.util.Observable;
import java.util.Observer;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.Command;
import nl.tudelft.ti2206.utils.commands.MoveDownCommand;
import nl.tudelft.ti2206.utils.commands.MoveLeftCommand;
import nl.tudelft.ti2206.utils.commands.MoveRightCommand;
import nl.tudelft.ti2206.utils.commands.MoveUpCommand;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.net.Networking;

/**
 * The RemoteInputHandler processes input events that come from over the
 * network.
 */
public class RemoteInputHandler implements Observer {
	/**
	 * A reference to the remote Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;

	/** The singleton reference to the Networking instance. */
	private static Networking networking = Networking.getInstance();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();
	
	/** The recent command of the remote player */
	private Command command;

	/**
	 * Creates a new RemoteInputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the remote Grid.
	 */
	public RemoteInputHandler(Grid grid) {
		this.grid = grid;

		networking.addObserver(this);
	}

	/**
	 * Fills the remote Grid with the tiles provided in the string.
	 * 
	 * @param tiles
	 *            The string describing all the Tiles.
	 */
	public void fillGrid(String tiles) {
		String[] split = tiles.split(",");

		for (int index = 0; index < split.length; index++) {
			grid.setTile(index, Integer.parseInt(split[index]));
		}
	}

	/**
	 * Performs a move upwards on the remote Grid.
	 */
	public void moveUp() {
		logger.info(this.getClass().getSimpleName(), "Remote player moves UP");
		setCommand(new MoveUpCommand(grid));
		command.execute();
	}

	/**
	 * Performs a move downwards on the remote Grid.
	 */
	public void moveDown() {
		logger.info(this.getClass().getSimpleName(), "Remote player moves DOWN");
		setCommand(new MoveDownCommand(grid));
		command.execute();
	}

	/**
	 * Performs a move to the right on the remote Grid.
	 */
	public void moveRight() {
		logger.info(this.getClass().getSimpleName(),
				"Remote player moves RIGHT");
		setCommand(new MoveRightCommand(grid));
		command.execute();
	}

	/**
	 * Performs a move to the left on the remote Grid.
	 */
	public void moveLeft() {
		logger.info(this.getClass().getSimpleName(), "Remote player moves LEFT");
		setCommand(new MoveLeftCommand(grid));
		command.execute();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String input = (String) arg;
			logger.debug(className, "update received: " + input);
			handleRemoteInput(input);
		}
	}

	/**
	 * Check if string representation of Grid is valid (contains 15 commas).
	 * 
	 * @param str
	 * @return
	 */
	public boolean validateGrid(String str) {
		return (str
				.matches("\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+") && (str
				.length() - str.replace(",", "").length()) == 15);
	}

	/**
	 * Parse the remote input and process it.
	 * 
	 * @param str
	 */
	public void handleRemoteInput(String str) {
		int closing = str.indexOf(']');

		if (closing == -1) {
			logger.error(className,
					"Protocol parsing failed, no closing bracket found (-1).");
		} else if (str.startsWith("GRID[")) {
			String strGrid = str.substring(5, closing);

			if (validateGrid(strGrid)) {
				fillGrid(strGrid);
				logger.debug(className, "New remote grid set.");
			} else {
				logger.error(className,
						"Protocol parsing failed, malformed remote grid string: "
								+ strGrid);
			}
		} else if (str.startsWith("MOVE[") && closing == 6) {
			char direction = str.charAt(5);

			logger.debug(className,
					"Processing remote user's move to"
							+ direction);
			
			switch (direction) {
			case 'U':
				moveUp();
				break;
			case 'D':
				moveDown();
				break;
			case 'R':
				moveRight();
				break;
			case 'L':
				moveLeft();
				break;
			default:
				logger.error(className,
						"Unknown remote direction parameter in protocol: "
								+ direction);
				break;
			}
		} else {
			logger.error(className, "Unrecognised remote string in protocol: "
					+ str + ", closing tag is at position " + closing);
		}
	}
	
	/** Used to insert a Mock for Logger. */
	public void setLogger(Logger loggerMock) {
		logger = loggerMock;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
}
