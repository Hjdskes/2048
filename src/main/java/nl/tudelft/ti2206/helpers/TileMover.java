package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

/**
 * This class is designed to move tiles on the grid.
 * 
 * @author group-21
 *
 */
public class TileMover {
	/** The length of a row on the grid. */
	private static final int ROW_LENGTH = 4;
	/** The length of a column on the grid. */
	private static final int COL_LENGTH = 4;
	/** The array holding all the tiles. */
	private Tile[] grid;
	/** Indicates whether a move has been made. */
	private boolean isMoveMade;
	/** The difference between the indices of the tile to be moved and the tile
	 *  to move to. */
	private int offset;

	public TileMover(Grid grid) {
		this.grid = grid.getTiles();
	}

	/**
	 * Performs a move to the left.
	 * 
	 * @return the value to add to the score.
	 */
	public int moveLeft() {
		int res = 0;
		offset = -1;
		for (int i = grid.length - 1; i >= 0; i = i - ROW_LENGTH) {
			res += moveAffected(i, Direction.LEFT);
		}
		return res;
	}

	/**
	 * Performs a move to the right.
	 * 
	 * @return the value to add to the score.
	 */
	public int moveRight() {
		int res = 0;
		offset = 1;
		for (int i = 0; i < grid.length; i = i + ROW_LENGTH) {
			res += moveAffected(i, Direction.RIGHT);
		}
		return res;
	}

	/**
	 * Performs a move upwards.
	 * 
	 * @return the value to add to the score.
	 */
	public int moveUp() {
		int res = 0;
		offset = -4;
		for (int i = 3 * ROW_LENGTH; i < grid.length; i++) {
			res += moveAffected(i, Direction.UP);
		}
		return res;
	}

	/**
	 * Performs a move downwards.
	 * 
	 * @return the value to add to the score.
	 */
	public int moveDown() {
		int res = 0;
		offset = 4;
		for (int i = 0; i < ROW_LENGTH; i++) {
			res += moveAffected(i, Direction.DOWN);
		}
		return res;
	}

	/**
	 * Checks if a tile can move to the neighboring tile in the specified
	 * direction.
	 * 
	 * @param index
	 *            index of the tile
	 * @param dir
	 *            direction to move to.
	 * @return true if a move can be made, false otherwise.
	 */
	private boolean isNeighbourFree(int index, Direction dir) {
		/* If the tile is empty, no move can be made. */
		if (grid[index].isEmpty()) {
			return false;
		/* If the tile is at the edge of that direction, it cannot move. For
		 * example, in | T - - - |, T cannot move to the left as it is already
		 * in the leftmost spot. */
		} else if (dir == Direction.LEFT && index % ROW_LENGTH == 0) {
			return false;
		} else if (dir == Direction.RIGHT && index % ROW_LENGTH == 3) {
			return false;
		} else if (dir == Direction.UP && index < COL_LENGTH) {
			return false;
		} else if (dir == Direction.DOWN && index >= 3 * COL_LENGTH) {
			return false;
		}

		/* If the destination and the tile to be moved have not already merged
		 * with another tile... */
		if (!grid[index + offset].isMerged() && !grid[index].isMerged()) {
			/*...and either their values are equal or the destination tile is
			 * empty, the move can be made. */
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
	 * @param i
	 *            the index of the farthest tile in the row or column, depending
	 *            on the direction to move to.
	 * @param dir
	 *            the direction to move to.
	 */
	private int moveAffected(int i, Direction dir) {
		int res = 0;

		/* While moves can be made in the row or column, move each tile in that
		 * row or column. */
		while (isNeighbourFree(i, dir) || isNeighbourFree(i + offset, dir)
				|| isNeighbourFree(i + 2 * offset, dir)) {
			for (int k = 0; k < 3; k++) {
				if (isNeighbourFree(i + k * offset, dir)) {
					res += moveTile(i + k * offset);
				}
			}
		}
		resetMergedTiles(i, dir);
		return res;
	}

	/**
	 * Moves a tile into the specified direction.
	 * 
	 * @param index
	 *            the index of the tile
	 * @return the value to add to the score.
	 */
	private int moveTile(int index) {
		int res = 0;

		if (grid[index].isEmpty()) {
			return res;
		} else {
			grid[index + offset].doubleValue();
			grid[index].resetValue();
			isMoveMade = true;
			if (grid[index + offset].getValue() == grid[index].getValue()) {
				/* Update score and set merged state only if two
				 * Tiles merged. */
				res += grid[index + offset].getValue();
				grid[index + offset].setMerged(true);
			}
		}
		return res;
	}

	/**
	 * Resets all merged tiles in a row or column so they can merge again.
	 * 
	 * @param index
	 *            the index of the farthest tile in the row or column, depending
	 *            on the direction to move to.
	 * @param dir
	 *            the direction to move to
	 */
	private void resetMergedTiles(int index, Direction dir) {
		if (dir == Direction.LEFT) {
			for (int i = 0; i < ROW_LENGTH; i++) {
				grid[index - i].setMerged(false);
			}
		} else if (dir == Direction.RIGHT) {
			for (int i = 0; i < ROW_LENGTH; i++) {
				grid[index + i].setMerged(false);
			}
		} else if (dir == Direction.UP) {
			for (int i = 0; i < COL_LENGTH; i++) {
				grid[index - i * COL_LENGTH].setMerged(false);
			}
		} else if (dir == Direction.DOWN) {
			for (int i = 0; i < COL_LENGTH; i++) {
				grid[index + i * COL_LENGTH].setMerged(false);
			}
		}
	}

	/**
	 * Indicates whether a move has been made.
	 * 
	 * @return true if a move has been made, false otherwise.
	 */
	public boolean isMoveMade() {
		return this.isMoveMade;
	}
}
