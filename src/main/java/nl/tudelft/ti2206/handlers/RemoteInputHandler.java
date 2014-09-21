package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

/**
 * The RemoteInputHandler processes input events that come from
 * over the network.
 */
public class RemoteInputHandler {
	/**
	 * A reference to the remote Grid, so the called objects can interact with
	 * it.
	 */
	private Grid grid;

	/**
	 * Creates a new RemoteInputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the remote Grid.
	 */
	public RemoteInputHandler(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Fills the remote Grid with the tiles provided in the string.
	 * @param tiles The string describing all the Tiles.
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
}
