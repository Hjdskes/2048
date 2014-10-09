package nl.tudelft.ti2206.handlers;

import java.util.Arrays;

import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.gameobjects.TileIterator;

/**
 * This class is responsible for moving tiles on the grid.
 */
public class TileHandler {
	/** The length of a row on the grid. */
	private static final int ROW_LENGTH = 4;

	/** The array holding all the tiles. */
	private Tile[] tiles;

	/** Indicates whether a move has been made. */
	private boolean isMoveMade;

	/** The points to be awarded to the player */
	private int scoreIncrement;

	/**
	 * Constructs a new TileHandler object.
	 * 
	 * @param tiles
	 *            The array holding the tiles.
	 */
	public TileHandler(Tile[] tiles) {
		this.tiles = tiles;
		this.scoreIncrement = 0;
		this.isMoveMade = false;
	}

	/**
	 * Performs a move to the left.
	 */
	public void moveLeft() {
		TileIterator iterator = null;
		/* For each row in the Grid... */
		for (int index = 0; index < tiles.length; index += ROW_LENGTH) {
			/* ... create an iterator that iterates over that row only... */
			iterator = new TileIterator(Arrays.copyOfRange(tiles, index, index
					+ ROW_LENGTH));
			/*
			 * ... and make it do so four times, to move tiles as far as they
			 * possibly can.
			 */
			for (int i = 0; i < ROW_LENGTH; i++) {
				move(iterator);
			}
			resetMerged(iterator);
		}
	}

	/**
	 * Performs a move to the right.
	 */
	public void moveRight() {
		tiles = rotate(180);
		moveLeft();
		tiles = rotate(180);
	}

	/**
	 * Performs a move downwards.
	 */
	public void moveDown() {
		tiles = rotate(90);
		moveLeft();
		tiles = rotate(270);
	}

	/**
	 * Performs a move upwards.
	 */
	public void moveUp() {
		tiles = rotate(270);
		moveLeft();
		tiles = rotate(90);
	}

	/**
	 * The method that actually performs the move.
	 * 
	 * @param iterator
	 *            The TileIterator to iterate over the tiles.
	 */
	private void move(TileIterator iterator) {
		Tile collidee = null;
		Tile collider = iterator.next();
		while (iterator.hasNext()) {
			collidee = collider;
			collider = iterator.next();
			if (collider.getValue() == 0 || collidee.isMerged()
					|| collider.isMerged()) {
				continue;
			} else {
				if (collidee.isEmpty()) {
					collidee.setValue(collider.getValue());
					if (collider.isMerged()) {
						collidee.setMerged(true);
					}
					collider.reset();
					isMoveMade = true;
				} else if (collider.getValue() == collidee.getValue()) {
					collidee.doubleValue();
					collidee.setMerged(true);
					collidee.merge();
					collider.reset();
					scoreIncrement += Math.pow(2, collidee.getValue());
					isMoveMade = true;
				}
			}
		}
		iterator.reset();
	}

	/**
	 * Rotates the grid. Taken from:
	 * https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L184
	 * 
	 * @param angle
	 *            The angle by which to rotate.
	 * @return The rotated Grid.
	 */
	private Tile[] rotate(int angle) {
		Tile[] res = new Tile[16];

		int offsetX = 3, offsetY = 3;
		if (angle == 90) {
			offsetY = 0;
		} else if (angle == 270) {
			offsetX = 0;
		}

		double rad = Math.toRadians(angle);
		int cos = (int) Math.cos(rad);
		int sin = (int) Math.sin(rad);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				int newX = (x * cos) - (y * sin) + offsetX;
				int newY = (x * sin) + (y * cos) + offsetY;
				res[newX + newY * 4] = tiles[x + y * 4];
			}
		}
		return res;
	}

	/**
	 * Resets all merged tiles in a row or column so they can merge again.
	 * 
	 * @param iterator
	 *            The TileIterator to iterate over the tiles.
	 */
	private void resetMerged(TileIterator iterator) {
		while (iterator.hasNext()) {
			iterator.next().setMerged(false);
		}
		iterator.reset();
	}

	/**
	 * @return True if a move has been made, false otherwise.
	 */
	public boolean isMoveMade() {
		return this.isMoveMade;
	}

	/**
	 * @return The value by which the score should be incremented.
	 */
	public int getScoreIncrement() {
		return this.scoreIncrement;
	}

	/**
	 * Prepares the TileHandler for the next round of moving tiles.
	 */
	public void reset() {
		this.scoreIncrement = 0;
		this.isMoveMade = false;
	}
}
