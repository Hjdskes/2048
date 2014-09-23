package nl.tudelft.ti2206.handlers;

import java.util.Observable;
import java.util.Observer;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.net.Networking;

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

	/** The singleton Networking instance. */
	private static Networking networking = Networking.getInstance();

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
		grid.move(Direction.UP);
	}

	/**
	 * Performs a move downwards on the remote Grid.
	 */
	public void moveDown() {
		grid.move(Direction.DOWN);
	}

	/**
	 * Performs a move to the right on the remote Grid.
	 */
	public void moveRight() {
		grid.move(Direction.RIGHT);
	}

	/**
	 * Performs a move to the left on the remote Grid.
	 */
	public void moveLeft() {
		grid.move(Direction.LEFT);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String input = (String) arg;
			System.out.println("INFO: update received: " + input);
			handleRemoteInput(input);
		}
	}
	
	/**
	 * Check if string representation of Grid is valid (contains 15 commas).
	 * @param str
	 * @return
	 */
	public boolean validGrid(String str) {
		
		return (str.matches("\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+,\\d+") &&
		(str.length() - str.replace(",", "").length()) == 15);
	}

	/**
	 * Parse the remote input and process it.
	 * 
	 * @param str
	 */
	public void handleRemoteInput(String str) {

		int closing = str.indexOf(']');
		System.out.println("INFO: handleInput: closing bracket @ " + closing);
		
		if (closing == -1) {
			System.err
					.println("ERROR: Protocol parsing failed, no closing bracket found (-1).");
		} else if (str.startsWith("GRID[")) {
			String strGrid = str.substring(5, closing);

			if (validGrid(strGrid)) {
				fillGrid(strGrid);
				System.out.println("INFO: New grid set.");
			} else {
				System.err
						.println("ERROR: Protocol parsing failed, malformed grid string: " + strGrid);
			}
		} else if (str.startsWith("MOVE[") && closing == 6) {
			char direction = str.charAt(5);

			System.out.println("INFO: handling move to " + direction);
			
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
				System.err.println("ERROR: Unknown direction parameter in protocol: "
								+ direction);
				break;
			}
		} else {
			System.err
					.println("ERROR: Unrecognised string in protocol: " + str + ", closing tag is at " + closing);
		}
	}

}
