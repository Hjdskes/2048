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
	/**
	 * The difference between the indices of the tile to be moved and the tile
	 * to move to.
	 */
	private int offset;
	/** The points to be awarded to the player */
	private int scoreIncrement;

	public TileMover(Grid grid) {
		this.grid = grid.getTiles();
		scoreIncrement = 0;
	}

	/**
	 * Performs a move to the left.
	 * 
	 * @return the value to add to the score.
	 */
	public void moveLeft() {
		offset = -1;
		for (int i = grid.length - 1; i >= 0; i = i - ROW_LENGTH) {
			moveAffected(i, Direction.LEFT);
		}
	}

	/**
	 * Performs a move to the right.
	 * 
	 * @return the value to add to the score.
	 */
	public void moveRight() {
		offset = 1;
		for (int i = 0; i < grid.length; i = i + ROW_LENGTH) {
			moveAffected(i, Direction.RIGHT);
		}
	}

	/**
	 * Performs a move upwards.
	 * 
	 * @return the value to add to the score.
	 */
	public void moveUp() {
		offset = -4;
		for (int i = 3 * ROW_LENGTH; i < grid.length; i++) {
			moveAffected(i, Direction.UP);
		}
	}

	/**
	 * Performs a move downwards.
	 * 
	 * @return the value to add to the score.
	 */
	public void moveDown() {
		offset = 4;
		for (int i = 0; i < ROW_LENGTH; i++) {
			moveAffected(i, Direction.DOWN);
		}
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
		if (grid[index].isEmpty())
			return false;

		/* If the tile next to this tile is empty, it can move */
		if (grid[index + offset].isEmpty())
			return true;
		/*
		 * If the tile is at the edge of that direction, it cannot move. For
		 * example, in | T - - - |, T cannot move to the left as it is already
		 * in the leftmost spot.
		 */
		if (dir == Direction.LEFT && index % ROW_LENGTH == 0) {
			return false;
		} else if (dir == Direction.RIGHT && index % ROW_LENGTH == 3) {
			return false;
		} else if (dir == Direction.UP && index < COL_LENGTH) {
			return false;
		} else if (dir == Direction.DOWN && index >= 3 * COL_LENGTH) {
			return false;
		}

		/*
		 * If the destination and the tile to be moved have not already merged
		 * with another tile...
		 */
		if (!grid[index + offset].isMerged() && !grid[index].isMerged()) {
			/*
			 * ...and either their values are equal or the destination tile is
			 * empty, the move can be made.
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
	 * @param i
	 *            the index of the farthest tile in the row or column, depending
	 *            on the direction to move to.
	 * @param dir
	 *            the direction to move to.
	 */
	private void moveAffected(int i, Direction dir) {
		/*
		 * While moves can be made in the row or column, move each tile in that
		 * row or column.
		 */
		while (isNeighbourFree(i, dir) || isNeighbourFree(i + offset, dir)
				|| isNeighbourFree(i + 2 * offset, dir)) {
			for (int k = 0; k < 3; k++) {
				if (isNeighbourFree(i + k * offset, dir)) {
					moveTile(i + k * offset);
				}
			}
		}
		resetMergedTiles(i, dir);
	}

	/**
	 * Moves a tile into the specified direction.
	 * 
	 * @param index
	 *            the index of the tile
	 * @return the value to add to the score.
	 */
	private void moveTile(int index) {
		if (grid[index].isEmpty()) {
			return;
		} else if (grid[index + offset].getValue() == grid[index].getValue()) {
			grid[index + offset].doubleValue();
			scoreIncrement += grid[index + offset].getValue();
			grid[index + offset].setMerged(true);
			grid[index].reset();
			isMoveMade = true;
		} else if (grid[index + offset].isEmpty()) {
			grid[index + offset].setValue(grid[index].getValue());
			grid[index].resetValue();
			isMoveMade = true;
		}
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

	public int getScoreIncrement() {
		return scoreIncrement;
	}

	public void reset() {
		scoreIncrement = 0;
		isMoveMade = false;
	}
}