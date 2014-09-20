package nl.tudelft.ti2206.handlers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * This class is responsible for moving tiles on the grid.
 */
public class TileHandler {
	/** The length of a row on the grid. */
	private static final int ROW_LENGTH = 4;

	/** The length of a column on the grid. */
	private static final int COL_LENGTH = 4;

	/** The array holding all the tiles. */
	private Tile[] grid;

	/** Indicates whether a move has been made. */
	private boolean isMoveMade;

	/**
	 * The difference between the indices of the Tile to be moved and
	 * the Tile to move to.
	 */
	private int offset;

	/** The points to be awarded to the player */
	private int scoreIncrement;

	/**
	 * Constructs a new TileHandler object.
	 * 
	 * @param grid
	 *            The grid to move the tiles for.
	 */
	public TileHandler(Grid grid) {
		this.grid = grid.getTiles();
		scoreIncrement = 0;
	}

	/**
	 * Performs a move to the left.
	 * 
	 * @return The value to add to the score.
	 */
	public void moveLeft() {
		offset = -1;
		for (int i = 0; i < grid.length; i = i + ROW_LENGTH) {
			moveAffected(i, Direction.LEFT);
		}
	}

	/**
	 * Performs a move to the right.
	 * 
	 * @return The value to add to the score.
	 */
	public void moveRight() {
		offset = 1;
		for (int i = grid.length - 1; i >= 0; i = i - ROW_LENGTH) {
			moveAffected(i, Direction.RIGHT);
		}
	}

	/**
	 * Performs a move downwards.
	 * 
	 * @return The value to add to the score.
	 */
	public void moveDown() {
		offset = -4;
		for (int i = 0; i < ROW_LENGTH; i++) {
			moveAffected(i, Direction.DOWN);
		}
	}

	/**
	 * Performs a move upwards.
	 * 
	 * @return The value to add to the score.
	 */
	public void moveUp() {
		offset = 4;
		for (int i = 3 * ROW_LENGTH; i < grid.length; i++) {
			moveAffected(i, Direction.UP);
		}
	}

	/**
	 * Checks if an Tile can move to the neighboring Tile in the
	 * specified direction.
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @param dir
	 *            The direction to move in.
	 * @return True if a move can be made, false otherwise.
	 */
	private boolean isNeighbourFree(int index, Direction dir) {
		/* If the Tile is empty, no move can be made. */
		if (grid[index].isEmpty()) {
			return false;
		}

		/*
		 * If the Tile is at the edge of that direction, it cannot move.
		 * For example, in | T - - - |, T cannot move to the left as it is
		 * already in the leftmost spot.
		 */
		if (dir == Direction.LEFT && index % ROW_LENGTH == 0) {
			return false;
		} else if (dir == Direction.RIGHT && index % ROW_LENGTH == 3) {
			return false;
		} else if (dir == Direction.DOWN && index < COL_LENGTH) {
			return false;
		} else if (dir == Direction.UP && index >= 3 * COL_LENGTH) {
			return false;
		}

		/* If the Tile next to this Tile is empty, it can move */
		if (grid[index + offset].isEmpty()) {
			return true;
		}

		/*
		 * If the destination and the Tile to be moved have not already
		 * merged with another Tile...
		 */
		if (!grid[index + offset].isMerged() && !grid[index].isMerged()) {
			/*
			 * ...and either their values are equal or the destination
			 * Tile is empty, the move can be made.
			 */
			if (grid[index + offset].getValue() == grid[index].getValue()
					|| grid[index + offset].isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Moves a row or column of tiles into the specified direction.
	 * 
	 * @param index
	 *            The index of the farthest Tile in the row or column,
	 *            depending on the direction to move in.
	 * @param dir
	 *            The direction to move in.
	 */
	private void moveAffected(int index, Direction dir) {
		/*
		 * While moves can be made in the row or column, move each Tile
		 * in that row or column.
		 */
		while (isNeighbourFree(index - offset, dir)
				|| isNeighbourFree(index - 2 * offset, dir)
				|| isNeighbourFree(index - 3 * offset, dir)) {
			for (int k = 1; k < ROW_LENGTH; k++) {
				if (isNeighbourFree(index - k * offset, dir)) {
					moveTile(index - k * offset);
				}
			}
		}
		resetMergedTiles(index, dir);
	}

	/**
	 * Moves an Tile into the specified direction.
	 * 
	 * @param index
	 *            The index of the Tile.
	 * @return The value to add to the score.
	 */
	private void moveTile(int index) {
		if (grid[index + offset].isEmpty()) {
			grid[index + offset].setValue(grid[index].getValue());
			if (grid[index].isMerged()) {
				grid[index + offset].setMerged(true);
			}
		} else if (grid[index + offset].getValue() == grid[index].getValue()) {
			grid[index + offset].doubleValue();
			grid[index + offset].setMerged(true);
			grid[index + offset].merge();
			scoreIncrement += grid[index + offset].getValue();
		}
		grid[index].reset();
		isMoveMade = true;
	}

	/**
	 * Resets all merged tiles in a row or column so they can merge again.
	 * 
	 * @param index
	 *            The index of the farthest Tile in the row or column,
	 *            depending on the direction to move to.
	 * @param dir
	 *            The direction to move in.
	 */
	private void resetMergedTiles(int index, Direction dir) {
		if (dir == Direction.LEFT) {
			for (int i = 0; i < ROW_LENGTH; i++) {
				grid[index + i].setMerged(false);
			}
		} else if (dir == Direction.RIGHT) {
			for (int i = 0; i < ROW_LENGTH; i++) {
				grid[index - i].setMerged(false);
			}
		} else if (dir == Direction.DOWN) {
			for (int i = 0; i < COL_LENGTH; i++) {
				grid[index + i * COL_LENGTH].setMerged(false);
			}
		} else if (dir == Direction.UP) {
			for (int i = 0; i < COL_LENGTH; i++) {
				grid[index - i * COL_LENGTH].setMerged(false);
			}
		}
	}

	/**
	 * Indicates whether a move has been made.
	 * 
	 * @return True if a move has been made, false otherwise.
	 */
	public boolean isMoveMade() {
		return this.isMoveMade;
	}

	/**
	 * Returns the value by which the score should be incremented.
	 * @return The value by which the score should be incremented.
	 */
	public int getScoreIncrement() {
		return this.scoreIncrement;
	}

	/**
	 * Prepares the TileHandler for the next round of moving tiles.
	 */
	public void reset() {
		scoreIncrement = 0;
		isMoveMade = false;
	}
}
