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
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public void fillGrid(String str) {
		String[] split = str.split(",");

		for (int index = 0; index < split.length; index += 1) {
			grid.setTile(index, Integer.parseInt(split[index]));
		}
		
	}
	

	public void setTileValues(int[] values) {
		Tile[] tiles = grid.getTiles();
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].setValue(values[i]);
		}
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
