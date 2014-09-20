package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

public class RemoteInputHandler {
	private Grid grid;

	/**
	 * Creates a new InputHandler instance.
	 * 
	 * @param grid
	 *            A reference to the current Grid.
	 */
	public RemoteInputHandler(Grid grid) {
		this.grid = grid;
	}

	public void setTiles(Tile[] tiles) {
		for (int index = 0; index < 15; index += 1)
			grid.setTile(index, tiles[index].getValue());
	}

	public void setTileValues(int[] values) {
		grid.setTileValues(values);
	}

	public void move(Direction direction) {
		grid.move(direction);
	}
	
	public void moveUp() {
		grid.move(Direction.UP);

	}

	public void moveDown() {
		grid.move(Direction.DOWN);

	}

	public void moveRight() {
		grid.move(Direction.RIGHT);

	}

	public void moveLeft() {
		grid.move(Direction.LEFT);
	}
}
