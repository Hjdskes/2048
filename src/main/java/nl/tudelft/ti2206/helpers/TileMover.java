package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.game.GameWorld;
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
	 * @param i
	 *            index of the tile
	 * @param dir
	 *            direction to move to.
	 * @return true if a move can be made, false otherwise.
	 */
	private boolean isNeighbourFree(int i, Direction dir) {
		/* If the tile is empty, no move can be made. */
		if (grid[i].isEmpty()) {
			return false;
		/* If the tile is at the edge of that direction, it cannot move. For
		 * example, in | T - - - |, T cannot move to the left as it is already
		 * in the leftmost spot. */
		} else if (dir == Direction.LEFT && i % ROW_LENGTH == 0) {
			return false;
		} else if (dir == Direction.RIGHT && i % ROW_LENGTH == 3) {
			return false;
		} else if (dir == Direction.UP && i < COL_LENGTH) {
			return false;
		} else if (dir == Direction.DOWN && i >= 3 * COL_LENGTH) {
			return false;
		}

		/* If the destination and the tile to be moved have not already merged
		 * with another tile... */
		if (!grid[i + offset].isMerged() && !grid[i].isMerged()) {
			/*...and either their values are equal or the destination tile is
			 * empty, the move can be made. */
			if (grid[i + offset].getValue() == grid[i].getValue()
					|| grid[i + offset].isEmpty()) {
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
					res += moveTile(i + k * offset, dir);
				}
			}
		}
		resetMergedTiles(i, dir);
		return res;
	}

	/**
	 * Moves a tile into the specified direction.
	 * 
	 * @param i
	 *            the index of the tile
	 * @param dir
	 *            the direction to move to
	 */
	private int moveTile(int i, Direction dir) {
		int res = 0;

		if (grid[i].isEmpty()) {
			return res;
		} else if (grid[i + offset].getValue() == grid[i].getValue()) {
			grid[i + offset].doubleValue();
			// update score
			res += grid[i + offset].getValue();
			grid[i + offset].setMerged(true);
			grid[i].resetValue();
			isMoveMade = true;
		} else if (grid[i + offset].isEmpty()) {
			grid[i + offset].setValue(grid[i].getValue());
			grid[i].resetValue();
			isMoveMade = true;
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
